package cz.jpalcut.pia.model;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Tabulka transakcí
 */
@Entity
@Table(schema = "public", name = "transaction")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    //příjem ano/ne
    @Column(name = "income")
    private Boolean income;

    //číslo účtu
    @Size(min = 1, max = 17, message = "Číslo účtu musí mít 1-17 znaků!")
    @Pattern(regexp = "[\\d -]+", message = "Číslo účtu obsahuje nepovolené znaky!")
    @NotNull
    @Column(name = "number")
    private String number;

    //kód účtu
    @Size(min = 4, max = 4, message = "Kód banky musí mít 4 znaky!")
    @Pattern(regexp = "^[0-9]*$", message = "Kód banky musí obsahovat pouze čísla!")
    @Column(name = "code")
    private String code;

    //hodnota k poslání
    @NotNull(message = "Částka musí být vyplněna!")
    @DecimalMin(value = "0.01", message = "Hodnota musí být větší než 0.00")
    @Column(name = "value")
    private Double value;

    //datum splatnosti
    @NotNull(message = "Datum splatnosti musí být vyplněn!")
    @Column(name = "due_date")
    private Date dueDate;

    @Size(max = 10, message = "Maximální počet znaků je 10!")
    @Pattern(regexp = "^[0-9]*$", message = "Variabilní symbol musí obsahovat pouze čísla!")
    @Nullable
    @Column(name = "variable_symbol")
    private String variableSymbol;

    @Size(max = 4, message = "Maximální počet znaků je 4!")
    @Pattern(regexp = "^[0-9]*$", message = "Konstantní symbol musí obsahovat pouze čísla!")
    @Nullable
    @Column(name = "constant_symbol")
    private String constantSymbol;

    @Size(max = 10, message = "Maximální počet znaků je 10!")
    @Pattern(regexp = "^[0-9]*$", message = "Specifický symbol musí obsahovat pouze čísla!")
    @Nullable
    @Column(name = "specific_symbol")
    private String specificSymbol;

    @Nullable
    @Size(max = 100, message = "Maximální počet znaků je 100!")
    @Column(name = "message")
    private String message;

    //datum reálného zpracování transakce
    @Nullable
    @Column(name = "processing_date")
    private Date processingDate;

    //účet ke kterému transakce patří
    @Nullable
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Transient
    private boolean checkAccount;

    /**
     * Konstruktor
     */
    public Transaction() {
    }

    /**
     * Konstruktor pro vytvoření nové transakce
     *
     * @param income         příjem ano/ne
     * @param number         číslo účtu
     * @param code           bankovní kód
     * @param value          hodnota
     * @param dueDate        datum splatnosti
     * @param variableSymbol variabilní symbol
     * @param constantSymbol konstantní symbol
     * @param specificSymbol specifický symbol
     * @param message        zpráva
     * @param processingDate datum zpracování transakce bankou
     * @param account        bankovní účet
     */
    public Transaction(Boolean income, String number, String code, Double value, Date dueDate, String variableSymbol,
                       String constantSymbol, String specificSymbol, String message, Date processingDate, Account account) {
        this.income = income;
        this.number = number;
        this.code = code;
        this.value = value;
        this.dueDate = dueDate;
        this.variableSymbol = variableSymbol;
        this.constantSymbol = constantSymbol;
        this.specificSymbol = specificSymbol;
        this.message = message;
        this.processingDate = processingDate;
        this.account = account;
        this.checkAccount = true;
    }

    /**
     * Vrátí id transakce
     *
     * @return id transakce
     */
    public Integer getId() {
        return id;
    }

    /**
     * Změní id transakce
     *
     * @param id id transakce
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Vrátí jestli se jedná o příjem nebo výdej
     *
     * @return true - příjem, false - výdej
     */
    public Boolean getIncome() {
        return income;
    }

    /**
     * Změní příjem/výdej transakce
     *
     * @param income true - příjem, false - výdej
     */
    public void setIncome(Boolean income) {
        this.income = income;
    }

    /**
     * Vrátí číslo účtu
     *
     * @return číslo účtu
     */
    public String getNumber() {
        return number;
    }

    /**
     * Změní číslo účtu
     *
     * @param number číslo účtu
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * Vrátí kód banky
     *
     * @return kód banky
     */
    public String getCode() {
        return code;
    }

    /**
     * Změní kód banky
     *
     * @param code kód banky
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Vrátí částku transakce
     *
     * @return
     */
    public Double getValue() {
        return value;
    }

    /**
     * Změní částku transakce
     *
     * @param value
     */
    public void setValue(Double value) {
        this.value = value;
    }

    /**
     * Vrátí datum splatnosti
     *
     * @return datum splatnosti
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * Změní datum splatnosti
     *
     * @param dueDate datum splatnosti
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Vrátí variabilní symbol
     *
     * @return variabilní symbol
     */
    public String getVariableSymbol() {
        return variableSymbol;
    }

    /**
     * Změní variabilní symbol
     *
     * @param variableSymbol variabilná symbol
     */
    public void setVariableSymbol(String variableSymbol) {
        this.variableSymbol = variableSymbol;
    }

    /**
     * Vrátí konstantní symbol
     *
     * @return konstantní symbol
     */
    public String getConstantSymbol() {
        return constantSymbol;
    }

    /**
     * Změní konstantní symbol
     *
     * @param constantSymbol konstantní symbol
     */
    public void setConstantSymbol(String constantSymbol) {
        this.constantSymbol = constantSymbol;
    }

    /**
     * Vrátí specifický symbol
     *
     * @return specifický symbol
     */
    public String getSpecificSymbol() {
        return specificSymbol;
    }

    /**
     * Změní specifický symbol
     *
     * @param specificSymbol specifický symbol
     */
    public void setSpecificSymbol(String specificSymbol) {
        this.specificSymbol = specificSymbol;
    }

    /**
     * Vrátí zprávu pro příjemce
     *
     * @return zpráva
     */
    public String getMessage() {
        return message;
    }

    /**
     * Změní zprávu pro příjemce
     *
     * @param message zpráva
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Vrátí datum provedení transakce
     *
     * @return datum provedení transakce
     */
    public Date getProcessingDate() {
        return processingDate;
    }

    /**
     * Změní datum provedení transakce
     *
     * @param processingDate datum provedení transakce
     */
    public void setProcessingDate(Date processingDate) {
        this.processingDate = processingDate;
    }

    /**
     * Vrátí bankovní účet transakce
     *
     * @return bankovní účet transakce
     */
    public Account getAccount() {
        return account;
    }

    /**
     * Změní bankovní účet transakce
     *
     * @param account bankovní účet
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * Kontrolovat reálnou existenci účtu ano/ne
     *
     * @return true - kontrolovat, false - nekontrolovat
     */
    public boolean checkAccount() {
        return checkAccount;
    }

    /**
     * Nastavení kontroli existence účtu
     *
     * @param checkAccount
     */
    public void setCheckAccount(boolean checkAccount) {
        this.checkAccount = checkAccount;
    }
}
