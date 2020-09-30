package cz.jpalcut.pia.service.interfaces;

import cz.jpalcut.pia.model.Account;
import cz.jpalcut.pia.model.User;
import cz.jpalcut.pia.model.UserRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Rozhraní pro službu spravující požadavky uživatelů
 */
public interface IUserRequestService {

    /**
     * Vrátí stránku žádostí uživatelů k zobrazení podle omezení
     *
     * @param pageable omezení pro výběr uživatelů
     * @return stránka obsahující žádosti uživatele
     */
    Page<UserRequest> getAllUserRequestPageable(Pageable pageable);

    /**
     * Vrátí žádost uživatele podle id
     *
     * @param id id žádosti
     * @return žádost uživatele
     */
    UserRequest getUserRequestById(Integer id);

    /**
     * Smaže žádost uživatele
     *
     * @param userRequest žádost uživatele k smazáí
     */
    void deleteUserRequest(UserRequest userRequest);

    /**
     * Vrátí všechny žádosti uživatele podle bankovního účtu
     *
     * @param account bankovní účet
     * @return seznam žádostí uživatele
     */
    List<UserRequest> getUserRequestsByAccount(Account account);

    /**
     * Vrátí žádost uživatele podle typu a bankovního účtu
     *
     * @param type    typ žádosti
     * @param account bankovní účet
     * @return žádost uživatele
     */
    UserRequest getUserRequestByTypeAndAccount(String type, Account account);

    /**
     * Smaže požadavky uživatele podle bankovního účtu
     *
     * @param account bankovní účet
     */
    void deleteUserRequestByAccount(Account account);

    /**
     * Ověří typ žádosti pro změnu mezinárodní platby kartou
     *
     * @param type typ žádosti
     * @return true - správný typ žádosti, false - jiný typ žádosti
     */
    boolean isInternationalPaymentType(String type);

    /**
     * Ověří typ žádosti pro změnu limitu do mínusu
     *
     * @param type typ žádosti
     * @return true - správný typ žádosti, false - jiný typ žádosti
     */
    boolean isLimitBelowType(String type);

    /**
     * Uloží novou žádost o změnu limitu platby do mínusu
     *
     * @param account uživatelský účet
     * @param type    typ žádosti
     * @param value   hodnota ke změně
     * @return uživatelská žádost
     */
    UserRequest saveNewUserRequest(Account account, String type, Double value);

    /**
     * Kontroluje zda-li uživatelský požadavek patří k uživateli
     *
     * @param userRequest uživatelský požadavek
     * @param user        uživatel
     * @return true - požadavek patří k uživateli, false - nepatří k uživateli
     */
    boolean belongsUserRequestToUser(UserRequest userRequest, User user);

}
