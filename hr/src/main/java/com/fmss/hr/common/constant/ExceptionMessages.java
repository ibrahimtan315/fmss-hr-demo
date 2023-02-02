package com.fmss.hr.common.constant;

public class ExceptionMessages {
    private static final String UTILITY_CLASS = "Utility Class";

    private ExceptionMessages() {
        throw new IllegalArgumentException(UTILITY_CLASS);
    }

    public static final String EXPIRED_OR_INVALID_JWT_TOKEN = "Expired or invalid JWT token";
    public static final String INVALID_USERNAME_OR_PASSWORD_SUPPLIED = "Invalid username/password supplied";
    public static final String USER_NOT_FOUND = "User Not Found !";
    public static final String ROLE_NOT_FOUND = "Role Not Found !";
    public static final String DEPARTMENT_NOT_FOUND = "Department Not Found !";
    public static final String NO_MEETINGS_FOUND = "No upcoming meeting found !";
    public static final String NO_PENDING_EMPLOYEES="No Pending Employee Found";
    public static final String NO_COMMENTS_FOUND_FOR_CANDIDATE ="There is no comment for candidate";
    public static final String NO_COMMENTS_FOUND_FOR_USER = "There is no comment found that entered by user";
}
