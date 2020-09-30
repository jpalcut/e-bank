package cz.jpalcut.pia.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Tabulka států
 */
@Entity
@Table(schema = "public", name = "state")
public class State implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    //název státu
    @Column(name = "name")
    private String name;

    /**
     * Konstruktor
     */
    public State() {
    }

    /**
     * Konstruktor pro vytvoření státu
     *
     * @param name název státu
     */
    public State(String name) {
        this.name = name;
    }

    /**
     * Vrátí id státu
     *
     * @return id státu
     */
    public Integer getId() {
        return id;
    }

    /**
     * Změní id státu
     *
     * @param id id státu
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Vrátí název státu
     *
     * @return název státu
     */
    public String getName() {
        return name;
    }

    /**
     * Změní název státu
     *
     * @param name název státu
     */
    public void setName(String name) {
        this.name = name;
    }
}
