package javache.constants;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public final class ServerConstants { // TODO - export to property file

    public static final Charset SERVER_ENCODING = StandardCharsets.UTF_8;

    public static final int SERVER_PORT = 8000;

    public static final int SOCKET_TIMEOUT_MILLISECONDS = 5000;

    public static final String SERVER_HTTP_VERSION = "HTTP/1.1";

    public static final String HTML_EXTENSION_AND_SEPARATOR = ".html";

    public static final String SERVER_RESOURCE_PATH = System.getProperty("user.dir") + "/src/main/resources/";
    public static final String ASSETS_FOLDER_PATH = SERVER_RESOURCE_PATH + "assets/";
    public static final String PAGES_FOLDER_PATH = SERVER_RESOURCE_PATH + "pages/";

    private ServerConstants() {
    }
}
