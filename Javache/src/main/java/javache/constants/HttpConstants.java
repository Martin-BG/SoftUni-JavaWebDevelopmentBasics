package javache.constants;

public final class HttpConstants {

    public static final String SERVER_HTTP_VERSION = "HTTP/1.1";

    public static final String SEPARATOR_LINE = "\\r\\n";
    public static final String SEPARATOR_EMPTY_SPACE = " ";
    public static final String SEPARATOR_HEADER_KVP = ": ";
    public static final String SEPARATOR_BODY_PARAMS = "&";
    public static final String SEPARATOR_BODY_KVP = "=";
    public static final String SEPARATOR_DOT = ".";
    public static final String SEPARATOR_LINE_RESPONSE = System.lineSeparator();

    private HttpConstants() {
    }
}
