package cz.jpalcut.pia.service;

import cz.jpalcut.pia.config.AppUser;
import cz.jpalcut.pia.dao.UserDAO;
import cz.jpalcut.pia.model.Account;
import cz.jpalcut.pia.model.Role;
import cz.jpalcut.pia.model.User;
import cz.jpalcut.pia.service.interfaces.*;
import cz.jpalcut.pia.utils.Enum;
import cz.jpalcut.pia.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailSendException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Slubža pro správu uživatelů
 */
@Service
@Transactional
public class UserService implements UserDetailsService, IUserService {

    private UserDAO userDAO;

    private IAccountService accountService;

    private IRoleService roleService;

    private IUserRequestService userRequestService;

    private IMailSenderService mailService;

    /**
     * Konstruktor třídy
     *
     * @param accountService AccountService
     * @param roleService    RoleService
     * @param userDAO        UserDAO
     */
    @Autowired
    public UserService(AccountService accountService, RoleService roleService, UserDAO userDAO,
                       UserRequestService userRequestService, MailSenderService mailSenderService) {
        this.accountService = accountService;
        this.roleService = roleService;
        this.userDAO = userDAO;
        this.userRequestService = userRequestService;
        this.mailService = mailSenderService;
    }

    /**
     * Načtení uživatele Spring Security
     *
     * @param loginId přihlašovací login
     * @return načtený uživatel Spring Security
     * @throws UsernameNotFoundException vyjímka pro nenalezení uživatele
     */
    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        User user = userDAO.findUserByLoginId(loginId);

        if (user == null || user.isDeleted()) {
            throw new UsernameNotFoundException("User" + loginId + " not found!");
        }

        List<GrantedAuthority> grantList = new ArrayList<>();
        if (user.getRoleList() != null) {
            for (Role role : user.getRoleList()) {
                GrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
                grantList.add(authority);
            }
        }

        UserDetails userDetails = new AppUser(user.getLoginId(),
                user.getPin(), user.getFirstname(), user.getLastname(), grantList);

        return userDetails;
    }

    /**
     * Vrátí přihlášeného uživatele
     *
     * @return přihlášený uživatel
     */
    @Override
    public User getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDAO.findUserByLoginId(((UserDetails) principal).getUsername());
    }

    /**
     * Upraví údaje aktuálně přihlášeného uživatele
     *
     * @param user    aktuálně přihlášený uživatel
     * @param newUser nová osobní data uživatele k editaci
     * @return uživatel
     */
    @Override
    public User editUser(User user, User newUser) {
        user.setAddress(newUser.getAddress());
        user.setAddressNumber(newUser.getAddressNumber());
        user.setZipCode(newUser.getZipCode());
        user.setState(newUser.getState());
        user.setEmail(newUser.getEmail());
        user.setSex(newUser.getSex());
        user.setTown(newUser.getTown());
        return userDAO.save(user);
    }

    /**
     * Upravení uživatele adminem
     *
     * @param user    aktulní údaje uživatele
     * @param newUser nové údaje uživatele
     * @return úpravený uživatel
     */
    @Override
    public User editUserByAdmin(User user, User newUser) {
        newUser.setId(user.getId());
        newUser.setPin(user.getPin());
        newUser.setLoginId(user.getLoginId());
        newUser.setRoleList(user.getRoleList());
        newUser.setDeleted(user.isDeleted());
        newUser = userDAO.save(newUser);

        if(newUser != null){
            try {
                mailService.sendEditedUserMail(newUser);
            }
            catch (MailSendException ex){
            ex.printStackTrace();
            }
        }
        return newUser;
    }

    /**
     * Vrátí stránku žádostí uživatelů k zobrazení podle omezení a role
     *
     * @param role     role uživatele
     * @param pageable omezení pro výběr uživatelů
     * @return stránka obsahující uživatele
     */
    @Override
    public Page<User> getAllUsersByRolePageable(String role, Pageable pageable) {
        List<Role> roleList = roleService.getRoleListByName(role);
        return userDAO.findAllByRoleListAndDeleted(roleList, pageable, false);
    }

    /**
     * Vrátí uživatele podle id
     *
     * @param id id uživatele
     * @return uživatel
     */
    @Override
    public User getUserById(Integer id) {
        return userDAO.findUserById(id);
    }

    /**
     * Smaže uživatele (nastaví hodnotu deleted na true) včetně jeho žádostí
     *
     * @param user uživatel
     * @return uživatel
     */
    @Override
    public User deleteUser(User user) {
        user.setDeleted(true);
        userRequestService.deleteUserRequestByAccount(accountService.getAccount(user));
        user = userDAO.save(user);
        if(user != null){
            try {
                mailService.sendDeletedUserMail(user);
            }
            catch (MailSendException ex){
                ex.printStackTrace();
            }
        }
        return user;
    }

    /**
     * Zkontroluje zda-li se jedná o smazaného uživatele
     *
     * @param user uživatel
     * @return true - je smazaný, false - není smazaný
     */
    @Override
    public boolean isDeletedUser(User user) {
        return user.isDeleted();
    }

    /**
     * Přidání uživatele včetně vytvoření bankovního účtu a generování potřebných údajů
     *
     * @param user uživatel k přidání
     * @return přidaný uživatel
     */
    @Override
    public User addUser(User user) {

        //uživatel
        String pin = Utils.generateNumber(5);
        user.setLoginId(generateLoginId());
        user.setPin(Utils.hashPassword(pin));
        user.setRoleList(roleService.getRoleListByName(Enum.Role.valueOf("USER").toString()));
        user.setDeleted(false);
        user = userDAO.save(user);

        //bankovní účet
        Account account = new Account(generateAccountNumber(), 0.00, 0.00,
                generateCreditCardNumber(), false, 0.00, Utils.generateNumber(5), user);

        account = accountService.save(account);

        if(account != null){
            try {
                mailService.sendRegisteredUserMail(user, pin);
            }
            catch (MailSendException ex){
                ex.printStackTrace();
            }
        }
        return user;
    }

    /**
     * Generuje unikatní přihlašovací login
     *
     * @return přihlašovací login
     */
    @Override
    public String generateLoginId() {
        String loginId;
        while (true) {
            loginId = Utils.generateNumber(8);
            if (userDAO.findUserByLoginId(loginId) == null) {
                return loginId;
            }
        }
    }

    /**
     * Generuje unikatní číslo karty
     *
     * @return číslo karty
     */
    @Override
    public String generateCreditCardNumber() {
        String creditCard;
        while (true) {
            creditCard = Utils.generateNumber(16);
            if (accountService.getAccountByCardNumber(creditCard) == null) {
                return creditCard;
            }
        }
    }

    /**
     * Generuje unikatní číslo účtu
     *
     * @return číslo účtu
     */
    @Override
    public String generateAccountNumber() {
        String accountNumber;
        while (true) {
            accountNumber = Utils.generateNumber(9);
            if (accountService.getAccountByNumber(accountNumber) == null) {
                return accountNumber;
            }
        }
    }

    /**
     * Kontrola zda-li má uživatel roli ADMIN
     *
     * @param user uživatel
     * @return true - admin, false - není admin
     */
    @Override
    public boolean hasRoleAdmin(User user) {
        return user.getRoleList().contains(roleService.getRoleByName(Enum.Role.valueOf("ADMIN").toString()));
    }

}
