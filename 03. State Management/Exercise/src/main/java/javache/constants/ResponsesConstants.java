package javache.constants;

public final class ResponsesConstants {

    // NOTE - Mutable arrays!
    public static final byte[] BAD_REQUEST_FOUND =
            "<h1>Error 400: Malformed Request...<h1/>".getBytes(ServerConstants.SERVER_ENCODING);
    public static final byte[] PAGE_OR_RESOURCE_NOT_FOUND =
            "<h1>Error 404: Page or Resource not found...<h1/>".getBytes(ServerConstants.SERVER_ENCODING);
    public static final byte[] SOMETHING_WENT_WRONG =
            "<h1>Error 500:Something went wrong!<h1/>".getBytes(ServerConstants.SERVER_ENCODING);

    private ResponsesConstants() {
    }
}
