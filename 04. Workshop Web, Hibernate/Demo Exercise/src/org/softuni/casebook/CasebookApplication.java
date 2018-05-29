package org.softuni.casebook;

import org.softuni.casebook.controllers.BaseController;
import org.softuni.casebook.controllers.HomeController;
import org.softuni.casebook.controllers.ResourceController;
import org.softuni.casebook.controllers.UserController;
import org.softuni.casebook.util.GetMapping;
import org.softuni.casebook.util.PostMapping;
import org.softuni.javache.api.RequestHandler;
import org.softuni.javache.http.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class CasebookApplication implements RequestHandler {
    private boolean intercepted;

    private HttpRequest httpRequest;

    private HttpResponse httpResponse;

    private HttpSessionStorage sessionStorage;

    private Set<BaseController> controllers;

    public CasebookApplication(HttpSessionStorage sessionStorage) {
        this.intercepted = false;
        this.sessionStorage = sessionStorage;
        this.initializeControllers();
    }

    private void initializeControllers() {
        this.controllers = new HashSet<>() {{
            add(new UserController());
            add(new HomeController());
        }};
    }

    @Override
    public byte[] handleRequest(String requestContent) {
        try {
            this.httpRequest = new HttpRequestImpl(requestContent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.httpResponse = new HttpResponseImpl();

        if (this.httpRequest
                .getCookies()
                .containsKey(CasebookWebConstants.CASEBOOK_SESSION_KEY)) {
            HttpSession clientSession = this.sessionStorage
                    .getById(
                            this.httpRequest
                                    .getCookies()
                                    .get(CasebookWebConstants.CASEBOOK_SESSION_KEY)
                                    .getValue()
                    );

            if (clientSession == null) {
                this.httpRequest.getCookies().remove(CasebookWebConstants.CASEBOOK_SESSION_KEY);
            }

            this.httpResponse.setSession(clientSession);
        }

        byte[] result = null;

        if (this.httpRequest.getMethod().equals("GET")) {
            result = this.processGetRequest();
        } else if (this.httpRequest.getMethod().equals("POST")) {
            result = this.processPostRequest();
        }

        if (this.httpResponse.getSession() != null
                && this.sessionStorage.getById(
                this.httpResponse.getSession().getId()) == null) {
            this.sessionStorage.addSession(this.httpResponse.getSession());
        }

        this.sessionStorage.refreshSessions();

        this.intercepted = true;
        return result;
    }

    @Override
    public boolean hasIntercepted() {
        return this.intercepted;
    }

    private byte[] processGetRequest() {
        String requestUrl = this.httpRequest.getRequestUrl();

        for (BaseController controller : this.controllers) {
            Method[] controllerActions = controller
                    .getClass()
                    .getDeclaredMethods();

            for (Method controllerAction : controllerActions) {
                if (controllerAction.isAnnotationPresent(GetMapping.class)) {
                    String actionRoute = controllerAction.getAnnotation(GetMapping.class).route();

                    if (actionRoute.equals(requestUrl)) {
                        try {
                            return (byte[]) controllerAction.invoke(controller, httpRequest, httpResponse);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        return new ResourceController().processResourceRequest(this.httpRequest, this.httpResponse);
    }

    private byte[] processPostRequest() {
        String requestUrl = this.httpRequest.getRequestUrl();

        for (BaseController controller : this.controllers) {
            Method[] controllerActions = controller
                    .getClass()
                    .getDeclaredMethods();

            for (Method controllerAction : controllerActions) {
                if (controllerAction.isAnnotationPresent(PostMapping.class)) {
                    String actionRoute = controllerAction.getAnnotation(PostMapping.class).route();

                    if (actionRoute.equals(requestUrl)) {
                        try {
                            return (byte[]) controllerAction.invoke(controller, httpRequest, httpResponse);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        throw new IllegalArgumentException("You shouldn't reach here!");
    }
}
