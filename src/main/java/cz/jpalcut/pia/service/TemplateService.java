package cz.jpalcut.pia.service;

import cz.jpalcut.pia.dao.TemplateDAO;
import cz.jpalcut.pia.model.Account;
import cz.jpalcut.pia.model.Template;
import cz.jpalcut.pia.model.User;
import cz.jpalcut.pia.service.interfaces.ITemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Služba pro správu šablon
 */
@Service
@Transactional
public class TemplateService implements ITemplateService {

    private TemplateDAO templateDAO;

    /**
     * Konstruktor třídy
     *
     * @param templateDAO TemplateDAO
     */
    @Autowired
    public TemplateService(TemplateDAO templateDAO) {
        this.templateDAO = templateDAO;
    }

    /**
     * Uloží nebo edituje šablonu
     *
     * @param template šablona
     * @param account  bankovní účet
     * @return šablona
     */
    @Override
    public Template saveTemplate(Template template, Account account) {
        template.setAccount(account);
        return templateDAO.save(template);
    }

    /**
     * Vrátí všechny šablony podle bankovního účtu
     *
     * @param account bankovní účet
     * @return seznam šablon
     */
    @Override
    public List<Template> getTemplatesByAccount(Account account) {
        return templateDAO.findAllByAccount(account);
    }

    /**
     * Vrátí šablonu podle id šablony
     *
     * @param id id šablony
     * @return šablona
     */
    @Override
    public Template getTemplateById(Integer id) {
        return templateDAO.findTemplateById(id);
    }

    /**
     * Smaže šablonu
     *
     * @param template šablona k smazání
     */
    @Override
    public void deleteTemplate(Template template) {
        templateDAO.delete(template);
    }

    /**
     * Vrátí stránku šablon k zobrazení podle omezení a bankovního účtu
     *
     * @param account  bankovní účet
     * @param pageable omezení pro výběr šablon
     * @return stránka obsahující šablony
     */
    @Override
    public Page<Template> getTemplatesByAccountPageable(Account account, Pageable pageable) {
        return templateDAO.findAllByAccount(account, pageable);
    }

    /**
     * Ověří zda-li šablona patří k uživateli
     *
     * @param template šablona
     * @param user     uživatel
     * @return true - patří k uživateli, false - nepatří k uživateli
     */
    @Override
    public boolean belongsTemplateToUser(Template template, User user) {
        return template.getAccount().getUser().getId().equals(user.getId());
    }

}
