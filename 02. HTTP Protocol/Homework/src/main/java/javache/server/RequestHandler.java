package javache.server;

import javache.constants.HttpConstants;
import javache.constants.MessagesConstants;
import javache.constants.ServerConstants;
import javache.http.api.HttpRequest;
import javache.http.api.HttpResponse;
import javache.http.enums.HttpStatus;
import javache.http.impl.HttpRequestImpl;
import javache.http.impl.HttpResponseImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RequestHandler {

    private HttpRequest httpRequest;
    private HttpResponse httpResponse;

    public byte[] handleRequest(final String requestContent) {
        this.httpRequest = new HttpRequestImpl(requestContent);
        this.httpResponse = new HttpResponseImpl();

        byte[] result = null;

        switch (this.httpRequest.getMethod()) {
        case GET:
            result = this.processGetRequest();
            break;
        case POST:
            result = this.processPostRequest();
            break;
        }

        return result;
    }

    private byte[] processPostRequest() {
        if ("/login".equals(this.httpRequest.getRequestUrl())) { // TODO - Just for demo, refactor
            final String sb = "<!DOCTYPE html>" +
                    "<html lang=\"en\">" +
                    "<head>" +
                    "<meta charset=\"UTF-8\">" +
                    "<title>Dodo</title>" +
                    "</head>" +
                    "<body>" +
                    "<h3>Hello, " +
                    this.httpRequest.getBodyParameters().get("username") +
                    "!</h3><hr/>" +
                    "<p><a href=\"/index\">To Home</a></p>" +
                    "</body></html>";

            return this.ok(sb.getBytes(HttpConstants.SERVER_ENCODING));
        }

        return this.processPageRequest(ServerConstants.INDEX_PAGE);
    }

    private String getMimeType(File file) { // TODO - replace with external library
        String fileName = file.getName();

        if (fileName.endsWith("css")) {
            return "text/css; charset=utf-8";
        } else if (fileName.endsWith("html")) {
            return "text/html; charset=utf-8";
        } else if (fileName.endsWith("jpg") || fileName.endsWith("jpeg")) {
            return "image/jpeg";
        } else if (fileName.endsWith("png")) {
            return "image/png";
        } else if (fileName.endsWith("ico")) {
            return "image/x-icon";
        }

        return "text/plain; charset=utf-8";
    }

    private byte[] processResourceRequest() {
        final String assetPath = ServerConstants.ASSETS_FOLDER_PATH + this.httpRequest.getRequestUrl();

        final File file = new File(assetPath);

        if (!file.exists() || file.isDirectory()) {
            return this.notFound(MessagesConstants.ASSET_NOT_FOUND.getBytes(HttpConstants.SERVER_ENCODING));
        }

        byte[] result;

        try {
            result = Files.readAllBytes(Paths.get(assetPath));
        } catch (IOException e) {
            return this.internalServerError(MessagesConstants.SOMETHING_WENT_WRONG.getBytes(HttpConstants.SERVER_ENCODING));
        }

        this.httpResponse.addHeader(HttpConstants.CONTENT_TYPE, this.getMimeType(file));
        this.httpResponse.addHeader(HttpConstants.CONTENT_LENGTH, Integer.toString(result.length));
        this.httpResponse.addHeader(HttpConstants.CONTENT_DISPOSITION, HttpConstants.INLINE_CONTENT_DISPOSITION);

        return this.ok(result);
    }

    private byte[] processPageRequest(String page) {
        final String pagePath = ServerConstants.PAGES_FOLDER_PATH + page + ServerConstants.HTML_EXTENSION_AND_SEPARATOR;

        final File file = new File(pagePath);

        if (!file.exists() || file.isDirectory()) {
            return this.notFound(MessagesConstants.PAGE_NOT_FOUND.getBytes(HttpConstants.SERVER_ENCODING));
        }

        byte[] result;

        try {
            result = Files.readAllBytes(Paths.get(pagePath));
        } catch (IOException e) {
            return this.internalServerError(MessagesConstants.SOMETHING_WENT_WRONG.getBytes(HttpConstants.SERVER_ENCODING));
        }

        this.httpResponse.addHeader(HttpConstants.CONTENT_TYPE, this.getMimeType(file));

        return this.ok(result);
    }

    private byte[] processGetRequest() {
        if (this.httpRequest.isResource()) {
            return this.processResourceRequest();
        }

        if ("/".equals(httpRequest.getRequestUrl())) {
            return this.processPageRequest(ServerConstants.INDEX_PAGE);
        }

        return this.processPageRequest(this.httpRequest.getRequestUrl());
    }

    private byte[] ok(final byte[] content) {
        this.httpResponse.setHttpStatus(HttpStatus.OK);
        this.httpResponse.setContent(content);
        return this.httpResponse.getBytes();
    }

    private byte[] badRequest(final byte[] content) {
        this.httpResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
        this.httpResponse.setContent(content);
        return this.httpResponse.getBytes();
    }

    private byte[] notFound(final byte[] content) {
        this.httpResponse.setHttpStatus(HttpStatus.NOT_FOUND);
        this.httpResponse.setContent(content);
        return this.httpResponse.getBytes();
    }

    private byte[] redirect(final byte[] content, String location) {
        this.httpResponse.setHttpStatus(HttpStatus.SEE_OTHER);
        this.httpResponse.addHeader(HttpConstants.LOCATION, location);
        this.httpResponse.setContent(content);
        return this.httpResponse.getBytes();
    }

    private byte[] internalServerError(final byte[] content) {
        this.httpResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        this.httpResponse.setContent(content);
        return this.httpResponse.getBytes();
    }
}
