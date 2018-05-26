package javache.http.api;

import javache.http.enums.HttpStatus;

import java.util.Map;

public interface HttpResponse {

    Map<String, String> getHeaders();

    HttpStatus getHttpStatus();

    void setHttpStatus(HttpStatus httpStatus);

    byte[] getContent();

    void setContent(byte[] content);

    byte[] getBytes();

    void addHeader(String header, String value);

    void addCookie(String name, String value);

    void expireCookie(String name);
}
