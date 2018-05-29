package javache.http.enums;

public enum HttpMethod {
    POST("POST"),
    GET("GET");

    private final String type;

    HttpMethod(final String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
