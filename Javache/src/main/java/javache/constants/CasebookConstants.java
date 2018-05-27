package javache.constants;

import static javache.constants.ServerConstants.SERVER_RESOURCE_PATH;

public final class CasebookConstants {

    public static final String CASEBOOK_SESSION_KEY = "CasebookSK";
    public static final String CASEBOOK_ROOT_PAGE = "/";
    public static final String CASEBOOK_INDEX_PAGE_STATIC = "/index.html";
    public static final String CASEBOOK_INDEX_PAGE = "/index";
    public static final String CASEBOOK_LOGIN_PAGE_STATIC = "/login.html";
    public static final String CASEBOOK_LOGIN_PAGE = "/login";
    public static final String CASEBOOK_LOGOUT_PAGE = "/logout";
    public static final String CASEBOOK_REGISTER_PAGE_STATIC = "/register.html";
    public static final String CASEBOOK_REGISTER_PAGE = "/register";
    public static final String CASEBOOK_USER_PROFILE_PAGE = "/users/profile";
    public static final String CASEBOOK_USER_HOME_PAGE = "/home";
    public static final String CASEBOOK_RESOURCE_PATH = SERVER_RESOURCE_PATH + "casebook/";
    public static final String CASEBOOK_ASSETS_PATH = CASEBOOK_RESOURCE_PATH + "assets/";
    public static final String CASEBOOK_PAGES_PATH = CASEBOOK_RESOURCE_PATH + "pages/";

    private CasebookConstants() {
    }
}
