package cz.jpalcut.pia.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Třída rozšiřující základní atributy třídy org.springframework.security.core.userdetails.User
 */
public class AppUser extends User {

    //křestní jméno uživatele
    private String firstName;

    //příjmení uživatele
    private String lastName;

    /**
     * Konstuktor pro vytvoření rozšířeného uživatele Spring Security
     *
     * @param username    přihlašovací login
     * @param password    přihlašovací heslo
     * @param firstName   křestní jméno
     * @param lastName    příjmení
     * @param authorities role uživatele
     */
    public AppUser(String username, String password, String firstName, String lastName,
                   Collection<? extends GrantedAuthority> authorities) {

        super(username, password, authorities);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
