package javache.server;

import javache.constants.CasebookConstants;
import javache.constants.HttpConstants;
import javache.constants.ResponsesConstants;
import javache.constants.ServerConstants;
import javache.database.entities.User;
import javache.database.services.UserService;
import javache.http.api.*;
import javache.http.enums.HttpStatus;
import javache.http.impl.HttpRequestImpl;
import javache.http.impl.HttpResponseImpl;
import javache.http.impl.HttpSessionImpl;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Collectors;

public class RequestHandler {

    private final HttpSessionStorage sessionStorage;
    private final UserService userService;
    private HttpRequest httpRequest;
    private HttpResponse httpResponse;

    public RequestHandler(final HttpSessionStorage sessionStorage,
                          final UserService userService) {
        this.sessionStorage = sessionStorage;
        this.userService = userService;
    }

    private static String getMimeType(String fileName) { // TODO - replace with external library
        switch (getFileExtension(fileName)) {
        case "css":
            return "text/css; charset=utf-8";
        case "html":
            return "text/html; charset=utf-8";
        case "jpg":
        case "jpeg":
            return "image/jpeg";
        case "png":
            return "image/png";
        case "ico":
            return "image/x-icon";
        default:
            return "text/plain; charset=utf-8";
        }
    }

    private static String getFileExtension(String fileName) {
        final int index = fileName.lastIndexOf(HttpConstants.SEPARATOR_DOT);

        if (index != -1 && index != 0) {
            return fileName.substring(index + 1);
        } else {
            return CasebookConstants.EMPTY_STRING;
        }
    }

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

        this.sessionStorage.refreshSessions();

