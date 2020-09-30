package cz.jpalcut.pia.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Random;

/**
 * Třída obsahující užitečné metody pro aplikaci
 */
public class Utils {

    /**
     * Generuje číselný řetězec podle zadané délky
     *
     * @param length délka k generování
     * @return vygenerovaný číselný řetězec
     */
    public static String generateNumber(int length) {
        Random r = new Random();
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < length; i++) {
            number.append(r.nextInt(10));
        }
        return number.toString();
    }

    /**
     * Vytváří hash pomocí BCryptPasswordEncoder
     *
     * @param password řetězec k hashování
     * @return hash
     */
    public static String hashPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    /**
     * Zkontroluje jestli je řetězec číslo
     *
     * @param string řetězec
     * @return true - je číslo, false - není číslo
     */
    public static boolean isNumber(String string) {
        try {
            Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

}
