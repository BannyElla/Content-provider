package org.levelup.web;

public class AppConstants {
    public static final String DATA_BASE = "ProdDb";
    public static final String TEST_DATA_BASE = "TestDb";

    public static final String ADMIN_LOGIN = "admin";
    public static final String ADMIN_PASSWORD = "123";

    /** Atribute names */
    public static final String FACTORY_ATTRIBUTE = "factory";
    public static final String VERIFIED_USER_NAME_ATTRIBUTE = "verifiedUserName";
    public static final String FORM_ATTRIBUTE = "form";
    public static final String ALL_ARTICLES_ATTRIBUTE = "all_articles";

    /** Parameter names */
    public static final String USER_NAME_PARAMETER = "usernameField";
    public static final String PASSWORD_PARAMETER = "passwordField";

    /** Pages URL */
    public static final String LOGIN_PAGE = "login?login=";

    /** Servlet URL patterns */
    public static final String LOGIN_SERVLET = "/login";
    public static final String REGISTRATION_SERVLET = "/registration";

    /** JSP URL */
    public static final String LOGIN_JSP = "/pages/login.jsp";
    public static final String REGISTRATION_JSP = "/pages/registration.jsp";

    private AppConstants() throws Exception {
        throw new Exception("It's class with constants");
    }
}
