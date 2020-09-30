package cz.jpalcut.pia.dao;

import cz.jpalcut.pia.model.Account;
import cz.jpalcut.pia.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

/**
 * Rozhraní pro přístup k transakcím
 */
@Repository
public interface TransactionDAO extends JpaRepository<Transaction, Integer> {

    /**
     * Vrátí stránku transakcí podle omezení a bankovního účtu
     *
     * @param account  bankovní účet
     * @param pageable omezení pro výběr transakcí
     * @return stránka trasakcí
     */
    Page<Transaction> findAllByAccount(Account account, Pageable pageable);

    /**
     * Vrátí transakci podle id transakce
     *
     * @param id id transakce
     * @return transakce
     */
    Transaction findTransactionById(Integer id);

    /**
     * Vrátí všechny transakce podle data zpracování
     *
     * @param date datum zpracování
     * @return seznam transakcí
     */
    List<Transaction> findAllByProcessingDate(Date date);

    /**
     * Vratí všechny transakce podle bankovního účtu
     *
     * @param account bankování účet
     * @return seznam transakcí
     */
    List<Transaction> findAllByAccount(Account account);
}
