package cz.jpalcut.pia.service.interfaces;

import cz.jpalcut.pia.model.User;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Rozhraní pro posílání emailů
 */
public interface IMailSenderService {

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
    MimeMessageHelper setMimeMessageHelper(MimeMessage message, String to, String text, String subject) throws MessagingException;

    /**
     * Odeslání emailu o vytvoření účtu
     *
     * @param user uživatel
     * @param pin  pin uživatele
     * @return true - email byl odeslán, false - nastala chyba
     */
    boolean sendRegisteredUserMail(User user, String pin);

    /**
     * Odeslání emailu o smazání účtu
     *
     * @param user uživatel
     * @return true - email byl odeslán, false - nastala chyba
     */
    boolean sendDeletedUserMail(User user);

    /**
     * Odeslání emailu o editaci dat uživatele
     *
     * @param user uživatel
     * @return true - email byl odeslán, false - nastala chyba
     */
    boolean sendEditedUserMail(User user);
}
