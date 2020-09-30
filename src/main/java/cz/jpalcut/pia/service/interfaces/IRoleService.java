package cz.jpalcut.pia.service.interfaces;

import cz.jpalcut.pia.model.Role;

import java.util.List;

/**
 * Rozhraní pro službu spravující role uživatelů
 */
public interface IRoleService {

    /**
     * Vrátí seznam rolí podle názvu role
     *
     * @param role název role
     * @return seznam rolí
     */
    List<Role> getRoleListByName(String role);

    /**
     * Vrátí roli podle názvu role
     *
     * @param role název role
     * @return role
     */
    Role getRoleByName(String role);

}
