package cz.jpalcut.pia.controller;

import cz.jpalcut.pia.model.Account;
import cz.jpalcut.pia.model.Template;
import cz.jpalcut.pia.model.User;
import cz.jpalcut.pia.service.AccountService;
import cz.jpalcut.pia.service.BankCodeService;
import cz.jpalcut.pia.service.TemplateService;
import cz.jpalcut.pia.service.UserService;
import cz.jpalcut.pia.service.interfaces.IAccountService;
import cz.jpalcut.pia.service.interfaces.IBankCodeService;
import cz.jpalcut.pia.service.interfaces.ITemplateService;
import cz.jpalcut.pia.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Controller pro správu šablon
 */
@Controller
@RequestMapping(name = "templateController")
public class TemplateController {

    private IUserService userService;

    private IAccountService accountService;

    private ITemplateService templateService;

    private IBankCodeService bankCodeService;

    /**
     * Konstruktor třídy
     *
     * @param userService     UserService
     * @param accountService  AccountService
     * @param templateService TemplateService
     */
    @Autowired
    public TemplateController(UserService userService, AccountService accountService, TemplateService templateService,
                              BankCodeService bankCodeService) {
        this.userService = userService;
        this.accountService = accountService;
        this.templateService = templateService;
        this.bankCodeService = bankCodeService;
    }

    /**
     * Zobrazí stránku s seznamem šablon
     *
     * @param pageable omezuje zobrazení na počet elementů a číslo stránky
     * @return ModelAndView
     */
    @RequestMapping(path = "/template/list", name = "list", method = RequestMethod.GET)
    public ModelAndView showTemplatesPage(Pageable pageable) {
        Account account = accountService.getAccount(userService.getUser());
        ModelAndView model = new ModelAndView("template/list");
        Page<Template> pages = templateService.getTemplatesByAccountPageable(account, pageable);
        model.addObject("pagination", pages);
        model.addObject("templates", pages.getContent());
        return model;
    }

    /**
     * Zobrazí stránku pro vytvoření nové šablony
     *
     * @return ModelAndView
     */
    @RequestMapping(path = "/template/new", name = "new", method = RequestMethod.GET)
    public ModelAndView showAddTemplatePage() {
        ModelAndView model = new ModelAndView("template/new");
        model.addObject("template", new Template());
        model.addObject("bankCodes", bankCodeService.getBankCodes());
        return model;
    }

    /**
     * Přidá novou šablonu
     *
     * @param template      data z formuláře pro vytvoření nové šablony
     * @param bindingResult vyhodnicení údajů z formuláře validací
     * @return ModelAndView
     */
    @RequestMapping(path = "/template/new/add", name = "new-add", method = RequestMethod.POST)
    public ModelAndView addNewTemplate(@Valid @ModelAttribute("template") Template template, BindingResult bindingResult) {
        ModelAndView model = new ModelAndView("template/new");
        Account account = accountService.getAccount(userService.getUser());
        model.addObject("bankCodes", bankCodeService.getBankCodes());

        if (bindingResult.hasErrors()) {
            model.addObject("flashMessageSuccess", false);
            model.addObject("flashMessageText", "Nastala chyba při vyplnění formuláře.");
            return model;
        }

        //uložení šablony
        if (templateService.saveTemplate(template, account) == null) {
            model.addObject("flashMessageSuccess", false);
            model.addObject("flashMessageText", "Nastala chyba s datovým uložištěm, opakujte akci později.");
            return model;
        }

        model.addObject("flashMessageSuccess", true);
        model.addObject("flashMessageText", "Šablona byla vytvořena.");
        model.addObject("template", new Template());
        return model;
    }

