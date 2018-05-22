package javache.http.impl;

import javache.constants.HttpConstants;
import javache.http.api.HttpResponse;
import javache.http.enums.HttpStatus;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class HttpResponseImpl implements HttpResponse {

    private HttpStatus httpStatus;
    private final Map<String, String> headers;
    private byte[] content;

    public HttpResponseImpl() {
        this.content = new byte[0];
        this.headers = new HashMap<>();
    }

    private byte[] getHeadersBytes() {
        final StringBuilder result = new StringBuilder()
                .append(this.getHttpStatus().getAsResponse())
                .append(HttpConstants.SEPARATOR_LINE_RESPONSE);

        for (final Map.Entry<String, String> header : this.getHeaders().entrySet()) {
            result.append(header.getKey())
                    .append(HttpConstants.SEPARATOR_HEADER_KVP)
                    .append(header.getValue())
                    .append(HttpConstants.SEPARATOR_LINE_RESPONSE);
        }

        result.append(HttpConstants.SEPARATOR_LINE_RESPONSE);

        return result.toString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(this.headers);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public void setHttpStatus(final HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public byte[] getContent() {
        return this.content;
    }

    @Override
    public void setContent(final byte[] content) {
        this.content = content;
    }

    @Override
    public void addHeader(final String header, final String value) {
        this.headers.putIfAbsent(header, value);
    }

    @Override
    public byte[] getBytes() {
        final byte[] headersBytes = this.getHeadersBytes();
        final byte[] bodyBytes = this.getContent();

        final byte[] fullResponse = new byte[headersBytes.length + bodyBytes.length];

        System.arraycopy(headersBytes, 0, fullResponse, 0, headersBytes.length);
        System.arraycopy(bodyBytes, 0, fullResponse, headersBytes.length, bodyBytes.length);

        return fullResponse;
    }
}
