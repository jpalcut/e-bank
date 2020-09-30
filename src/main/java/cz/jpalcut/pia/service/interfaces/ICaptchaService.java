package cz.jpalcut.pia.service.interfaces;

/**
 * Rozhraní pro službu spravující Google Captcha
 */
public interface ICaptchaService {

    /**
     * Pro ověření Google Captcha
     *
     * @param response        google.recaptcha.key.site
     * @param remoteIpAddress ip adresa uživatele
     * @return true - ověření v pořádku, false - ověření selhalo
     */
    boolean processResponse(String response, String remoteIpAddress);

}
