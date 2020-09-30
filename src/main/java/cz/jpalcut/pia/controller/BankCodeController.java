package cz.jpalcut.pia.controller;

import cz.jpalcut.pia.model.BankCode;
import cz.jpalcut.pia.service.BankCodeService;
import cz.jpalcut.pia.service.interfaces.IBankCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller pro správu bankovních kódů
 */
@Controller
@RequestMapping(name = "bankCodeController")
public class BankCodeController {

    private IBankCodeService bankCodeService;

    /**
     * Konstruktor
     *
     * @param bankCodeService BankCodeService
     */
    @Autowired
    public BankCodeController(BankCodeService bankCodeService) {
        this.bankCodeService = bankCodeService;
    }

    /**
     * Zobrazí seznam bankovních kódů
     *
     * @param pageable omezení pro zobrazení bankovních kódů
     * @return ModelAndView
     */
    @RequestMapping(path = "/bankcode/list", name = "list", method = RequestMethod.GET)
    public ModelAndView showBankCodesPage(Pageable pageable) {
        ModelAndView model = new ModelAndView("bank_code/list");
        Page<BankCode> pages = bankCodeService.getBankCodesPageable(pageable);
        model.addObject("pagination", pages);
        model.addObject("bankCodes", pages.getContent());
        return model;
    }

    /**
     * Aktualizuje seznam bankovních kódů
     *
     * @param redirectAttributes pro přenos objektů na stránku přesměrování
     * @return ModelAndView
     */
    @RequestMapping(path = "/bankcode/update", name = "update", method = RequestMethod.GET)
    public ModelAndView updateBankCodes(RedirectAttributes redirectAttributes) {
        ModelAndView model = new ModelAndView("redirect:/bankcode/list");
        if (bankCodeService.updateBankCodes()) {
            redirectAttributes.addFlashAttribute("flashMessageSuccess", true);
            redirectAttributes.addFlashAttribute("flashMessageText", "Aktualizace proběhla v pořádku.");
            return model;
        }
        redirectAttributes.addFlashAttribute("flashMessageSuccess", false);
        redirectAttributes.addFlashAttribute("flashMessageText", "Při aktualizaci nastala chyba.");
        return model;
    }

}
