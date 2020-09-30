package cz.jpalcut.pia.service.interfaces;

import cz.jpalcut.pia.model.Account;
import cz.jpalcut.pia.model.Template;
import cz.jpalcut.pia.model.Transaction;
import cz.jpalcut.pia.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.List;

/**
 * Rozhraní pro službu spravující transakce
 */
public interface ITransactionService {

    /**
     * Přidá transakci k provedení
     *
     * @param transaction transakce
     * @param localBankTransaction transakce uvnitř firmy
     * @return transakce
     */
    Transaction addTransaction(Transaction transaction, boolean localBankTransaction);

    /**
     * Pravidelné schvalování transakcí podle v opakujícím
     */
    void processTransactions();

    /**
     * Vrátí seznam transakcí podle bankovního účtu
     *
     * @param account bankování účet
     * @return seznam transakcí
     */
    List<Transaction> getTransactionsByAccount(Account account);

    /**
     * Vrátí transakci podle id
     *
     * @param id id transakce
     * @return transakce
     */
    Transaction getTransactionById(Integer id);

    /**
     * Vrátí stránku transakcí k zobrazení podle omezení a bankovního účtu
     *
     * @param account  bankovní účet
     * @param pageable omezení pro výběr transakcí
     * @return stránka obsahující transakce
     */
    Page<Transaction> getTransactionsByAccountPageable(Account account, Pageable pageable);

    /**
     * Převede data z objektu Template do objektu Transaction
     *
     * @param template šablona
     * @return transakce
     */
    Transaction convertTemplateToTransaction(Template template);

    /**
     * Ověří zda-li transakce patří k uživateli
     *
     * @param transaction transakce
     * @param user        uživatel
     * @return true - patří k uživateli, false - nepatří k uživateli
     */
    boolean belongsTransactionToUser(Transaction transaction, User user);

    /**
     * Kontroluje datum splatnosti
     *
     * @param first datum k porovnání
     * @return true - validní datum splatnosti, false - datum je starší než aktuální datum
     */
    boolean isValidDueDate(Date first);

    /**
     * Kostroluje shodu bankovního čísla a bankovního kódu
     *
     * @param account       bankovní účet
     * @param accountNumber číslo účtu ke kontrole
     * @param bankCode      bankovní kód ke kontrole
     * @return true - údaje se rovnají, false - rozdílné údaje
     */
    boolean isAccountEqual(Account account, String accountNumber, String bankCode);

    /**
     * Kontroluje dostatek peněz na účtu k provedení transakce
     *
     * @param account bankovní účet
     * @param value   částka k zaplacení
     * @return true - dostatek peněz, false - nedostatek peněz
     */
    boolean hasMoneyToExecuteTransaction(Account account, Double value);

    /**
     * Kontroluje zda-li se jedná o neexistující účet v bance
     *
     * @param accountNumber číslo účtu
     * @param bankCode      bankovní kód
     * @return true - neexistující účet v bance, false - účet existuje
     */
    boolean isLocalNonExistentAccount(String accountNumber, String bankCode);

    /**
     * Kontroluje shodu bankovního kódu s lokálním kódem banky
     *
     * @param bankCode bankovní kód ke kontrole
     * @return true - jedná se o lokální kód, false - cizí banka
     */
    boolean isLocalBankCode(String bankCode);

}
