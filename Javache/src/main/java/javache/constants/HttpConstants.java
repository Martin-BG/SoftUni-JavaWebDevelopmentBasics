package javache.constants;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public final class HttpConstants {

    public static final String SERVER_HTTP_VERSION = "HTTP/1.1";

    public static final Charset SERVER_ENCODING = StandardCharsets.UTF_8;

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

    private HttpConstants() {
    }
}
