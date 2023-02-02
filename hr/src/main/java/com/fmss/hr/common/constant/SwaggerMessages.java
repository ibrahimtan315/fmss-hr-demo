package com.fmss.hr.common.constant;

public class SwaggerMessages {
    private static final String UTILITY_CLASS = "Utility Class";

    private SwaggerMessages() {
        throw new IllegalArgumentException(UTILITY_CLASS);
    }

    public static final String SUCCESSFUL_LOGIN = "Successful login.";

    public static final String UNSUCCESSFUL_LOGIN = "Unsuccessful login.";

    public static final String FORBIDDEN = "Accessing the resource you were trying to reach is forbidden.";

    public static final String SUCCESSFUL_SIGNUP = "Successful sign up.";

    public static final String UNSUCCESSFUL_SIGNUP = "Unsuccessful sign up.";
}
