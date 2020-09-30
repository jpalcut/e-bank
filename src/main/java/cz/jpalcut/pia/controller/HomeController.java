package cz.jpalcut.pia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller pro hlavní stránku aplikace
 */
@Controller
@RequestMapping(name = "homeController")
public class HomeController {

    /**
     * Zobrazí hlavní stránku aplikace
     *
     * @return ModelAndView
     */
    @RequestMapping(path = "/", name = "home", method = RequestMethod.GET)
    public ModelAndView showHomePage() {
        return new ModelAndView("home");
    }

}
