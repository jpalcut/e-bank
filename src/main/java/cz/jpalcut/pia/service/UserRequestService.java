package cz.jpalcut.pia.service;

import cz.jpalcut.pia.dao.UserRequestDAO;
import cz.jpalcut.pia.model.Account;
import cz.jpalcut.pia.model.User;
import cz.jpalcut.pia.model.UserRequest;
import cz.jpalcut.pia.service.interfaces.IUserRequestService;
import cz.jpalcut.pia.utils.Enum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Služba pro správu uživatelských žádostí
 */
@Service
@Transactional
public class UserRequestService implements IUserRequestService {

    private UserRequestDAO userRequestDAO;

    /**
     * Konstruktor třídy
     *
     * @param userRequestDAO UserRequestDAO
     */
    @Autowired
    public UserRequestService(UserRequestDAO userRequestDAO) {
        this.userRequestDAO = userRequestDAO;
    }

    /**
     * Vrátí stránku žádostí uživatelů k zobrazení podle omezení
     *
     * @param pageable omezení pro výběr uživatelů
     * @return stránka obsahující žádosti uživatele
     */
    @Override
    public Page<UserRequest> getAllUserRequestPageable(Pageable pageable) {
        return userRequestDAO.findAll(pageable);
    }

    /**
     * Vrátí žádost uživatele podle id
     *
     * @param id id žádosti
     * @return žádost uživatele
     */
    @Override
    public UserRequest getUserRequestById(Integer id) {
        return userRequestDAO.findUserRequestById(id);
    }

    /**
     * Smaže žádost uživatele
     *
     * @param userRequest žádost uživatele k smazáí
     */
    @Override
    public void deleteUserRequest(UserRequest userRequest) {
        userRequestDAO.delete(userRequest);
    }

    /**
     * Vrátí všechny žádosti uživatele podle bankovního účtu
     *
     * @param account bankovní účet
     * @return seznam žádostí uživatele
     */
    @Override
    public List<UserRequest> getUserRequestsByAccount(Account account) {
        return userRequestDAO.findAllByAccount(account);
    }

    /**
     * Vrátí žádost uživatele podle typu a bankovního účtu
     *
     * @param type    typ žádosti
     * @param account bankovní účet
     * @return žádost uživatele
     */
    @Override
    public UserRequest getUserRequestByTypeAndAccount(String type, Account account) {
        return userRequestDAO.findUserRequestByTypeAndAccount(type, account);
    }

    /**
     * Smaže požadavky uživatele podle bankovního účtu
     *
     * @param account bankovní účet
     */
    @Override
    public void deleteUserRequestByAccount(Account account) {
        userRequestDAO.deleteByAccount(account);
    }

    /**
     * Ověří typ žádosti pro změnu mezinárodní platby kartou
     *
     * @param type typ žádosti
     * @return true - správný typ žádosti, false - jiný typ žádosti
     */
    @Override
    public boolean isInternationalPaymentType(String type) {
        return type.equals(Enum.UserRequestType.valueOf("INTERNATIONAL_PAYMENT").toString());
    }

    /**
     * Ověří typ žádosti pro změnu limitu do mínusu
     *
     * @param type typ žádosti
     * @return true - správný typ žádosti, false - jiný typ žádosti
     */
    @Override
    public boolean isLimitBelowType(String type) {
        return type.equals(Enum.UserRequestType.valueOf("LIMIT_BELOW").toString());
    }

    /**
     * Uloží novou uživatelskou žádost o změnu
     *
     * @param account uživatelský účet
     * @param type    typ žádosti
     * @param value   hodnota ke změně
     * @return uživatelská žádost
     */
    @Override
    public UserRequest saveNewUserRequest(Account account, String type, Double value) {
        UserRequest userRequest = new UserRequest(type, value, account);
        return userRequestDAO.save(userRequest);
    }

    /**
     * Kontroluje zda-li uživatelský požadavek patří k uživateli
     *
     * @param userRequest uživatelský požadavek
     * @param user        uživatel
     * @return true - požadavek patří k uživateli, false - nepatří k uživateli
     */
    @Override
    public boolean belongsUserRequestToUser(UserRequest userRequest, User user) {
        return userRequest.getAccount().getUser().getId().equals(user.getId());
    }


}
