package cz.jpalcut.pia.service;

import cz.jpalcut.pia.config.CaptchaSettings;
import cz.jpalcut.pia.service.interfaces.ICaptchaService;
import cz.jpalcut.pia.utils.CaptchaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import javax.transaction.Transactional;
import java.net.URI;

/**
 * Služba pro kontrolu Google Captcha
 */
@Service
public class CaptchaService implements ICaptchaService {

    private CaptchaSettings captchaSettings;

    private RestOperations restTemplate;

    /**
     * Konstruktor třídy
     *
     * @param captchaSettings CaptchaSettings
     */
    @Autowired
    public CaptchaService(CaptchaSettings captchaSettings, RestOperations restTemplate) {
        this.captchaSettings = captchaSettings;
        this.restTemplate = restTemplate;
    }

    /**
     * Pro ověření Google Captcha
     *
     * @param response        google.recaptcha.key.site
     * @param remoteIpAddress ip adresa uživatele
     * @return true - ověření v pořádku, false - ověření selhalo
     */
    @Override
    public boolean processResponse(String response, String remoteIpAddress) {
        URI verifyUri = URI.create(String.format(
                "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s&remoteip=%s",
                captchaSettings.getSecret(), response, remoteIpAddress));

        CaptchaResponse captchaResponse = restTemplate.getForObject(verifyUri, CaptchaResponse.class);

        return captchaResponse.isSuccess();
    }

}
