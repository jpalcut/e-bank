package cz.jpalcut.pia.service;

import cz.jpalcut.pia.dao.AccountDAO;
import cz.jpalcut.pia.model.Account;
import cz.jpalcut.pia.model.User;
import cz.jpalcut.pia.service.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Služba pro správu bankovních účtů
 */
@Service
@Transactional
public class AccountService implements IAccountService {

    private AccountDAO accountDAO;

    /**
     * Konstruktor třídy
     *
     * @param accountDAO AccountDAO
     */
    @Autowired
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    /**
     * Vratí bankovní účet podle uživatele
     *
     * @param user uživatel
     * @return bankovní účet
     */
    @Override
    public Account getAccount(User user) {
        return accountDAO.findAccountByUser(user);
    }

    /**
     * Vrátí bankovní účet podle čísla účtu
     *
     * @param number číslo bankovního účtu
     * @return bankovní účet
     */
    @Override
    public Account getAccountByNumber(String number) {
        return accountDAO.findAccountByNumber(number);
    }

    /**
     * Vrátí bankovní účet podle čísla kreditní karty
     *
     * @param number číslo kreditnní karty
     * @return bankovní účet
     */
    @Override
    public Account getAccountByCardNumber(String number) {
        return accountDAO.findAccountByCardNumber(number);
    }

    /**
     * Uloží nebo úpraví bankovní účet
     *
     * @param account bankovní účet
     * @return bankovní účet
     */
    @Override
    public Account save(Account account) {
        return accountDAO.save(account);
    }

    /**
     * Vrátí bankovní účet podle ID
     *
     * @param id indentifikace účtu
     * @return bankovní účet
     */
    @Override
    public Account getAccountById(Integer id) {
        return accountDAO.findAccountById(id);
    }

    /**
     * Změní limit platby do mínusu u bankovního účtu
     *
     * @param account bankovní účet
     * @param value   hodnota
     * @return bankovní účet
     */
    @Override
    public Account changeLimitBelow(Account account, Double value) {
        account.setLimitBelow(value);
        return accountDAO.save(account);
    }

    /**
     * Povolí nebo zakáže mezinárodní platbu kartou
     *
     * @param account bankovní účet
     * @return bankovní účet
     */
    @Override
    public Account changeInternationalPayment(Account account) {
        if (account.getInternationalPayment()) {
            account.setInternationalPayment(false);
        } else {
            account.setInternationalPayment(true);
        }
        return accountDAO.save(account);
    }


    /**
     * Ověření jestli účet patří uživateli
     *
     * @param account uživatelský účet
     * @param user    uživatel
     * @return true - účet patří uživateli, false - nepatří uživateli
     */
    @Override
    public boolean belongsAccountToUser(Account account, User user) {
        return account.getUser().getId().equals(user.getId());
    }

    /**
     * Porovná shodu hodnotu limitu účtu pod nulu s zadanou hodnotou
     *
     * @param account uživatelský účet
     * @param value   hodnota k porovnání
     * @return true - jsou shodné, false - jsou odlišné
     */
    @Override
    public boolean isLimitValueBelowEqual(Account account, Double value) {
        return account.getLimitBelow().equals(value);
    }

}
