package cz.jpalcut.pia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller pro přihlašení a odhlášení uživatele
 */
@Controller
@RequestMapping(name = "loginController")
public class LoginController {

    /**
     * Zobrazí stránku pro přihlášení uživatele
     *
     * @param error parametr error GET požadavku
     * @return ModelAndView
     */
    @RequestMapping(path = {"/login"}, name = "login", method = RequestMethod.GET)
    public ModelAndView showLoginPage(@RequestParam(value = "error", required = false) String error) {
        ModelAndView model = new ModelAndView("authentication/login");
        if (error != null) {
            model.addObject("flashMessageSuccess", false);
            model.addObject("flashMessageText", "Byly zadány špatné přihlašovací údaje.");
        }
        return model;
    }

    /**
     * Zobrazí stránku pro úspěšné odhlášení z aplikace
     *
     * @return ModelAndView
     */
    @RequestMapping(path = "/logoutSuccessful", name = "logout", method = RequestMethod.GET)
    public ModelAndView showSuccessFulLogoutPage() {
        ModelAndView model = new ModelAndView("authentication/login");
        model.addObject("flashMessageSuccess", true);
        model.addObject("flashMessageText", "Byl jste úspěšně odhlášen z aplikace.");
        return model;
    }

}
