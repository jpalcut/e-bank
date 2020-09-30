package cz.jpalcut.pia.service.interfaces;

import cz.jpalcut.pia.model.Account;
import cz.jpalcut.pia.model.Template;
import cz.jpalcut.pia.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Rozhraní pro službu spravující šablony uživatele
 */
public interface ITemplateService {

    /**
     * Uloží nebo edituje šablonu
     *
     * @param template šablona
     * @param account  bankovní účet
     * @return šablona
     */
    Template saveTemplate(Template template, Account account);

    /**
     * Vrátí všechny šablony podle bankovního účtu
     *
     * @param account bankovní účet
     * @return seznam šablon
     */
    List<Template> getTemplatesByAccount(Account account);

    /**
     * Vrátí šablonu podle id šablony
     *
     * @param id id šablony
     * @return šablona
     */
    Template getTemplateById(Integer id);

    /**
     * Smaže šablonu
     *
     * @param template šablona k smazání
     */
    void deleteTemplate(Template template);

    /**
     * Vrátí stránku šablon k zobrazení podle omezení a bankovního účtu
     *
     * @param account  bankovní účet
     * @param pageable omezení pro výběr šablon
     * @return stránka obsahující šablony
     */
    Page<Template> getTemplatesByAccountPageable(Account account, Pageable pageable);

    /**
     * Ověří zda-li šablona patří k uživateli
     *
     * @param template šablona
     * @param user     uživatel
     * @return true - patří k uživateli, false - nepatří k uživateli
     */
    boolean belongsTemplateToUser(Template template, User user);

}
