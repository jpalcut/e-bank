package cz.jpalcut.pia.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Tabulka rolí uživatelů
 */
@Entity
@Table(schema = "public", name = "role")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    //jméno role
    @Column(name = "name")
    private String name;

    //seznam uživatelů s touto rolí
    @ManyToMany(mappedBy = "roleList")
    private List<User> users;

    /**
     * Konstruktor
     */
    public Role() {
    }

    /**
     * Konstruktor pro vytvoření role
     *
     * @param name  název role
     * @param users seznam uživatelů pro přiřazení role
     */
    public Role(String name, List<User> users) {
        this.name = name;
        this.users = users;
    }

    /**
     * Vrátí id role
     *
     * @return id role
     */
    public int getId() {
        return id;
    }

    /**
     * Změní id role
     *
     * @param id id role
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Vrátí název role
     *
     * @return název role
     */
    public String getName() {
        return name;
    }

    /**
     * Změní název role
     *
     * @param name název role
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Vrátí list uživatelů s touto rolí
     *
     * @return seznam uživatelů
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Změní seznam uživatelů pro roli
     *
     * @param users seznam uživatelů
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }
}
