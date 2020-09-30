package cz.jpalcut.pia.dao;

import cz.jpalcut.pia.model.Role;
import cz.jpalcut.pia.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Rozhraní pro přístup k uživatelům
 */
@Repository
public interface UserDAO extends JpaRepository<User, Integer> {

    /**
     * Vrátí seznam uživatelů podle omezení a rolí
     *
     * @param roleList seznam rolí uživatele
     * @param pageable omezení pro výběr uživatelů
     * @return stránku uživatelů
     */
    Page<User> findAllByRoleListAndDeleted(List<Role> roleList, Pageable pageable, Boolean deleted);

    /**
     * Vrátí uživatele podle přihlašovacího id
     *
     * @param loginId přihlašovací id uživatele
     * @return uživatel
     */
    User findUserByLoginId(String loginId);

    /**
     * Vrátí uživatele podle id
     *
     * @param id id uživatele
     * @return uživatel
     */
    User findUserById(Integer id);

}
