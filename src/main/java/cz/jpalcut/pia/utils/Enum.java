package cz.jpalcut.pia.utils;

/**
 * Obsahuje výčtové typy enum
 */
public class Enum {

    /**
     * Uživatelské role
     */
    public enum Role {
        ADMIN("ADMIN"),
        USER("USER");

        //uživatelská role
        private String role;

        /**
         * Konstruktor výčtového typu enum pro role uživatele
         *
         * @param role role uživatele
         */
        Role(String role) {
            this.role = role;
        }

        /**
         * Vrací roli uživatele
         *
         * @return role uživatele
         */
        @Override
        public String toString() {
            return role;
        }
    }

    /**
     * Typy požadavků na schválení adminem
     */
    public enum UserRequestType {
        LIMIT_BELOW("limit_below"),
        INTERNATIONAL_PAYMENT("international_payment");

        //typ uživatelské žádosti
        private String type;

        /**
         * Konstruktor výčtového typu enum pro typ žádosti uživatele
         *
         * @param type typ žádosti
         */
        UserRequestType(String type) {
            this.type = type;
        }

        /**
         * Vrací typ žádosti uživatele
         *
         * @return typ žádosti uživatele
         */
        @Override
        public String toString() {
            return type;
        }
    }

    /**
     * Subjekt zprávy
     */
    public enum SubjectType {
        DELETED_USER("e-Banking - smazání účtu"),
        CREATED_USER("e-Banking - vytvoření účtu"),
        EDITED_USER("e-Banking - úprava údajů");

        //typ uživatelské žádosti
        private String subject;

        /**
         * Konstruktor výčtového typu enum pro subjekt zprávy
         *
         * @param subject subjekt
         */
        SubjectType(String subject) {
            this.subject = subject;
        }

        /**
         * Vrací subjekt emailu
         *
         * @return subjekt
         */
        @Override
        public String toString() {
            return subject;
        }
    }

}
