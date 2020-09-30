package cz.jpalcut.pia.dao;

import cz.jpalcut.pia.model.Account;
import cz.jpalcut.pia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Rozhraní pro přístup k bankovním účetům
 */
@Repository
public interface AccountDAO extends JpaRepository<Account, Integer> {

    /**
     * Vrátí bankovní účet podle čísla účtu
     *
     * @param number číslo účtu
     * @return bankovní účet
     */
    Account findAccountByNumber(String number);

    /**
     * Vrátí bankovní účet podle číska kreditní karty
     *
     * @param number číslo kreditní karty
     * @return bankovní účet
     */
    Account findAccountByCardNumber(String number);

    /**
     * Vrátí bankovní účet podle uživatele
     *
     * @param user uživatel
     * @return bankovní účet
     */
    Account findAccountByUser(User user);

    /**
     * Vrátí bakovní účet podle id
     *
     * @param id id bankovního účtu
     * @return bankovní účet
     */
    Account findAccountById(Integer id);

}
