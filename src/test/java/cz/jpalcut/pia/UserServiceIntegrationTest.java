package cz.jpalcut.pia;

import cz.jpalcut.pia.dao.UserDAO;
import cz.jpalcut.pia.model.*;
import cz.jpalcut.pia.service.*;
import cz.jpalcut.pia.utils.Enum;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Třída testující UserService
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class UserServiceIntegrationTest {

    private UserDAO userDAO = mock(UserDAO.class);

    private AccountService accountService = mock(AccountService.class);

    private RoleService roleService = mock(RoleService.class);

    private UserService userService;

    private UserRequestService userRequestService = mock(UserRequestService.class);

    private MailSenderService mailService = mock(MailSenderService.class);

    @Before
    public void setUp() {
        userService = new UserService(accountService, roleService, userDAO, userRequestService, mailService);
    }

    /**
     * Testování návratu uživatele podle existujícího id
     */
    @Test
    public void getUserByIdExists() {
        User first = new User();
        first.setId(1);

        when(userDAO.findUserById(first.getId())).thenReturn(first);

        User second = userService.getUserById(first.getId());

        Assert.assertEquals(first, second);
    }

    /**
     * Testování návratu uživatele podle id, které neexistuje
     */
    @Test
    public void getUserByIdNull() {
        int userId = 1;
        User user = null;
        User userServiceReturn;

        when(userDAO.findUserById(userId)).thenReturn(null);

        userServiceReturn = userService.getUserById(userId);

        Assert.assertEquals(userServiceReturn, user);
    }

    /**
     * Testování správného uložení přihlašovacího jména a hesla do Spring Security uživatele
     */
    @Test
    public void loadUserByUsername() {
        User first = new User();
        first.setLoginId("12345678");
        first.setPin("12345");
        first.setFirstname("Karel");
        first.setLastname("Novák");
        first.setDeleted(false);

        when(userDAO.findUserByLoginId(first.getLoginId())).thenReturn(first);

        UserDetails second = userService.loadUserByUsername(first.getLoginId());

        Assert.assertEquals(second.getUsername(), first.getLoginId());
        Assert.assertEquals(second.getPassword(), first.getPin());
    }

    /**
     * Testování změny správných hodnot při editaci uživatele adminem
     */
    @Test
    public void editUserByAdmin() {
        State state = new State();
        state.setName("Česká Republika");

        //role list
        Role role = new Role("USER", null);
        List<Role> roleList = new ArrayList<>();
        roleList.add(role);

        User newUser = new User("Karel","Novotný", "test@test.cz", null, null,
                "1234567899", "Zahradní", "44", "39701", "Muž", "Písek", null,
                state, null);

        User user = new User();
        user.setId(1);
        user.setPin("12345");
        user.setLoginId("1234578");
        user.setRoleList(roleList);

        //userService return
        User userServiceReturn;

        when(userDAO.save(any())).thenAnswer(i -> i.getArguments()[0]);

        userServiceReturn = userService.editUserByAdmin(user, newUser);

        //kontrola nezměnění nových údajů
        Assert.assertEquals(userServiceReturn.getState(), newUser.getState());
        Assert.assertEquals(userServiceReturn.getZipCode(), newUser.getZipCode());
        Assert.assertEquals(userServiceReturn.getTown(), newUser.getTown());
        Assert.assertEquals(userServiceReturn.getSex(), newUser.getSex());
        Assert.assertEquals(userServiceReturn.getEmail(), newUser.getEmail());
        Assert.assertEquals(userServiceReturn.getLastname(), newUser.getLastname());
        Assert.assertEquals(userServiceReturn.getFirstname(), newUser.getFirstname());
        Assert.assertEquals(userServiceReturn.getAddressNumber(), newUser.getAddressNumber());
        Assert.assertEquals(userServiceReturn.getAddress(), newUser.getAddress());
        Assert.assertEquals(userServiceReturn.getPid(), newUser.getPid());

        //kontrola přepsání starých údajů
        Assert.assertEquals(userServiceReturn.getPin(), user.getPin());
        Assert.assertEquals(userServiceReturn.getLoginId(), user.getLoginId());
        Assert.assertEquals(userServiceReturn.getRoleList(), user.getRoleList());
        Assert.assertEquals(userServiceReturn.getId(), user.getId());
        Assert.assertEquals(userServiceReturn.isDeleted(), user.isDeleted());

    }

    /**
     * Kontrola změny správných údajů při editaci uživatele
     */
    @Test
    public void editUser() {
        State state = new State();
        state.setName("Česká Republika");

        //role list
        Role role = new Role("USER", null);
        List<Role> roleList = new ArrayList<>();
        roleList.add(role);

        User userFirst = new User("Karel","Novotný", null, "12345678", "12345",
                "1234567899", null, null, null, null, null, false,
                null, roleList);
        userFirst.setId(1);


        User user = new User(null, null, "test@test.cz", null, null, null,
                "Zahradní", "44", "39701", "Muž", "Písek", null,
                state, null);

        when(userDAO.save(any())).thenAnswer(i -> i.getArguments()[0]);
        User second = userService.editUser(userFirst, user);

        //kontrola nezměnění údajů na null
        Assert.assertNotNull((second.getState()));
        Assert.assertNotNull(second.getZipCode());
        Assert.assertNotNull(second.getTown());
        Assert.assertNotNull(second.getSex());
        Assert.assertNotNull(second.getEmail());
        Assert.assertNotNull(second.getAddressNumber());
        Assert.assertNotNull(second.getAddress());

        //kontrola přepsání starých nezměnitelných údajů
        Assert.assertEquals(second.getFirstname(), userFirst.getFirstname());
        Assert.assertEquals(second.getLastname(), userFirst.getLastname());
        Assert.assertEquals(second.getPid(), userFirst.getPid());
        Assert.assertEquals(second.getRoleList(), userFirst.getRoleList());
        Assert.assertEquals(second.getLoginId(), userFirst.getLoginId());
        Assert.assertEquals(second.getPin(), userFirst.getPin());
        Assert.assertEquals(second.getId(), userFirst.getId());
        Assert.assertEquals(second.isDeleted(), userFirst.isDeleted());

    }

    /**
     * Testování správného vytvoření uživatele
     */
    @Test
    public void addUser() {
        State state = new State();
        state.setName("Česká Republika");

        //role list
        Role role = new Role("USER", null);
        List<Role> roleList = new ArrayList<>();
        roleList.add(role);


        User user = new User("Karel","Novotný", "test@test.cz", null, null,
                "1234567899", "Zahradní", "44", "39701", "Muž", "Písek", null,
                state, null);

        User userServiceReturn;

        when(userDAO.findUserByLoginId(any())).thenReturn(null);
        when(roleService.getRoleListByName("USER")).thenReturn(roleList);
        when(userDAO.save(any())).thenAnswer(i -> i.getArguments()[0]);
        when(accountService.save(any())).thenAnswer(i -> i.getArguments()[0]);

        userServiceReturn = userService.addUser(user);

        //kontrola nezměnění údajů z formuláře
        Assert.assertEquals(userServiceReturn.getState(), user.getState());
        Assert.assertEquals(userServiceReturn.getZipCode(), user.getZipCode());
        Assert.assertEquals(userServiceReturn.getTown(), user.getTown());
        Assert.assertEquals(userServiceReturn.getSex(), user.getSex());
        Assert.assertEquals(userServiceReturn.getEmail(), user.getEmail());
        Assert.assertEquals(userServiceReturn.getLastname(), user.getLastname());
        Assert.assertEquals(userServiceReturn.getFirstname(), user.getFirstname());
        Assert.assertEquals(userServiceReturn.getAddressNumber(), user.getAddressNumber());
        Assert.assertEquals(userServiceReturn.getAddress(), user.getAddress());
        Assert.assertEquals(userServiceReturn.getPid(), user.getPid());

        //ověření přidání údajů
        Assert.assertNotNull(userServiceReturn.getLoginId());
        Assert.assertNotNull(userServiceReturn.getPin());
        Assert.assertFalse(userServiceReturn.isDeleted());
    }

    /**
     * Zkontroluje jestli je role uživatele ADMIN
     */
    @Test
    public void hasRoleAdmin() {
        //role list
        Role role = new Role("ADMIN", null);
        List<Role> roleList = new ArrayList<>();
        roleList.add(role);

        //user
        User user = new User();
        user.setRoleList(roleList);

        when(roleService.getRoleByName(Enum.Role.valueOf("ADMIN").toString())).thenReturn(role);

        Assert.assertTrue(userService.hasRoleAdmin(user));
    }

    /**
     * Zkontroluje vygenerování čísla účtu
     */
    @Test
    public void generateAccountNumber() {
        when(accountService.getAccountByNumber(any())).thenReturn(null);
        Assert.assertNotNull(userService.generateAccountNumber());
    }

    /**
     * Zkontroluje vygenerování pinu
     */
    @Test
    public void generateLoginId() {
        when(userDAO.findUserByLoginId(any())).thenReturn(null);
        Assert.assertNotNull(userService.generateLoginId());
    }

    /**
     * Zkontroluje vygenerování čísla kreditní karty
     */
    @Test
    public void generateCreditCardNumber(){
        when(accountService.getAccountByCardNumber(any())).thenReturn(null);
        Assert.assertNotNull(userService.generateCreditCardNumber());
    }

    /**
     * Testování smazání uživatele
     */
    @Test
    public void deleteUser(){
        User user = new User();
        Account account = new Account();
        user.setDeleted(false);

        when(accountService.getAccount(user)).thenReturn(account);
        when(userDAO.save(any())).thenAnswer(i -> i.getArguments()[0]);

        Assert.assertTrue(userService.deleteUser(user).isDeleted());
    }

    /**
     * Testování jestli je uživatel smazáný
     */
    @Test
    public void isDeletedUser(){
        User user = new User();
        user.setDeleted(true);

        Assert.assertTrue(userService.isDeletedUser(user));
    }

    /**
     * Testování jestli je uživatel nesmazaný
     */
    @Test
    public void notDeletedUser(){
        User user = new User();
        user.setDeleted(false);

        Assert.assertFalse(userService.isDeletedUser(user));
    }

}
