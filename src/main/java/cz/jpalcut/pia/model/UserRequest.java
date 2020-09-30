package cz.jpalcut.pia.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Tabulka požadavků uživatelů ke změně
 */
@Entity
@Table(schema = "public", name = "user_request")
public class UserRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    //typ požadavku uživatele
    @Column(name = "type")
    private String type;

    //hodnota ke změnění
    @Column(name = "value")
    private Double value;

    //účet ke změně
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    /**
     * Konstruktor
     */
    public UserRequest() {
    }

    /**
     * Konstruktor pro vytvoření uživatelské žádosti
     *
     * @param type    typ žádosti
     * @param value   hodnota ke změně
     * @param account uživatelský účet
     */
    public UserRequest(String type, Double value, Account account) {
        this.type = type;
        this.value = value;
        this.account = account;
    }

    /**
     * Vrátí id požadavku
     *
     * @return id požadavku
     */
    public Integer getId() {
        return id;
    }

    /**
     * Změni id požadavku
     *
     * @param id id požadavku
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Vrátí typ požadavku
     *
     * @return typ požadavku
     */
    public String getType() {
        return type;
    }

    /**
     * Změní typ požadavku
     *
     * @param type typo požadavku
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Vrátí hodnotu požadavku
     *
     * @return hodnota požadavku
     */
    public Double getValue() {
        return value;
    }

    /**
     * Změní hodnotu požadavku
     *
     * @param value hodnota požadavku
     */
    public void setValue(Double value) {
        this.value = value;
    }

    /**
     * Vrátí bankovní účet požadavku
     *
     * @return bankovní účet
     */
    public Account getAccount() {
        return account;
    }

    /**
     * Změní bankovní účet požadavku
     *
     * @param account bankovní účet
     */
    public void setAccount(Account account) {
        this.account = account;
    }

}
