package cz.jpalcut.pia.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Třída spravující konfigurace týkající se banky z application.properties
 */
@Configuration
public class BankConfig {

    //kód banky
    @Value("${appvar.bank_code}")
    private String bankCode;

    //url pro stažení kódů bank
    @Value("${appvar.bank_codes_url}")
    private String bankCodesUrl;

    //url pro stažení kódů bank
    @Value("${appvar.info_email}")
    private String infoEmail;

    /**
     * Vrací kód banky
     *
     * @return kód banky
     */
    public String getBankCode() {
        return bankCode;
    }

    /**
     * Vrátí url pro stažení kódů bank
     *
     * @return url
     */
    public String getBankCodesUrl() {
        return bankCodesUrl;
    }

    /**
     * Vrátí email pro posílání oznámení
     *
     * @return email
     */
    public String getInfoEmail(){
        return infoEmail;
    }

}
