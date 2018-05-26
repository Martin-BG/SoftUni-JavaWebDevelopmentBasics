package javache.constants;

public final class HttpConstants {

    public static final String SEPARATOR_LINE = "\\r\\n";
    public static final String SEPARATOR_EMPTY_SPACE = " ";
    public static final String SEPARATOR_HEADER_KVP = ": ";
    public static final String SEPARATOR_BODY_PARAMS = "&";
    public static final String SEPARATOR_BODY_KVP = "=";
    public static final String SEPARATOR_DOT = ".";
    public static final String SEPARATOR_LINE_RESPONSE = System.lineSeparator();

    public static final String LOCATION = "Location";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String CONTENT_DISPOSITION = "Content-Disposition";
    public static final String INLINE_CONTENT_DISPOSITION = "inline";

    public static final String COOKIE = "Cookie";
    public static final String SEPARATOR_COOKIES = "; ";
    public static final String SEPARATOR_COOKIE_KVP = "=";
    public static final String COOKIE_RESPONSE_NAME = "Set-Cookie: ";
    public static final String COOKIE_DELETE = "deleted; expires=Thu, 01 Jan 1970 00:00:00 GMT;";

    public static final String JAVACHE_SESSION_KEY = "Javache";

    private HttpConstants() {
    }
}
