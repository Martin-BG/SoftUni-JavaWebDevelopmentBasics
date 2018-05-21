package javache.http.impl;

import javache.constants.TextConstants;
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

    public HttpRequestImpl(String requestContent) {
        this.method = this.parseMethod(requestContent);
        this.requestUrl = this.parseRequestUrl(requestContent);
        this.headers = this.parseHeaders(requestContent);
        this.bodyParameters = this.parseBodyParameters(requestContent);
        this.isResource = this.parseIsResource();
    }

    private HttpMethod parseMethod(String requestContent) {
        return HttpMethod.valueOf(requestContent.split(TextConstants.SEPARATOR_EMPTY_SPACE)[0].toUpperCase());
    }

    private String parseRequestUrl(String requestContent) {
        return requestContent.split(TextConstants.SEPARATOR_EMPTY_SPACE)[1];
    }

    private Map<String, String> parseHeaders(String requestContent) {
        final Map<String, String> headers = new HashMap<>();

        String[] requestParams = requestContent.split(TextConstants.SEPARATOR_LINE);

        for (final String header : requestParams) {
            if (header.isEmpty()) {
                continue;
            }

            final String[] headerKeyValuePair = header.split(TextConstants.SEPARATOR_HEADER_KVP);

            headers.putIfAbsent(headerKeyValuePair[0], headerKeyValuePair[1]);
        }

        return headers;
    }

    private Map<String, String> parseBodyParameters(String requestContent) {
        final Map<String, String> bodyParameters = new HashMap<>();

        if (this.getMethod() == HttpMethod.POST) {
            final String[] requestParams = requestContent.split(TextConstants.SEPARATOR_LINE);

            if (requestParams.length > this.headers.size() + 2) {
                final String[] bodyParams = requestParams[this.headers.size() + 2].split(TextConstants.SEPARATOR_BODY_PARAMS);

                for (final String bodyParam : bodyParams) {
                    final String[] bodyKeyValuePair = bodyParam.split(TextConstants.SEPARATOR_BODY_KVP);
                    bodyParameters.putIfAbsent(bodyKeyValuePair[0], bodyKeyValuePair[1]);
                }
            }
        }

        return bodyParameters;
    }

    private boolean parseIsResource() {
        return this.requestUrl.contains(TextConstants.SEPARATOR_DOT);
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
