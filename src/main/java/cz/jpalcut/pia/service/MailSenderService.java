package cz.jpalcut.pia.service;

import cz.jpalcut.pia.config.BankConfig;
import cz.jpalcut.pia.model.User;
import cz.jpalcut.pia.service.interfaces.IMailSenderService;
import cz.jpalcut.pia.utils.Enum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Služba pro posílání emailů
 */
@Service
public class MailSenderService implements IMailSenderService {

    private JavaMailSender mailSender;

    private BankConfig bankConfig;

    @Autowired
    public MailSenderService(JavaMailSender mailSender, BankConfig bankConfig) {
        this.mailSender = mailSender;
        this.bankConfig = bankConfig;
    }

    /**
     * Nastavení parametrů MimeMessageHelper pro odeslání emailu
     *
     * @param message zpráva pro nastavení
     * @param to      příjemce
     * @param text    tělo zprávy
     * @param subject subjekt zprávy
     * @return MimeMessageHelper
     * @throws MessagingException chyba při nastavování MimeMessageHelper
     */
    @Override
    public MimeMessageHelper setMimeMessageHelper(MimeMessage message, String to, String text, String subject) throws MessagingException {

        MimeMessageHelper helper = new MimeMessageHelper(message);

        String headerText = "Vážený zákazníku,<br><br>";

        String footerText = "<br><br>e-Banking<br>Kontakt: contact@e-banking.cz<br>Zpráva je generována automaticky - neodpovídat<br>";

        text = headerText + text + footerText;

        helper.setTo(to);
        helper.setText(text, true);
        helper.setSubject(subject);
        helper.setFrom(bankConfig.getInfoEmail());

        return helper;
    }

    /**
     * Odeslání emailu o vytvoření účtu
     *
     * @param user uživatel
     * @param pin  pin uživatele
     * @return true - email byl odeslán, false - nastala chyba
     */
    @Override
    public boolean sendRegisteredUserMail(User user, String pin) {
        MimeMessage message = mailSender.createMimeMessage();
        String subject = Enum.SubjectType.valueOf("CREATED_USER").toString();

        String text = "byl Vám vytvořen uživatelský účet u banky e-Banking.<br>" +
                "<br>přihlašovací login: " + user.getLoginId() +
                "<br>heslo: " + pin;

        try {
            MimeMessageHelper helper = setMimeMessageHelper(message, user.getEmail(), text, subject);
            mailSender.send(message);
        } catch (MessagingException e) {
            return false;
        }
        return true;
    }

    /**
     * Odeslání emailu o smazání účtu
     *
     * @param user uživatel
     * @return true - email byl odeslán, false - nastala chyba
     */
    @Override
    public boolean sendDeletedUserMail(User user) {
        MimeMessage message = mailSender.createMimeMessage();
        String subject = Enum.SubjectType.valueOf("DELETED_USER").toString();

        String text = "uživatelský účet s přihlašovacím údajem " + user.getLoginId() + " byl zrušen.";

        try {
            MimeMessageHelper helper = setMimeMessageHelper(message, user.getEmail(), text, subject);
            mailSender.send(message);
        } catch (MessagingException e) {
            return false;
        }
        return true;
    }

    /**
     * Odeslání emailu o editaci dat uživatele
     *
     * @param user uživatel
     * @return true - email byl odeslán, false - nastala chyba
     */
    @Override
    public boolean sendEditedUserMail(User user) {
        MimeMessage message = mailSender.createMimeMessage();
        String subject = Enum.SubjectType.valueOf("EDITED_USER").toString();

        String text = "došlo ke změně Vašich osobních údajů administrátorem. Aktuální vaše osobní údaje jsou:<br>" +
                "<br>Křestní jméno: " + user.getFirstname() +
                "<br>Příjmení: " + user.getLastname() +
                "<br>Rodné číslo: " + user.getPid() +
                "<br>Email: " + user.getEmail() +
                "<br>Adresa: " + user.getAddress() +
                "<br>Číslo popisné: " + user.getAddressNumber() +
                "<br>Město: " + user.getTown() +
                "<br>Stát: " + user.getState().getName() +
                "<br>PSČ: " + user.getZipCode() +
                "<br><br>V případě nesrovnalosti některých z údajů nás kontaktujte.";

        try {
            MimeMessageHelper helper = setMimeMessageHelper(message, user.getEmail(), text, subject);
            mailSender.send(message);
        } catch (MessagingException e) {
            return false;
        }
        return true;
    }

}
