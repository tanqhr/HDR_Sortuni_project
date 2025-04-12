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



    }

}
