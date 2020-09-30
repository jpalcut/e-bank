package cz.jpalcut.pia.controller;

import cz.jpalcut.pia.model.Account;
import cz.jpalcut.pia.model.Template;
import cz.jpalcut.pia.model.Transaction;
import cz.jpalcut.pia.model.User;
import cz.jpalcut.pia.service.*;
import cz.jpalcut.pia.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Controller pro správu transakcí
 */
@Controller
@RequestMapping(name = "transactionController")
public class TransactionController {

    private ITransactionService transactionService;

    private IUserService userService;

    private IAccountService accountService;

    private ITemplateService templateService;

    private ICaptchaService captchaService;

    private IBankCodeService bankCodeService;

    /**
     * Konstruktor třídy
     *
     * @param transactionService TransactionService
     * @param userService        UserService
     * @param accountService     AccountService
     * @param templateService    TemplateService
     * @param captchaService     CaptchaService
     */
    @Autowired
    public TransactionController(TransactionService transactionService, UserService userService,
                                 AccountService accountService, TemplateService templateService,
                                 CaptchaService captchaService, BankCodeService bankCodeService) {

        this.transactionService = transactionService;
        this.userService = userService;
        this.accountService = accountService;
        this.templateService = templateService;
        this.captchaService = captchaService;
        this.bankCodeService = bankCodeService;
    }

    /**
     * Zobrazí stránku s formulářem pro vytvoření nové transakce
     *
     * @return ModelAndView
     */
    @RequestMapping(path = "/transaction/new", name = "new", method = RequestMethod.GET)
    public ModelAndView showNewTransactionPage() {
        ModelAndView model = new ModelAndView("transaction/new");
        Account account = accountService.getAccount(userService.getUser());
        model.addObject("transaction", new Transaction());
        model.addObject("templates", templateService.getTemplatesByAccount(account));
        model.addObject("bankCodes", bankCodeService.getBankCodes());
        return model;
    }

    /**
     * Přidá novou transakci
     *
     * @param transaction   data z formuláře pro vytvoření nové transakce
     * @param bindingResult vyhodnicení údajů z formuláře validací
     * @param request       požadavek na server
     * @return ModelAndView
     */
    @RequestMapping(path = "/transaction/new/add", name = "new-add", method = RequestMethod.POST)
    public ModelAndView addNewTransaction(@Valid @ModelAttribute("transaction") Transaction transaction,
                                          BindingResult bindingResult, HttpServletRequest request) {

        ModelAndView model = new ModelAndView("transaction/new");
        Account account = accountService.getAccount(userService.getUser());
        model.addObject("templates", templateService.getTemplatesByAccount(account));
        model.addObject("bankCodes", bankCodeService.getBankCodes());

        if (bindingResult.hasErrors()) {
            model.addObject("flashMessageSuccess", false);
            model.addObject("flashMessageText", "Nastala chyba při vyplnění formuláře.");
            return model;
        }
        //ověření google captcha
        if (!captchaService.processResponse(request.getParameter("g-recaptcha-response"), request.getRemoteAddr())) {
            model.addObject("flashMessageSuccess", false);
            model.addObject("flashMessageText", "Nastala chyba při ověření formuláře - Google reCAPTCHA ");
            return model;
        }
        //validace data splatnosti
        if (!transactionService.isValidDueDate(transaction.getDueDate())) {
            model.addObject("flashMessageSuccess", false);
            model.addObject("flashMessageText", "Datum splatnosti nesmí být v minulosti");
            return model;
        }
        //zakazání poslání peněz na vlastní účet
        if (transactionService.isAccountEqual(account, transaction.getNumber(), transaction.getCode())) {
            model.addObject("flashMessageSuccess", false);
            model.addObject("flashMessageText", "Nemůžete poslat peníze na vlastní účet.");
            return model;
        }
        //kontrola stavu peněž na účtu po odečtení částky transakce
        if (!transactionService.hasMoneyToExecuteTransaction(account, transaction.getValue())) {
            model.addObject("flashMessageSuccess", false);
            model.addObject("flashMessageText", "Nemáte dostatek peněz na účtu.");
            return model;

        }
        if (transaction.checkAccount()) {
            if (transactionService.isLocalNonExistentAccount(transaction.getNumber(), transaction.getCode())) {
                model.addObject("flashMessageSuccess", false);
                model.addObject("flashMessageText", "Zvolený účet v naší bance neexistuje.");
                return model;
            }
            transaction = transactionService.addTransaction(transaction, true);
        } else {
            transaction = transactionService.addTransaction(transaction, false);
        }
        //kontrola provedení transakce
        if (transaction == null) {
            model.addObject("flashMessageSuccess", false);
            model.addObject("flashMessageText", "Nastala chyba s datovým uložištěm, opakujte akci později.");
            return model;
        }
        model.addObject("flashMessageSuccess", true);
        model.addObject("flashMessageText", "Byla přijata transakce ke zpracování.");
        model.addObject("transaction", new Transaction());
        return model;
    }

