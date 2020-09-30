package cz.jpalcut.pia.controller;

import cz.jpalcut.pia.config.BankConfig;
import cz.jpalcut.pia.model.User;
import cz.jpalcut.pia.service.AccountService;
import cz.jpalcut.pia.service.CaptchaService;
import cz.jpalcut.pia.service.StateService;
import cz.jpalcut.pia.service.UserService;
import cz.jpalcut.pia.service.interfaces.IAccountService;
import cz.jpalcut.pia.service.interfaces.ICaptchaService;
import cz.jpalcut.pia.service.interfaces.IStateService;
import cz.jpalcut.pia.service.interfaces.IUserService;
import cz.jpalcut.pia.utils.Enum;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Controller pro správu uživatelů
 */
@Controller
@RequestMapping(name = "userController")
public class UserController {

    private IStateService stateService;

    private IUserService userService;

    private BankConfig bankConfig;

    private IAccountService accountService;

    private ICaptchaService captchaService;

    /**
     * Konstruktor třídy
     *
     * @param stateService   StateService
     * @param userService    UserService
     * @param bankConfig     BankConfig
     * @param accountService AccountService
     * @param captchaService CaptchaService
     */
    @Autowired
    public UserController(StateService stateService, UserService userService, BankConfig bankConfig,
                          AccountService accountService, CaptchaService captchaService) {

        this.stateService = stateService;
        this.userService = userService;
        this.accountService = accountService;
        this.captchaService = captchaService;
        this.bankConfig = bankConfig;
    }

    /**
     * Zobrazí stránku s osobními údaji přihlášeného uživatele
     *
     * @return ModelAndView
     */
    @RequestMapping(path = "/user", name = "user", method = RequestMethod.GET)
    public ModelAndView showUserPage() {
        ModelAndView model = new ModelAndView("user/profile");
        model.addObject("states", stateService.getAllStates());
        model.addObject("userForm", userService.getUser());
        return model;
    }

    /**
     * Upraví údaje přihlašeného uživatele
     *
     * @param user          údaje z formuláře pro editaci
     * @param bindingResult vyhodnicení údajů z formuláře validací
     * @return ModelAndView
     */
    @RequestMapping(path = "/user/edit", name = "edit", method = RequestMethod.POST)
    public ModelAndView editUser(@Valid @ModelAttribute("userForm") User user, BindingResult bindingResult) {
        ModelAndView model = new ModelAndView("user/profile");
        model.addObject("states", stateService.getAllStates());

        if (bindingResult.hasErrors()) {
            model.addObject("flashMessageSuccess", false);
            model.addObject("flashMessageText", "Nastala chyba při vyplnění formuláře.");
            return model;
        }

        //úprava uživatele
        if (userService.editUser(userService.getUser(), user) == null) {
            model.addObject("flashMessageSuccess", false);
            model.addObject("flashMessageText", "Nastala chyba s datovým uložištěm, opakujte akci později.");
            return model;
        }

        model.addObject("flashMessageSuccess", true);
        model.addObject("flashMessageText", "Uživatelský profil byl upraven.");
        return model;
    }

    /**
     * Zobrazí stránku s seznamem uživatelů
     *
     * @param pageable omezuje zobrazení na počet elementů a číslo stránky
     * @return ModelAndView
     */
    @RequestMapping(path = "/user/list", name = "list", method = RequestMethod.GET)
    public ModelAndView showUserListPage(Pageable pageable) {
        ModelAndView model = new ModelAndView("user/list");
        Page<User> pages = userService.getAllUsersByRolePageable(Enum.Role.valueOf("USER").toString(), pageable);
        model.addObject("pagination", pages);
        model.addObject("users", pages.getContent());
        return model;
    }

    /**
     * Zobrazí stránku s osobními údaji pro účel admina podle id uživatele
     *
     * @param userId             id uživatele
     * @param redirectAttributes pro přenos objektů na stránku přesměrování
     * @return ModelAndView
     */
    @RequestMapping(path = "/user/{id}", name = "id", method = RequestMethod.GET)
    public ModelAndView showUserDetailPage(@PathVariable("id") Integer userId, RedirectAttributes redirectAttributes) {
        User user = userService.getUserById(userId);
        ModelAndView model = new ModelAndView("user/edit");

        //kontrola existence uživatele
        if (user == null || userService.isDeletedUser(user)) {
            model.setViewName("redirect:/user/list");
            redirectAttributes.addFlashAttribute("flashMessageSuccess", false);
            redirectAttributes.addFlashAttribute("flashMessageText", "Uživatel neexistuje.");
        }

        model.addObject("states", stateService.getAllStates());
        model.addObject("userForm", user);
        model.addObject("account", accountService.getAccount(user));
        model.addObject("bankCode", bankConfig.getBankCode());
        return model;
    }

