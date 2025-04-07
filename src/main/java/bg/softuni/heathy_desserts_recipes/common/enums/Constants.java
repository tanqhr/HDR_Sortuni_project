package bg.softuni.heathy_desserts_recipes.common.enums;

public enum Constants {
    ;
    public static final String BINDING_RESULT_PATH = "org.springframework.validation.BindingResult.";
    public static final String ROLE_PREFIX = "ROLE_";
    public static final String FORMAT_USER_WITH_EMAIL_NOT_FOUND = "User with email: %s not found!";

    public enum Registration {

        ;
        public static final int MIN_USERNAME_LENGTH = 3;
        public static final int MAX_USERNAME_LENGTH= 30;
        public static final int MIN_PASSWORD_LENGTH = 5;
        public static final int MIN_FIRSTNAME_LENGTH = 3;
        public static final int MIN_LASTNAME_LENGTH = 3;
        public static final int MAX_FIRSTNAME_LENGTH = 30;
        public static final int MAX_LASTNAME_LENGTH = 30;

        public static final String EMAIL_REG_EXP = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

        public static final String NOT_BLANK_OR_EMPTY = "should not be blank or empty.";
        public static final String MIN_FIRST_NAME = "First Name length should be minimum " + MIN_FIRSTNAME_LENGTH + " characters.";
        public static final String MIN_LAST_NAME = "Last Name length should be minimum" + MIN_LASTNAME_LENGTH + " characters.";
        public static final String MAX_FIRST_NAME = "First Name length should be minimum " + MIN_FIRSTNAME_LENGTH + " characters.";
        public static final String MAX_LAST_NAME = "Last Name length should be minimum" + MIN_LASTNAME_LENGTH + " characters.";
        public static final String MIN_USERNAME_NAME = "Username length should be minimum " + MIN_USERNAME_LENGTH + " characters.";
        public static final String MAX_USERNAME_NAME = "Username length should be maximum " + MAX_USERNAME_LENGTH + " characters.";
        public static final String NOT_WELL_FORMATTED_EMAIL = "Please  provide well formatted email!";
        public static final String EMAIL_NOT_AVAILABLE = "There is existing registration with this email.";
        public static final String MIN_PASSWORD_LENGTH_MSG = "Password length should be minimum " + MIN_PASSWORD_LENGTH + " characters.";

    }

}
