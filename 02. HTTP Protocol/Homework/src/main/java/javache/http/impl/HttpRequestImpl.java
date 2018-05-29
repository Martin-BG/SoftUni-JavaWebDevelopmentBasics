package javache.http.impl;

import javache.constants.HttpConstants;
import javache.http.api.HttpRequest;
import javache.http.enums.HttpMethod;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class HttpRequestImpl implements HttpRequest {
    private final HttpMethod method;
    private final String requestUrl;
    private final Map<String, String> headers;
    private final Map<String, String> bodyParameters;
    private final boolean isResource;

    public HttpRequestImpl(final String requestContent) {
        this.method = this.parseMethod(requestContent);
        this.requestUrl = this.parseRequestUrl(requestContent);
        this.headers = this.parseHeaders(requestContent);
        this.bodyParameters = this.parseBodyParameters(requestContent);
        this.isResource = this.parseIsResource();
    }

    private HttpMethod parseMethod(final String requestContent) {
        return HttpMethod.valueOf(requestContent.split(HttpConstants.SEPARATOR_EMPTY_SPACE)[0].toUpperCase());
    }

    private String parseRequestUrl(final String requestContent) {
        return requestContent.split(HttpConstants.SEPARATOR_EMPTY_SPACE)[1];
    }

    private Map<String, String> parseHeaders(final String requestContent) {
        final Map<String, String> headers = new HashMap<>();

        final String[] requestParams = requestContent.split(HttpConstants.SEPARATOR_LINE);

        for (int i = 1; i < requestParams.length; i++) {
            if (requestParams[i].isEmpty()) {
                break;
            }

            final String[] headerKeyValuePair = requestParams[i].split(HttpConstants.SEPARATOR_HEADER_KVP);

            headers.putIfAbsent(headerKeyValuePair[0], headerKeyValuePair[1]);
        }

        return headers;
    }

    private Map<String, String> parseBodyParameters(final String requestContent) {
        final Map<String, String> bodyParameters = new HashMap<>();

        if (this.getMethod() == HttpMethod.POST) {
            final String[] requestParams = requestContent.split(HttpConstants.SEPARATOR_LINE);

            if (requestParams.length > this.headers.size() + 2) {
                final String[] bodyParams = requestParams[this.headers.size() + 2].split(HttpConstants.SEPARATOR_BODY_PARAMS);

                for (final String bodyParam : bodyParams) {
                    final String[] bodyKeyValuePair = bodyParam.split(HttpConstants.SEPARATOR_BODY_KVP);
                    bodyParameters.putIfAbsent(bodyKeyValuePair[0], bodyKeyValuePair[1]);
                }
            }
        }

        return bodyParameters;
    }

    private boolean parseIsResource() {
        return this.requestUrl.contains(HttpConstants.SEPARATOR_DOT);
    }

    @Override
    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(this.headers);
    }

    @Override
    public Map<String, String> getBodyParameters() {
        return Collections.unmodifiableMap(this.bodyParameters);
    }

    @Override
    public HttpMethod getMethod() {
        return this.method;
    }

    @Override
    public String getRequestUrl() {
        return this.requestUrl;
    }

    @Override
    public boolean isResource() {
        return this.isResource;
    }
}
