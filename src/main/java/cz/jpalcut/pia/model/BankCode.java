package cz.jpalcut.pia.model;

import cz.jpalcut.pia.utils.Utils;

import javax.persistence.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "public", name = "bank_code")
public class BankCode {

    @Id
    @Column(name = "code")
    private String bankCode;

    @Column(name = "name")
    private String name;

    /**
     * Konstruktor
     */
    public BankCode() {
    }

    /**
     * Konstruktor
     *
     * @param name     název banky
     * @param bankCode kód anky
     */
    public BankCode(String name, String bankCode) {
        this.name = name;
        this.bankCode = bankCode;
    }

    /**
     * Vrátí název banky
     *
     * @return název banky
     */
    public String getName() {
        return name;
    }

    /**
     * Změní název banky
     *
     * @param name název banky
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Vrátí kód banky
     *
     * @return kód banky
     */
    public String getBankCode() {
        return bankCode;
    }

    /**
     * Změní kód banky
     *
     * @param bankCode kód banky
     */
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    /**
     * Načte kódy bank
     *
     * @param sourceUrl zdroj dat
     * @return seznam bankovních kódů
     */
    public List<BankCode> loadBankCodes(String sourceUrl) {
        String line;
        String[] columns;
        BankCode bankCode;
        ArrayList<BankCode> bankCodes = new ArrayList<>();

        try {

            URL url = new URL(sourceUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));

                while ((line = reader.readLine()) != null) {
                    columns = line.split(";");

                    if (!Utils.isNumber(columns[0])) {
                        continue;
                    }

                    bankCode = new BankCode(columns[1], columns[0]);
                    bankCodes.add(bankCode);
                }

                reader.close();
            }

        } catch (Exception e) {
            return null;
        }

        return bankCodes;
    }
}
