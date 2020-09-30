package cz.jpalcut.pia.model;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Tabulka šablon
 */
@Entity
@Table(schema = "public", name = "template")
public class Template implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    //název šablony
    @Size(min = 1, max = 50, message = "Název vzoru musí mít 1-50 znaků!")
    @NotNull
    @Column(name = "name")
    private String name;

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
    @Nullable
    @Column(name = "value")
    private Double value;

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

    @Nullable
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    /**
     * Konstruktor
     */
    public Template() {
    }

    /**
     * Konstruktor pro vytvoření šablony
     *
     * @param name           název šablony
     * @param number         číslo účtu
     * @param code           bankovní kód
     * @param value          hodnota
     * @param variableSymbol variabilní symbol
     * @param constantSymbol konstantní symbol
     * @param specificSymbol specifický symbol
     * @param message        zpráva
     * @param account        bankovní účet
     */
    public Template(String name, String number, String code, Double value, String variableSymbol, String constantSymbol,
                    String specificSymbol, String message, Account account) {
        this.name = name;
        this.number = number;
        this.code = code;
        this.value = value;
        this.variableSymbol = variableSymbol;
        this.constantSymbol = constantSymbol;
        this.specificSymbol = specificSymbol;
        this.message = message;
        this.account = account;
    }

    /**
     * Vrátí id šablony
     *
     * @return id šablony
     */
    public Integer getId() {
        return id;
    }

    /**
     * Změní id šablony
     *
     * @param id id šablony
     */
    public void setId(Integer id) {
        this.id = id;
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
     * Vrátí částku uloženou v šabloně
     *
     * @return částka
     */
    public Double getValue() {
        return value;
    }

    /**
     * Změní částku v šabloně
     *
     * @param value částka
     */
    public void setValue(Double value) {
        this.value = value;
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
     * @param variableSymbol variabilní symbol
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
     * Změní zpravu pro příjemce
     *
     * @param message zpráva
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Vrátí název šablony
     *
     * @return název šablony
     */
    public String getName() {
        return name;
    }

    /**
     * Změní název šablony
     *
     * @param name název šablony
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Vrátí bankovní účet šablony
     *
     * @return
     */
    public Account getAccount() {
        return account;
    }

    /**
     * Změní bankovní účet šablony
     *
     * @param account
     */
    public void setAccount(Account account) {
        this.account = account;
    }
}