    /**
     * Zobrazí stránku s detailem šablony
     *
     * @param templateId         id šablony
     * @param redirectAttributes pro přenos objektů na stránku přesměrování
     * @return ModelAndView
     */
    @RequestMapping(path = "/template/{id}", name = "id", method = RequestMethod.GET)
    public ModelAndView showEditTemplatePage(@PathVariable("id") Integer templateId, RedirectAttributes redirectAttributes) {

        ModelAndView model = new ModelAndView("redirect:/template/list");
        Template template = templateService.getTemplateById(templateId);

        //kontrola existence šablony
        if (template == null) {
            redirectAttributes.addFlashAttribute("flashMessageSuccess", false);
            redirectAttributes.addFlashAttribute("flashMessageText", "Šablona neexistuje.");
            return model;
        }

        //ověření uživatele pro zobrazení
        if (!templateService.belongsTemplateToUser(template, userService.getUser())) {
            redirectAttributes.addFlashAttribute("flashMessageSuccess", false);
            redirectAttributes.addFlashAttribute("flashMessageText", "Nepovolený požadavek.");
            return model;
        }

        model.setViewName("template/edit");
        model.addObject("bankCodes", bankCodeService.getBankCodes());
        model.addObject("template", template);

        return model;
    }

    /**
     * Upraví šablonu
     *
     * @param newTemplate        data z formuláře pro úpravu šablony
     * @param bindingResult      vyhodnicení údajů z formuláře validací
     * @param templateId         id šablony
     * @param redirectAttributes pro přenos objektů na stránku přesměrování
     * @return ModelAndView
     */
    @RequestMapping(path = "/template/edit/{id}", name = "id-edit", method = RequestMethod.POST)
    public ModelAndView editTemplate(@Valid @ModelAttribute("template") Template newTemplate, BindingResult bindingResult,
                                     @PathVariable("id") Integer templateId, RedirectAttributes redirectAttributes) {

        ModelAndView model = new ModelAndView("template/edit");
        model.addObject("bankCodes", bankCodeService.getBankCodes());
        Account account = accountService.getAccount(userService.getUser());

        if (bindingResult.hasErrors()) {
            model.addObject("flashMessageSuccess", false);
            model.addObject("flashMessageText", "Nastala chyba při vyplnění formuláře.");
            return model;
        }

        Template template = templateService.getTemplateById(templateId);

        //kontrola existence šablony
        if (template == null) {
            model.setViewName("redirect:/template/list");
            redirectAttributes.addFlashAttribute("flashMessageSuccess", false);
            redirectAttributes.addFlashAttribute("flashMessageText", "Šablona neexistuje.");
            return model;
        }

        //ověření uživatele pro zobrazení
        if (!templateService.belongsTemplateToUser(template, userService.getUser())) {
            model.setViewName("redirect:/template/list");
            redirectAttributes.addFlashAttribute("flashMessageSuccess", false);
            redirectAttributes.addFlashAttribute("flashMessageText", "Nepovolený požadavek.");
            return model;
        }

        //upravení šablony
        if (templateService.saveTemplate(newTemplate, account) == null) {
            redirectAttributes.addFlashAttribute("flashMessageSuccess", false);
            redirectAttributes.addFlashAttribute("flashMessageText", "Nastala chyba s datovým uložištěm, opakujte akci později.");
            return model;
        }

        model.addObject("flashMessageSuccess", true);
        model.addObject("flashMessageText", "Šablona byla upravena.");
        return model;
    }

    /**
     * Smaže šablonu
     *
     * @param templateId         id šablony
     * @param redirectAttributes pro přenos objektů na stránku přesměrování
     * @return ModelAndView
     */
    @RequestMapping(path = "/template/delete/{id}", name = "id-delete", method = RequestMethod.GET)
    public ModelAndView deleteTemplate(@PathVariable("id") Integer templateId, RedirectAttributes redirectAttributes) {

        ModelAndView model = new ModelAndView("redirect:/template/list");

        Template template = templateService.getTemplateById(templateId);
        User user = userService.getUser();

        if (template == null) {
            redirectAttributes.addFlashAttribute("flashMessageSuccess", false);
            redirectAttributes.addFlashAttribute("flashMessageText", "Šablona neexistuje.");
            return model;
        }

        //ověření uživatele
        if (templateService.belongsTemplateToUser(template, user)) {
            templateService.deleteTemplate(template);
        } else {
            redirectAttributes.addFlashAttribute("flashMessageSuccess", false);
            redirectAttributes.addFlashAttribute("flashMessageText", "Nepovolený požadavek.");
            return model;
        }

        redirectAttributes.addFlashAttribute("flashMessageSuccess", true);
        redirectAttributes.addFlashAttribute("flashMessageText", "Šablona byla smazána.");
        return model;
    }

}