    /**
     * Zobrazí stránku se seznam transakcí
     *
     * @param pageable omezuje zobrazení na počet elementů a číslo stránky
     * @return ModelAndView
     */
    @RequestMapping(path = "/transaction/list", name = "list", method = RequestMethod.GET)
    public ModelAndView showTransactionListPage(Pageable pageable) {
        ModelAndView model = new ModelAndView("transaction/list");
        Account account = accountService.getAccount(userService.getUser());
        Page<Transaction> pages = transactionService.getTransactionsByAccountPageable(account, pageable);
        model.addObject("pagination", pages);
        model.addObject("transactions", pages.getContent());
        return model;
    }

    /**
     * Zobrazí stránku s detailem transakce
     *
     * @param transactionId      id transakce
     * @param redirectAttributes pro přenos objektů na stránku přesměrování
     * @return ModelAndView
     */
    @RequestMapping(path = "/transaction/detail/{id}", name = "id-detail", method = RequestMethod.GET)
    public ModelAndView showTransactionDetailPage(@PathVariable("id") Integer transactionId, RedirectAttributes redirectAttributes) {
        ModelAndView model = new ModelAndView("transaction/detail");
        Transaction transaction = transactionService.getTransactionById(transactionId);
        User user = userService.getUser();

        //kontrola existence šablony
        if (transaction == null) {
            model.setViewName("redirect:/transaction/list");
            redirectAttributes.addFlashAttribute("flashMessageSuccess", false);
            redirectAttributes.addFlashAttribute("flashMessageText", "Transakce neexistuje.");
            return model;
        }

        //ověření uživatele pro zobrazení
        if (!transactionService.belongsTransactionToUser(transaction, user)) {
            model.setViewName("redirect:/transaction/list");
            redirectAttributes.addFlashAttribute("flashMessageSuccess", false);
            redirectAttributes.addFlashAttribute("flashMessageText", "Nepovolený požadavek.");
            return model;
        }

        model.addObject("transaction", transaction);
        return model;
    }

    /**
     * Zobrazí stránku pro vytvoření transakce se předvyplněnými údaji z šablony
     *
     * @param templateId         id šablony
     * @param redirectAttributes pro přenos objektů na stránku přesměrování
     * @return ModelAndView
     */
    @RequestMapping(path = "/transaction/new/{id}", name = "new-id", method = RequestMethod.GET)
    public ModelAndView showNewTemplateTransactionPage(@PathVariable("id") Integer templateId, RedirectAttributes redirectAttributes) {
        ModelAndView model = new ModelAndView("transaction/new");
        Account account = accountService.getAccount(userService.getUser());
        Template template = templateService.getTemplateById(templateId);

        //kontrola existence šablony
        if (template == null) {
            model.setViewName("redirect:/transaction/new");
            redirectAttributes.addFlashAttribute("flashMessageSuccess", false);
            redirectAttributes.addFlashAttribute("flashMessageText", "Šablona neexistuje.");
            return model;
        }

        //ověření uživatele pro použití šablony
        if (!templateService.belongsTemplateToUser(template, account.getUser())) {
            model.setViewName("redirect:/transaction/new");
            redirectAttributes.addFlashAttribute("flashMessageSuccess", false);
            redirectAttributes.addFlashAttribute("flashMessageText", "Nepovolený požadavek.");
            return model;
        }

        model.addObject("transaction", transactionService.convertTemplateToTransaction(template));
        model.addObject("templates", templateService.getTemplatesByAccount(account));
        model.addObject("bankCodes", bankCodeService.getBankCodes());
        model.addObject("template", template);
        return model;
    }

    /**
     * Zkontroluje existenci bankovního účtu v bance
     *
     * @param number   číslo účtu
     * @param bankCode bankovní kód
     * @return true - účet existuje, false - účet neexistuje
     */
    @RequestMapping(value = "/transaction/check-account", method = RequestMethod.GET)
    @ResponseBody
    public boolean notLocalBankAccount(@RequestParam String number, @RequestParam String bankCode) {
        if (number == null || bankCode == null) {
            return false;
        }
        if (!transactionService.isLocalBankCode(bankCode)) {
            return false;
        }
        return accountService.getAccountByNumber(number) == null;
    }


}
