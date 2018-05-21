package javache.http.api;

import javache.http.enums.HttpMethod;

import java.util.Map;

public interface HttpRequest {

    Map<String, String> getHeaders();

    Map<String, String> getBodyParameters();

    HttpMethod getMethod();

    String getRequestUrl();

    boolean isResource();
}