    /**
     * Upraví údaje uživatele adminem podle id uživatele
     *
     * @param newUser            data z formuláře k úpravě uživatele
     * @param bindingResult      vyhodnicení údajů z formuláře validací
     * @param userId             id uživatele
     * @param redirectAttributes pro přenos objektů na stránku přesměrování
     * @return ModelAndView
     */
    @RequestMapping(path = "/user/edit/{id}", name = "edit-id", method = RequestMethod.POST)
    public ModelAndView editUserByAdmin(@Valid @ModelAttribute("userForm") User newUser, BindingResult bindingResult,
                                        @PathVariable("id") Integer userId, RedirectAttributes redirectAttributes) {

        User user = userService.getUserById(userId);
        ModelAndView model = new ModelAndView("user/edit");

        //kontrola existence uživatele
        if (user == null || userService.isDeletedUser(user)) {
            model.setViewName("redirect:/user/list");
            redirectAttributes.addFlashAttribute("flashMessageSuccess", false);
            redirectAttributes.addFlashAttribute("flashMessageText", "Uživatel neexistuje.");
        }

        model.addObject("states", stateService.getAllStates());
        model.addObject("account", accountService.getAccount(user));
        model.addObject("bankCode", bankConfig.getBankCode());

        if (bindingResult.hasErrors()) {
            model.addObject("flashMessageSuccess", false);
            model.addObject("flashMessageText", "Nastala chyba při vyplnění formuláře.");
            return model;
        }

        //úprava uživatele
        if (userService.editUserByAdmin(user, newUser) == null) {
            model.addObject("flashMessageSuccess", false);
            model.addObject("flashMessageText", "Nastala chyba s datovým uložištěm, opakujte akci později.");
            return model;
        }

        model.addObject("flashMessageSuccess", true);
        model.addObject("flashMessageText", "Uživatel byl úspěšně upraven.");
        return model;
    }

    /**
     * Zobrazí stránku pro přidání nového uživatele adminem
     *
     * @return ModelAndView
     */
    @RequestMapping(path = "/user/new", name = "new", method = RequestMethod.GET)
    public ModelAndView showNewUserPage() {
        ModelAndView model = new ModelAndView("user/new");
        model.addObject("states", stateService.getAllStates());
        model.addObject("userForm", new User());
        return model;
    }

    /**
     * Přidá nového uživatele
     *
     * @param user          údaje uživatele z formuláře pro přidání uživatele
     * @param bindingResult vyhodnicení údajů z formuláře validací
     * @param request       požadavek na server
     * @return ModelAndView
     */
    @RequestMapping(path = "/user/new/add", name = "new-add", method = RequestMethod.POST)
    public ModelAndView saveNewUser(@Valid @ModelAttribute("userForm") User user, BindingResult bindingResult,
                                    HttpServletRequest request) {
        ModelAndView model = new ModelAndView("user/new");
        model.addObject("states", stateService.getAllStates());

        if (bindingResult.hasErrors()) {
            model.addObject("flashMessageSuccess", false);
            model.addObject("flashMessageText", "Nastala chyba při vyplnění formuláře.");
            return model;
        }

        if (!captchaService.processResponse(request.getParameter("g-recaptcha-response"), request.getRemoteAddr())) {
            model.addObject("flashMessageSuccess", false);
            model.addObject("flashMessageText", "Nastala chyba při ověření formuláře - Google reCAPTCHA ");
            return model;
        }

        //přidání uživatele
        if (userService.addUser(user) == null) {
            model.addObject("flashMessageSuccess", false);
            model.addObject("flashMessageText", "Nastala chyba s datovým uložištěm, opakujte akci později.");
            return model;
        }

        model.addObject("flashMessageSuccess", true);
        model.addObject("flashMessageText", "Uživatel byl přidán.");
        model.addObject("userForm", new User());
        return model;
    }

    @RequestMapping(path = "/user/delete/{id}", name = "id-delete", method = RequestMethod.GET)
    public ModelAndView deleteUser(@PathVariable("id") Integer userId, RedirectAttributes redirectAttributes) {

        ModelAndView model = new ModelAndView("redirect:/user/list");

        User user = userService.getUserById(userId);

        //kontrola existence uživatele
        if (user == null || userService.isDeletedUser(user)) {
            redirectAttributes.addFlashAttribute("flashMessageSuccess", false);
            redirectAttributes.addFlashAttribute("flashMessageText", "Uživatel neexistuje.");
            return model;
        }

        userService.deleteUser(user);

        redirectAttributes.addFlashAttribute("flashMessageSuccess", true);
        redirectAttributes.addFlashAttribute("flashMessageText", "Uživatel byl smazán.");
        return model;
    }

}
