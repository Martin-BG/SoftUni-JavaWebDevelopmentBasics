package javache.constants;

public final class ServerConstants { // TODO - export to property file

    public static final int SERVER_PORT = 8000;

    public static final int SOCKET_TIMEOUT_MILLISECONDS = 5000;
    public static final String HTML_EXTENSION_AND_SEPARATOR = ".html";
    public static final String INDEX_PAGE = "/index";
    private static final String RESOURCE_FOLDER_PATH = System.getProperty("user.dir") + "/src/main/resources/";
    public static final String ASSETS_FOLDER_PATH = RESOURCE_FOLDER_PATH + "assets/";
    public static final String PAGES_FOLDER_PATH = RESOURCE_FOLDER_PATH + "pages/";

    private ServerConstants() {
    }
}
