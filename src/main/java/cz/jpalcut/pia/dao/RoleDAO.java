package cz.jpalcut.pia.dao;

import cz.jpalcut.pia.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Rozhraní pro přístup k uživatelským rolím
 */
@Repository
public interface RoleDAO extends JpaRepository<Role, Integer> {

    /**
     * Vrátí roli podle názvu role
     *
     * @param role název role
     * @return role
     */
    Role findByName(String role);

    /**
     * Vrátí všechny role podle názvu role
     *
     * @param role název role
     * @return seznam rolí
     */
    List<Role> findRoleListByName(String role);

}
