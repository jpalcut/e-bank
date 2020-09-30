package cz.jpalcut.pia.service.interfaces;

import cz.jpalcut.pia.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Rozhraní pro správu uživatelského účtu
 */
public interface IUserService {

    /**
     * Vrátí přihlášeného uživatele
     *
     * @return přihlášený uživatel
     */
    User getUser();

    /**
     * Upraví údaje aktuálně přihlášeného uživatele
     *
     * @param user    přihlášený uživatel
     * @param newUser nové osobní údaje uživatele
     * @return uživatel
     */
    User editUser(User user, User newUser);

    /**
     * Upravení uživatele adminem
     *
     * @param user    aktulní údaje uživatele
     * @param newUser nové údaje uživatele
     * @return úpravený uživatel
     */
    User editUserByAdmin(User user, User newUser);

    /**
     * Vrátí stránku žádostí uživatelů k zobrazení podle omezení a role
     *
     * @param role     role uživatele
     * @param pageable omezení pro výběr uživatelů
     * @return stránka obsahující uživatele
     */
    Page<User> getAllUsersByRolePageable(String role, Pageable pageable);

    /**
     * Přidání uživatele
     *
     * @param user uživatel k přidání
     * @return přidaný uživatel
     */
    User addUser(User user);

    /**
     * Vrátí uživatele podle id
     *
     * @param id id uživatele
     * @return uživatel
     */
    User getUserById(Integer id);

    /**
     * Smaže uživatele (nastaví hodnotu deleted na true)
     *
     * @param user uživatel
     * @return uživatel
     */
    User deleteUser(User user);

    /**
     * Zkontroluje zda-li se jedná o smazaného uživatele
     *
     * @param user uživatel
     * @return true - je smazaný, false - není smazaný
     */
    boolean isDeletedUser(User user);

    /**
     * Generuje unikatní přihlašovací login
     *
     * @return přihlašovací login
     */
    String generateLoginId();

    /**
     * Generuje unikatní číslo karty
     *
     * @return číslo karty
     */
    String generateCreditCardNumber();

    /**
     * Generuje unikatní číslo účtu
     *
     * @return číslo účtu
     */
    String generateAccountNumber();

    /**
     * Kontrola zda-li má uživatel roli ADMIN
     *
     * @param user uživatel
     * @return true - admin, false - není admin
     */
    boolean hasRoleAdmin(User user);

}
