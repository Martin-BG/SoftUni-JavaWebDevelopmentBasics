package javache.http.impl;

import javache.http.api.HttpCookie;

import static javache.constants.HttpConstants.SEPARATOR_COOKIE_KVP;

public final class HttpCookieImpl implements HttpCookie {

    private final String name;
    private final String value;

    public HttpCookieImpl(final String name, final String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.name + SEPARATOR_COOKIE_KVP + this.getValue();
    }
}