        return result;
    }

    private byte[] processResourceRequest(final String resourceUrl) {

        final String extension = getFileExtension(resourceUrl);

        if ("html".equalsIgnoreCase(extension)) {   // TODO - refactor
            final HttpSession session = getCurrentSession();
            final boolean isSessionValid = session != null && session.isValid();

            if (!isSessionValid) {
                if (resourceUrl.toLowerCase().endsWith(CasebookConstants.CASEBOOK_LOGOUT_PAGE) ||
                        resourceUrl.toLowerCase().endsWith(CasebookConstants.CASEBOOK_USER_HOME_PAGE) ||
                        resourceUrl.toLowerCase().endsWith(CasebookConstants.CASEBOOK_USER_PROFILE_PAGE) ||
                        resourceUrl.toLowerCase().endsWith(CasebookConstants.CASEBOOK_LOGOUT_PAGE + ServerConstants.HTML_EXTENSION_AND_SEPARATOR) ||
                        resourceUrl.toLowerCase().endsWith(CasebookConstants.CASEBOOK_USER_HOME_PAGE + ServerConstants.HTML_EXTENSION_AND_SEPARATOR) ||
                        resourceUrl.toLowerCase().endsWith(CasebookConstants.CASEBOOK_USER_PROFILE_PAGE + ServerConstants.HTML_EXTENSION_AND_SEPARATOR)) {
                    return this.redirect(new byte[0], CasebookConstants.CASEBOOK_LOGIN_PAGE_STATIC);
                }
            } else if (resourceUrl.toLowerCase().endsWith(CasebookConstants.CASEBOOK_INDEX_PAGE_STATIC) ||
                    resourceUrl.toLowerCase().endsWith(CasebookConstants.CASEBOOK_REGISTER_PAGE_STATIC) ||
                    resourceUrl.toLowerCase().endsWith(CasebookConstants.CASEBOOK_LOGIN_PAGE) ||
                    resourceUrl.toLowerCase().endsWith(CasebookConstants.CASEBOOK_LOGIN_PAGE + ServerConstants.HTML_EXTENSION_AND_SEPARATOR)) {
                return this.redirect(new byte[0], CasebookConstants.CASEBOOK_USER_PROFILE_PAGE);
            }

        }

        final String assetPath = extension.isEmpty() ?
                CasebookConstants.CASEBOOK_ASSETS_PATH + resourceUrl :
                CasebookConstants.CASEBOOK_ASSETS_PATH + extension + HttpConstants.SEPARATOR_FOLDER + resourceUrl;

        final File file = new File(assetPath);

        if (!file.exists() || file.isDirectory()) {
            return this.notFound(ResponsesConstants.PAGE_OR_RESOURCE_NOT_FOUND);
        }

        byte[] result;

        try {
            result = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            return this.internalServerError(ResponsesConstants.SOMETHING_WENT_WRONG);
        }

        this.httpResponse.addHeader(HttpConstants.CONTENT_TYPE, getMimeType(assetPath));
        this.httpResponse.addHeader(HttpConstants.CONTENT_LENGTH, Integer.toString(result.length));
        this.httpResponse.addHeader(HttpConstants.CONTENT_DISPOSITION, HttpConstants.INLINE_CONTENT_DISPOSITION);

        return this.ok(result);
    }

    private byte[] processPageRequest(final String page) {
        if (page.contains(HttpConstants.SEPARATOR_DOT)) {
            return this.processResourceRequest(page);
        }

        final String pagePath = CasebookConstants.CASEBOOK_PAGES_PATH + page + ServerConstants.HTML_EXTENSION_AND_SEPARATOR;

        final File file = new File(pagePath);

        if (!file.exists() || file.isDirectory()) {
            return processResourceRequest(page + ServerConstants.HTML_EXTENSION_AND_SEPARATOR); // NOTE - Search in assets too
        }

        byte[] result;

        try {
            final HttpSession session = this.getCurrentSession();
            if (session != null) {
                if (page.toLowerCase().endsWith(CasebookConstants.CASEBOOK_USER_PROFILE_PAGE)) {
                    final String password = this.getParameterFromSession(session, CasebookConstants.PARAMETER_PASSWORD);
                    final String email = this.getParameterFromSession(session, CasebookConstants.PARAMETER_EMAIL);
                    final StringBuilder sb = new StringBuilder();
                    Files.readAllLines(Paths.get(pagePath), ServerConstants.SERVER_ENCODING).forEach(sb::append);
                    String res = sb.toString();
                    result = res.replace(CasebookConstants.PLACEHOLDER_PASSWORD, password != null ? password : CasebookConstants.EMPTY_STRING)
                            .replace(CasebookConstants.PLACEHOLDER_EMAIL, email != null ? email : CasebookConstants.EMPTY_STRING)
                            .getBytes(ServerConstants.SERVER_ENCODING);
                } else if (page.toLowerCase().endsWith(CasebookConstants.CASEBOOK_USER_HOME_PAGE)) {
                    final String email = this.getParameterFromSession(session, CasebookConstants.PARAMETER_EMAIL);
                    final Collection<User> allUsers = this.userService.getAll();
                    final String users = allUsers.stream()
                            .filter(user -> !user.getName().equals(email))
                            .map(user -> String.format(CasebookConstants.FORMAT_USERS, user.getName()))
                            .collect(Collectors.joining(CasebookConstants.EMPTY_STRING));

                    final StringBuilder sb = new StringBuilder();
                    Files.readAllLines(Paths.get(pagePath), ServerConstants.SERVER_ENCODING)
                            .forEach(sb::append);
                    String res = sb.toString();
                    result = res.replace(CasebookConstants.PLACEHOLDER_USERS, users)
                            .getBytes(ServerConstants.SERVER_ENCODING);
                } else {
                    result = Files.readAllBytes(Paths.get(pagePath));
                }
            } else {
                result = Files.readAllBytes(Paths.get(pagePath));
            }
        } catch (IOException e) {
            return this.internalServerError(ResponsesConstants.SOMETHING_WENT_WRONG);
        }

        this.httpResponse.addHeader(HttpConstants.CONTENT_TYPE, getMimeType(pagePath));

        return this.ok(result);
    }

    private String getParameterFromBody(final String parameter) {
        String param = this.httpRequest.getBodyParameters().get(parameter);

        if (param != null) {
            try {
                param = URLDecoder.decode(param, ServerConstants.SERVER_ENCODING.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return param;
    }

    private String getParameterFromSession(final HttpSession session, final String parameter) {
        String param = (String) session.getAttributes().get(parameter);

        if (param != null) {
            try {
                param = URLDecoder.decode(param, ServerConstants.SERVER_ENCODING.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return param;
    }

    private byte[] processPostRequest() {
        String requestUrl = this.httpRequest.getRequestUrl();
        final HttpSession session = getCurrentSession();
        final boolean isSessionValid = session != null && session.isValid();

        switch (requestUrl) {
        case CasebookConstants.CASEBOOK_LOGIN_PAGE:
        case CasebookConstants.CASEBOOK_LOGIN_PAGE_STATIC: {
            if (isSessionValid) {
                return this.redirect(new byte[0], CasebookConstants.CASEBOOK_USER_PROFILE_PAGE);
            }

            final String password = this.getParameterFromBody(CasebookConstants.PARAMETER_PASSWORD);
            final String email = this.getParameterFromBody(CasebookConstants.PARAMETER_EMAIL);

            if (email != null && password != null) {

                final User user = this.userService.validate(email, password);
                if (user == null) { // Invalid credentials
                    return this.badRequest(ResponsesConstants.BAD_REQUEST_FOUND);
                }

                HttpSession newSession = new HttpSessionImpl();
                newSession.addAttribute(CasebookConstants.PARAMETER_EMAIL, email);
                newSession.addAttribute(CasebookConstants.PARAMETER_PASSWORD, password);
                this.sessionStorage.addSession(newSession);

                this.httpResponse.addCookie(CasebookConstants.CASEBOOK_SESSION_KEY, newSession.getId());

                return this.redirect(new byte[0], CasebookConstants.CASEBOOK_USER_PROFILE_PAGE);
            } else {
                return this.badRequest(ResponsesConstants.BAD_REQUEST_FOUND);
            }
        }
        case CasebookConstants.CASEBOOK_REGISTER_PAGE:
        case CasebookConstants.CASEBOOK_REGISTER_PAGE_STATIC: {
            if (isSessionValid) {
                return this.redirect(new byte[0], CasebookConstants.CASEBOOK_USER_PROFILE_PAGE);
            }

            final String password = this.getParameterFromBody(CasebookConstants.PARAMETER_PASSWORD);
            final String passwordConfirm = this.getParameterFromBody(CasebookConstants.PARAMETER_PASSWORD_CONFIRM);
            final String email = this.getParameterFromBody(CasebookConstants.PARAMETER_EMAIL);

            if (email != null && password != null &&
                    password.equals(passwordConfirm) &&
                    this.userService.registerUser(email, password)) {
                return this.redirect(new byte[0], CasebookConstants.CASEBOOK_LOGIN_PAGE_STATIC);
            }

            return this.badRequest(ResponsesConstants.BAD_REQUEST_FOUND);
        }
        default: {
            return this.notFound(ResponsesConstants.PAGE_OR_RESOURCE_NOT_FOUND);
        }
        }
    }

    private byte[] processGetRequest() {
        if (this.httpRequest.isResource()) {
            return this.processResourceRequest(this.httpRequest.getRequestUrl());
        }

        final HttpSession session = getCurrentSession();
        final boolean isSessionValid = session != null && session.isValid();

        switch (this.httpRequest.getRequestUrl()) {
        case CasebookConstants.CASEBOOK_INDEX_PAGE:
        case CasebookConstants.CASEBOOK_INDEX_PAGE_STATIC:
        case CasebookConstants.CASEBOOK_ROOT_PAGE: {
            if (isSessionValid) {
                return this.redirect(new byte[0], CasebookConstants.CASEBOOK_USER_PROFILE_PAGE);
            }

            if (!this.httpRequest.getRequestUrl().equals(CasebookConstants.CASEBOOK_INDEX_PAGE)) {
                return this.redirect(new byte[0], CasebookConstants.CASEBOOK_INDEX_PAGE_STATIC);
            }

            return this.processPageRequest(CasebookConstants.CASEBOOK_INDEX_PAGE_STATIC);
        }
        case CasebookConstants.CASEBOOK_REGISTER_PAGE:
        case CasebookConstants.CASEBOOK_REGISTER_PAGE_STATIC:
        case CasebookConstants.CASEBOOK_LOGIN_PAGE:
        case CasebookConstants.CASEBOOK_LOGIN_PAGE_STATIC: {
            System.out.println();
            return isSessionValid ?
                    this.redirect(new byte[0], CasebookConstants.CASEBOOK_USER_PROFILE_PAGE) :
                    this.badRequest(ResponsesConstants.BAD_REQUEST_FOUND);
        }
        case CasebookConstants.CASEBOOK_LOGOUT_PAGE: {
            if (!isSessionValid) {
                return this.redirect(new byte[0], CasebookConstants.CASEBOOK_USER_PROFILE_PAGE);
            }

            this.httpResponse.expireCookie(CasebookConstants.CASEBOOK_SESSION_KEY);
            session.invalidate();

            return this.redirect(new byte[0], CasebookConstants.CASEBOOK_INDEX_PAGE_STATIC);
        }
        case CasebookConstants.CASEBOOK_USER_HOME_PAGE: {
            if (!isSessionValid) {
                return this.redirect(new byte[0], CasebookConstants.CASEBOOK_LOGIN_PAGE_STATIC);
            }

            return this.processPageRequest(CasebookConstants.CASEBOOK_USER_HOME_PAGE);
        }
        case CasebookConstants.CASEBOOK_USER_PROFILE_PAGE: {
            if (!isSessionValid) {
                return this.redirect(new byte[0], CasebookConstants.CASEBOOK_LOGIN_PAGE_STATIC);
            }

            return this.processPageRequest(CasebookConstants.CASEBOOK_USER_PROFILE_PAGE);
        }
        default:
            return this.notFound(ResponsesConstants.PAGE_OR_RESOURCE_NOT_FOUND);
        }
    }

    private HttpSession getCurrentSession() {
        final HttpCookie cookie = this.httpRequest.getCookies().get(CasebookConstants.CASEBOOK_SESSION_KEY);
        return cookie != null ?
                this.sessionStorage.getById(cookie.getValue()) :
                null;
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
