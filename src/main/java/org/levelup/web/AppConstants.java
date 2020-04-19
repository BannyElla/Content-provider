package org.levelup.web;

public class AppConstants {
    public static final String DATA_BASE = "ProdDb";
    public static final String TEST_DATA_BASE = "TestDb";

    public static final String ADMIN = "admin";
    public static final String ADMIN_PASSWORD = "123";

    public static final String LOGIN = "login";
    public static final String REDIRECT = "redirect:/";
    public static final String REGISTRATION = "registration";
    private static final String PAGES_CATALOG = "pages";
    public static final String CREATE_CATEGORY = "createCategory";
    public static final String CATEGORIES = "categories";
    public static final String INDEX = "index";
    public static final String ALL_ARTICLES = "all-articles";
    public static final String LOG_OUT = "log-out";

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
    public static final String LOGIN_PAGE_WITH_PARAMETER = String.format("%s?%s=", LOGIN, LOGIN);

    /** Controller URL patterns */
    public static final String LOGIN_PAGE = "/" + LOGIN;
    public static final String REGISTRATION_PAGE = "/" + REGISTRATION;
    public static final String CREATE_CATEGORY_PAGE = "/" + CREATE_CATEGORY;
    public static final String CRUD_CATEGORIES_PAGE = "/" + ADMIN + "/" + CATEGORIES;
    public static final String ALL_ARTICLES_PAGE = "/" + ALL_ARTICLES;
    public static final String LOG_OUT_PAGE = "/" + LOG_OUT;

    /** JSP URL */
    public static final String LOGIN_JSP = String.format("/%s/%s.jsp", PAGES_CATALOG, LOGIN);
    public static final String REGISTRATION_JSP = String.format("/%s/%s.jsp", PAGES_CATALOG, REGISTRATION);

    /** Actions */
    public static final String LOGIN_ACTION = "/login-action";

    private AppConstants() throws Exception {
        throw new Exception("It's class with constants");
    }
}
