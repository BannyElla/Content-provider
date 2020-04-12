package org.levelup.web;

public class AppConstants {
    public static final String DATA_BASE = "ProdDb";
    public static final String TEST_DATA_BASE = "TestDb";

    public static final String ADMIN_LOGIN = "admin";
    public static final String ADMIN_PASSWORD = "123";

    public static final String LOGIN = "login";
    public static final String REDIRECT = "redirect:/";
    public static final String REGISTRATION = "registration";
    private static final String PAGES_CATALOG = "pages";
    public static final String CREATE_CATEGORY = "createCategory";
    public static final String CATEGORIES = "categories";

    /** Attribute names */
    public static final String FACTORY_ATTRIBUTE = "factory";
    public static final String VERIFIED_USER_NAME_ATTRIBUTE = "verifiedUserName";
    public static final String FORM_ATTRIBUTE = "form";
    public static final String ALL_ARTICLES_ATTRIBUTE = "all_articles";
    public static final String CATEGORIES_FORM_ATTRIBUTE = "categoriesForm";
    public static final String VISIBILITY_ATTRIBUTE = "visibility";

    /** Parameter names */
    public static final String USER_NAME_PARAMETER = "usernameField";
    public static final String PASSWORD_PARAMETER = "passwordField";

    /** Pages URL */
    public static final String LOGIN_PAGE = String.format("%s?%s=", LOGIN, LOGIN);

    /** Controller URL patterns */
    public static final String LOGIN_PATH = "/" + LOGIN;
    public static final String REGISTRATION_PATH = "/" + REGISTRATION;
    public static final String CREATE_CATEGORY_PATH = "/" + CREATE_CATEGORY;
    public static final String CATEGORIES_PATH = "/" + CATEGORIES;

    /** JSP URL */
    public static final String LOGIN_JSP = String.format("/%s/%s.jsp", PAGES_CATALOG, LOGIN);
    public static final String REGISTRATION_JSP = String.format("/%s/%s.jsp", PAGES_CATALOG, REGISTRATION);

    private AppConstants() throws Exception {
        throw new Exception("It's class with constants");
    }
}
