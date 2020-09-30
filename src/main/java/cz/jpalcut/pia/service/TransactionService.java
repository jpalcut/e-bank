package cz.jpalcut.pia.service;

import cz.jpalcut.pia.config.BankConfig;
import cz.jpalcut.pia.dao.TransactionDAO;
import cz.jpalcut.pia.model.Account;
import cz.jpalcut.pia.model.Template;
import cz.jpalcut.pia.model.Transaction;
import cz.jpalcut.pia.model.User;
import cz.jpalcut.pia.service.interfaces.IAccountService;
import cz.jpalcut.pia.service.interfaces.ITransactionService;
import cz.jpalcut.pia.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

/**
 * Služba pro správu transakcí
 */
@Service
@Transactional
public class TransactionService implements ITransactionService {

    private TransactionDAO transactionDAO;

    private IUserService userService;

    private IAccountService accountService;

    private BankConfig bankConfig;

    /**
     * Konstruktor třídy
     *
     * @param userService    UserService
     * @param accountService AccountService
     * @param transactionDAO TransactionService
     */
    @Autowired
    public TransactionService(UserService userService, AccountService accountService, TransactionDAO transactionDAO, BankConfig bankConfig) {
        this.userService = userService;
        this.accountService = accountService;
        this.transactionDAO = transactionDAO;
        this.bankConfig = bankConfig;
    }

    /**
     * Přidá transakci k provedení
     *
     * @param transaction          transakce
     * @param localBankTransaction transakce uvnitř banky
     * @return transakce
     */
    @Override
    public Transaction addTransaction(Transaction transaction, boolean localBankTransaction) {
        Account account = accountService.getAccount(userService.getUser());
        account.setBlockedBalance(account.getBlockedBalance() + transaction.getValue());

        transaction.setProcessingDate(null);
        transaction.setIncome(false);
        transaction.setAccount(account);
        transactionDAO.save(transaction);

        if (localBankTransaction && isLocalBankCode(transaction.getCode())) {
            Account secondAccount = accountService.getAccountByNumber(transaction.getNumber());
            Transaction secondTransaction = new Transaction(true, transaction.getNumber(), transaction.getCode(),
                    transaction.getValue(), transaction.getDueDate(), transaction.getVariableSymbol(), transaction.getConstantSymbol(),
                    transaction.getSpecificSymbol(), transaction.getMessage(), null, secondAccount);

            transactionDAO.save(secondTransaction);
        }

        return transaction;
    }

    /**
     * Pravidelné schvalování transakcí podle v opakujícím se čase podle fixedDelayString
     */
    @Scheduled(fixedDelayString = "${appvar.proccess_transaction_time}")
    @Override
    public void processTransactions() {
        Account account;
        List<Transaction> transactions = transactionDAO.findAllByProcessingDate(null);
        for (Transaction transaction : transactions) {
            transaction.setProcessingDate(new Date(System.currentTimeMillis()));
            account = transaction.getAccount();

            if (transaction.getIncome()) {
                account.setBalance(account.getBalance() + transaction.getValue());
            } else {
                account.setBalance(account.getBalance() - transaction.getValue());
                account.setBlockedBalance(account.getBlockedBalance() - transaction.getValue());
            }

            transactionDAO.save(transaction);
        }
    }

    /**
     * Vrátí seznam transakcí podle bankovního účtu
     *
     * @param account bankování účet
     * @return seznam transakcí
     */
    @Override
    public List<Transaction> getTransactionsByAccount(Account account) {
        return transactionDAO.findAllByAccount(account);
    }

    /**
     * Vrátí transakci podle id
     *
     * @param id id transakce
     * @return transakce
     */
    @Override
    public Transaction getTransactionById(Integer id) {
        return transactionDAO.findTransactionById(id);
    }

    /**
     * Vrátí stránku transakcí k zobrazení podle omezení a bankovního účtu
     *
     * @param account  bankovní účet
     * @param pageable omezení pro výběr transakcí
     * @return stránka obsahující transakce
     */
    @Override
    public Page<Transaction> getTransactionsByAccountPageable(Account account, Pageable pageable) {
        return transactionDAO.findAllByAccount(account, pageable);
    }

    /**
     * Převede data z objektu Template do objektu Transaction
     *
     * @param template šablona
     * @return transakce
     */
    @Override
    public Transaction convertTemplateToTransaction(Template template) {
        return new Transaction(null, template.getNumber(), template.getCode(), template.getValue(),
                null, template.getVariableSymbol(), template.getConstantSymbol(), template.getSpecificSymbol(),
                template.getMessage(), null, null);
    }

    /**
     * Ověří zda-li transakce patří k uživateli
     *
     * @param transaction transakce
     * @param user        uživatel
     * @return true - patří k uživateli, false - nepatří k uživateli
     */
    @Override
    public boolean belongsTransactionToUser(Transaction transaction, User user) {
        return transaction.getAccount().getUser().getId().equals(user.getId());
    }

    /**
     * Kontroluje datum splatnosti
     *
     * @param first datum k porovnání
     * @return true - validní datum splatnosti, false - datum je starší než aktuální datum
     */
    @Override
    public boolean isValidDueDate(Date first) {
        Date second = new Date(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(first);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(second);

        //testování jestli se jedná o starší datum než aktuální
        if (first.compareTo(second) < 0) {
            //porovnání let
            if (cal.get(Calendar.YEAR) < cal2.get(Calendar.YEAR)) {
                return false;
            }
            //porovnání měsíců
            if (cal.get(Calendar.MONTH) < cal2.get(Calendar.MONTH)) {
                return false;
            }
            //porovnání dnů
            if (cal.get(Calendar.DAY_OF_MONTH) < cal2.get(Calendar.DAY_OF_MONTH)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Kostroluje shodu bankovního čísla a bankovního kódu
     *
     * @param account       bankovní účet
     * @param accountNumber číslo účtu ke kontrole
     * @param bankCode      bankovní kód ke kontrole
     * @return true - údaje se rovnají, false - rozdílné údaje
     */
    @Override
    public boolean isAccountEqual(Account account, String accountNumber, String bankCode) {
        return account.getNumber().equals(accountNumber) && bankCode.equals(bankConfig.getBankCode());
    }

    /**
     * Kontroluje dostatek peněz na účtu k provedení transakce
     *
     * @param account bankovní účet
     * @param value   částka k zaplacení
     * @return true - dostatek peněz, false - nedostatek peněz
     */
    @Override
    public boolean hasMoneyToExecuteTransaction(Account account, Double value) {
        return (account.getBalance() + account.getLimitBelow() - account.getBlockedBalance() - value) >= 0.0;
    }

    /**
     * Kontroluje zda-li se jedná o neexistující účet v bance
     *
     * @param accountNumber číslo účtu
     * @param bankCode      bankovní kód
     * @return true - neexistující účet v bance, false - účet existuje
     */
    @Override
    public boolean isLocalNonExistentAccount(String accountNumber, String bankCode) {
        if (!isLocalBankCode(bankCode)) {
            return false;
        }
        return accountService.getAccountByNumber(accountNumber) == null;
    }

    /**
     * Kontroluje shodu bankovního kódu s lokálním kódem banky
     *
     * @param bankCode bankovní kód ke kontrole
     * @return true - jedná se o lokální kód, false - cizí banka
     */
    @Override
    public boolean isLocalBankCode(String bankCode) {
        return bankCode.equals(bankConfig.getBankCode());
    }

}
