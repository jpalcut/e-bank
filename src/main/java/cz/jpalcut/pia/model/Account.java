package cz.jpalcut.pia.model;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Tabulka bankovních účtů
 */
@Entity
@Table(schema = "public", name = "account")
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    //číslo účtu
    @Column(name = "number")
    private String number;

    //zůstatek na účtu
    @Column(name = "balance")
    private Double balance;

    //blokovaná suma nezpracovanými transakcemi
    @Column(name = "blocked_balance")
    private Double blockedBalance;

    @Column(name = "card_number")
    private String cardNumber;

    //povolení nebo zamítnutí mezinárodní platby kartou
    @Column(name = "international_payment")
    private Boolean internationalPayment;

    //limit do kterého může jít uživatel pod 0.00
    @Column(name = "limit_below")
    private Double limitBelow;

    //pin karty
    @Nullable
    @Column(name = "card_pin")
    private String cardPin;

    //vlastník účtu
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Konstruktor
     */
    public Account() {
    }

    /**
     * Konstruktor pro vytvoření bankovního účtu
     *
     * @param number               číslo účtu
     * @param balance              zůstatek
     * @param blockedBalance       blokovaná částka
     * @param cardNumber           číslo karty
     * @param internationalPayment mezinárodní platba kartou ano/ne
     * @param limitBelow           limit pro platbu do mínusu
     * @param cardPin              pin karty
     * @param user                 uživatel
     */
    public Account(String number, Double balance, Double blockedBalance, String cardNumber,
                   Boolean internationalPayment, Double limitBelow, String cardPin, User user) {
        this.number = number;
        this.balance = balance;
        this.blockedBalance = blockedBalance;
        this.cardNumber = cardNumber;
        this.internationalPayment = internationalPayment;
        this.limitBelow = limitBelow;
        this.cardPin = cardPin;
        this.user = user;
    }

    /**
     * Vrátí id bankovního účtu
     *
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Změní id bankovního účtu
     *
     * @param id id bankovního účtu
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Vrátí číslo bankovního účtu
     *
     * @return číslo bankovního účtu
     */
    public String getNumber() {
        return number;
    }

    /**
     * Změní číslo bankovního účtu
     *
     * @param number číslo bankovního účtu
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * Vrátí zůstatek na účtu
     *
     * @return zůstatek na účtu
     */
    public Double getBalance() {
        return balance;
    }

    /**
     * Změní zůstatek na účtu
     *
     * @param balance zůstatek ke změně
     */
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    /**
     * Vrátí číslo kreditní karty
     *
     * @return
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Změní číslo kreditní karty
     *
     * @param cardNumber číslo kreditní karty
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * Vrátí hodnotu blokované částky účtu
     *
     * @return blokovaná částka
     */
    public Double getBlockedBalance() {
        return blockedBalance;
    }

    /**
     * Změní hodnotu blokované částky účtu
     *
     * @param blockedBalance částka ke změně
     */
    public void setBlockedBalance(Double blockedBalance) {
        this.blockedBalance = blockedBalance;
    }

    /**
     * Vrátí uživatele účtu
     *
     * @return uživatel
     */
    public User getUser() {
        return user;
    }

    /**
     * Změní uživatele účtu
     *
     * @param user uživatel
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Vrátí mezinárodní platbu kartou
     *
     * @return true - povoleno, false - zakázáno
     */
    public Boolean getInternationalPayment() {
        return internationalPayment;
    }

    /**
     * Změní nastavení mezinárodní platby kartou
     *
     * @param internationalPayment true - povoleno, false - zakázáno
     */
    public void setInternationalPayment(Boolean internationalPayment) {
        this.internationalPayment = internationalPayment;
    }

    /**
     * Vrátí limit povolení platby pod 0.00
     *
     * @return limit
     */
    public Double getLimitBelow() {
        return limitBelow;
    }

    /**
     * Nastaví limit povolení platby pod 0.00
     *
     * @param limitBelow limit
     */
    public void setLimitBelow(Double limitBelow) {
        this.limitBelow = limitBelow;
    }

    /**
     * Vrátí pin kreditní karty
     *
     * @return pin karty
     */
    public String getCardPin() {
        return cardPin;
    }

    /**
     * Změní pin kreditní karty
     *
     * @param cardPin pin karty
     */
    public void setCardPin(String cardPin) {
        this.cardPin = cardPin;
    }
}
