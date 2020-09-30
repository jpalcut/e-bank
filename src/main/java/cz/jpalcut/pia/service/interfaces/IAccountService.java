package cz.jpalcut.pia.service.interfaces;

import cz.jpalcut.pia.model.Account;
import cz.jpalcut.pia.model.User;

/**
 * Rozhraní pro službu spravující bankovní účty
 */
public interface IAccountService {


    /**
     * Vratí bankovní účet podle uživatele
     *
     * @param user uživatel
     * @return bankovní účet
     */
    Account getAccount(User user);

    /**
     * Vrátí bankovní účet podle čísla účtu
     *
     * @param number číslo bankovního účtu
     * @return bankovní účet
     */
    Account getAccountByNumber(String number);

    /**
     * Vrátí bankovní účet podle čísla kreditní karty
     *
     * @param number číslo kreditnní karty
     * @return bankovní účet
     */
    Account getAccountByCardNumber(String number);

    /**
     * Uloží nebo úpraví bankovní účet
     *
     * @param account bankovní účet
     * @return bankovní účet
     */
    Account save(Account account);

    /**
     * Vrátí bankovní účet podle ID
     *
     * @param id indentifikace účtu
     * @return bankovní účet
     */
    Account getAccountById(Integer id);

    /**
     * Změní limit platby do mínusu u bankovního účtu
     *
     * @param account bankovní účet
     * @param value   hodnota
     * @return bankovní účet
     */
    Account changeLimitBelow(Account account, Double value);

    /**
     * Povolí nebo zakáže mezinárodní platbu kartou
     *
     * @param account bankovní účet
     * @return bankovní účet
     */
    Account changeInternationalPayment(Account account);

    /**
     * Ověření jestli účet patří uživateli
     *
     * @param account uživatelský účet
     * @param user    uživatel
     * @return true - účet patří uživateli, false - nepatří uživateli
     */
    boolean belongsAccountToUser(Account account, User user);

    /**
     * Porovná shodu hodnotu limitu účtu pod nulu s zadanou hodnotou
     *
     * @param account uživatelský účet
     * @param value   hodnota k porovnání
     * @return true - jsou shodné, false - jsou odlišné
     */
    boolean isLimitValueBelowEqual(Account account, Double value);

}
