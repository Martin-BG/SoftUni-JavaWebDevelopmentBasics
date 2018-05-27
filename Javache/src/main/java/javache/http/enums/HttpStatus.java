package javache.http.enums;

import javache.constants.ServerConstants;

public enum HttpStatus {
    OK(200, "OK"),
    CREATED(201, "Created"),
    NO_CONTENT(204, "No Content"),
    SEE_OTHER(303, "See Other"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private final String statusPhrase;
    private final String statusResponse;
    private final int statusCode;

    HttpStatus(final int statusCode, final String statusText) {
        this.statusCode = statusCode;
        this.statusPhrase = String.format("%d %s", statusCode, statusText);
        this.statusResponse = String.format("%s %d %s", ServerConstants.SERVER_HTTP_VERSION, statusCode, statusText);
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getStatusPhrase() {
        return this.statusPhrase;
    }

    public String getAsResponse() {
        return this.statusResponse;
    }
}
