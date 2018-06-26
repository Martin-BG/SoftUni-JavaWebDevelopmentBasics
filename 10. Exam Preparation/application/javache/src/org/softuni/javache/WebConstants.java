package org.softuni.javache;

public final class WebConstants {
    public static final Integer DEFAULT_SERVER_PORT = 8000;
    public static final String SERVER_HTTP_VERSION = "HTTP/1.1";
    private static final String WEB_SERVER_PACKAGE_PATH = "/org/softuni/javache";
    public static final String SERVER_ROOT_FOLDER_PATH =
            Server
                    .class
                    .getResource("")
                    .toString()
                    .replace("file:/", "")
                    .replace(WEB_SERVER_PACKAGE_PATH, "")
                    .replace("%20", " "); // NOTE - replace %20 with " " to allow paths with spaces

    private WebConstants() {
    }
}