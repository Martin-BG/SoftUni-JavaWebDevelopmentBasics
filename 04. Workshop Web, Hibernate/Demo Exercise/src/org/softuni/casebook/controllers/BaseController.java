package org.softuni.casebook.controllers;

import org.softuni.casebook.CasebookWebConstants;
import org.softuni.javache.WebConstants;
import org.softuni.javache.http.HttpResponse;
import org.softuni.javache.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseController {
    private static final String PAGE_MIME_TYPE = "text/html";

    protected BaseController() {
    }

    protected byte[] ok(byte[] content, HttpResponse httpResponse) {
        httpResponse.setStatusCode(HttpStatus.Ok);
        httpResponse.setContent(content);
        return httpResponse.getBytes();
    }

    protected byte[] created(byte[] content, HttpResponse httpResponse) {
        httpResponse.setStatusCode(HttpStatus.Created);
        httpResponse.setContent(content);
        return httpResponse.getBytes();
    }

    protected byte[] badRequest(byte[] content, HttpResponse httpResponse) {
        httpResponse.setStatusCode(HttpStatus.BadRequest);
        httpResponse.setContent(content);
        return httpResponse.getBytes();
    }

    protected byte[] notFound(byte[] content, HttpResponse httpResponse) {
        httpResponse.setStatusCode(HttpStatus.NotFound);
        httpResponse.setContent(content);
        return httpResponse.getBytes();
    }

    protected byte[] redirect(byte[] content, String location, HttpResponse httpResponse) {
        httpResponse.setStatusCode(HttpStatus.SeeOther);
        httpResponse.addHeader("Location", location);
        httpResponse.setContent(content);
        return httpResponse.getBytes();
    }

    protected byte[] internalServerError(byte[] content, HttpResponse httpResponse) {
        httpResponse.setStatusCode(HttpStatus.InternalServerError);
        httpResponse.setContent(content);
        return httpResponse.getBytes();
    }

    private byte[] loadAndRenderPage(String pagePath, HashMap<String, String> viewData) throws IOException {
        String textContent = String.join("", Files.readAllLines(Paths.get(pagePath)));

        for (Map.Entry<String, String> parameterEntry : viewData.entrySet()) {
            textContent = textContent
                    .replace(
                            "${" + parameterEntry.getKey() + "}"
                            , parameterEntry.getValue());
        }

        return textContent.getBytes();
    }

    protected byte[] processPageRequest(String page, HttpResponse httpResponse) {
        return this.processPageRequest(page, null, httpResponse);
    }

    protected byte[] processPageRequest(String page, HashMap<String, String> viewData, HttpResponse httpResponse) {
        String pagePath = WebConstants.PAGES_FOLDER_PATH +
                page
                + CasebookWebConstants.HTML_EXTENSION_AND_SEPARATOR;

        File file = new File(pagePath);

        if (!file.exists() || file.isDirectory()) {
            return this.notFound(("Page not found!").getBytes(), httpResponse);
        }

        byte[] result = null;

        try {
            if (viewData != null) {
                result = this.loadAndRenderPage(pagePath, viewData);
            } else {
                result = Files.readAllBytes(Paths.get(pagePath));
            }
        } catch (IOException e) {
            return this.internalServerError(("Something went wrong!").getBytes(), httpResponse);
        }

        httpResponse.addHeader("Content-Type", PAGE_MIME_TYPE);

        return this.ok(result, httpResponse);
    }
}
