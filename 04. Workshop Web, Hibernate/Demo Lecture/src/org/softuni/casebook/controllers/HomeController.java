package org.softuni.casebook.controllers;

import org.softuni.casebook.CasebookWebConstants;
import org.softuni.javache.http.HttpRequest;
import org.softuni.javache.http.HttpResponse;

public class HomeController extends BaseController {
    public byte[] index(HttpRequest httpRequest, HttpResponse httpResponse) {
        if(httpRequest.getCookies().containsKey(CasebookWebConstants.CASEBOOK_SESSION_KEY)) {
            return this.redirect(new byte[0], "/home", httpResponse);
        }

        return this.processPageRequest("/index", httpResponse);
    }
}
