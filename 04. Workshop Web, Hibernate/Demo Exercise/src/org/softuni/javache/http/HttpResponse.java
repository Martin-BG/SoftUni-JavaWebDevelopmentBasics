package org.softuni.javache.http;

import org.softuni.javache.WebConstants;

import java.util.Arrays;
import java.util.HashMap;

public interface HttpResponse {
    HashMap<String, String> getHeaders();

    HttpStatus getStatusCode();

    void setStatusCode(HttpStatus statusCode);

    HttpSession getSession();

    void setSession(HttpSession session);

    byte[] getContent();

    void setContent(byte[] content);

    byte[] getBytes();

    void addHeader(String header, String value);

    void addCookie(String name, String value);

    enum ResponseLines {
        Ok(WebConstants.SERVER_HTTP_VERSION + " " + HttpStatus.Ok.getStatusPhrase()),
        Created(WebConstants.SERVER_HTTP_VERSION + " " + HttpStatus.Created.getStatusPhrase()),
        NoContent(WebConstants.SERVER_HTTP_VERSION + " " + HttpStatus.NoContent.getStatusPhrase()),
        SeeOther(WebConstants.SERVER_HTTP_VERSION + " " + HttpStatus.SeeOther.getStatusPhrase()),
        BadRequest(WebConstants.SERVER_HTTP_VERSION + " " + HttpStatus.BadRequest.getStatusPhrase()),
        Unauthorized(WebConstants.SERVER_HTTP_VERSION + " " + HttpStatus.Unauthorized.getStatusPhrase()),
        Forbidden(WebConstants.SERVER_HTTP_VERSION + " " + HttpStatus.Forbidden.getStatusPhrase()),
        NotFound(WebConstants.SERVER_HTTP_VERSION + " " + HttpStatus.NotFound.getStatusPhrase()),
        InternalServerError(WebConstants.SERVER_HTTP_VERSION + " " + HttpStatus.InternalServerError.getStatusPhrase());

        private String value;

        ResponseLines(String responseLine) {
            this.value = responseLine;
        }

        static String getResponseLine(int statusCode) {
            return ((ResponseLines) Arrays.stream(values()).filter((x) -> x.value.contains("" + statusCode)).toArray()[0]).value;
        }
    }
}
