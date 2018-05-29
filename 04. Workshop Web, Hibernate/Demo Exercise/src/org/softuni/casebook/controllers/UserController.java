package org.softuni.casebook.controllers;

import org.softuni.casebook.CasebookWebConstants;
import org.softuni.casebook.util.GetMapping;
import org.softuni.casebook.util.PostMapping;
import org.softuni.database.entities.User;
import org.softuni.database.repositories.UserRepository;
import org.softuni.javache.http.HttpRequest;
import org.softuni.javache.http.HttpResponse;
import org.softuni.javache.http.HttpSession;
import org.softuni.javache.http.HttpSessionImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class UserController extends BaseController {
    private UserRepository userRepository;

    public UserController() {
        this.userRepository = new UserRepository();
    }

    @GetMapping(route = "/login")
    public byte[] login(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.getCookies().containsKey(CasebookWebConstants.CASEBOOK_SESSION_KEY)) {
            return this.redirect(new byte[0], "/home", httpResponse);
        }

        return this.processPageRequest("/login", httpResponse);
    }

    @PostMapping(route = "/login")
    public byte[] loginPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.getCookies().containsKey(CasebookWebConstants.CASEBOOK_SESSION_KEY)) {
            return this.redirect(new byte[0], "/home", httpResponse);
        }

        String username = httpRequest.getBodyParameters().get("username");
        String password = httpRequest.getBodyParameters().get("password");

        if (this.userRepository.findByUsername(username) == null) {
            return this.badRequest(("Username does not exist!").getBytes(), httpResponse);
        }

        HttpSession session = new HttpSessionImpl();
        session.addAttribute("username", username);

        httpResponse.addCookie(CasebookWebConstants.CASEBOOK_SESSION_KEY, session.getId());
        httpResponse.setSession(session);

        return this.redirect(("Successfully logged in!").getBytes(), "/home", httpResponse);
    }

    @GetMapping(route = "/register")
    public byte[] register(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.getCookies().containsKey(CasebookWebConstants.CASEBOOK_SESSION_KEY)) {
            return this.redirect(new byte[0], "/home", httpResponse);
        }

        return this.processPageRequest("/register", httpResponse);
    }

    @PostMapping(route = "/register")
    public byte[] registerPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.getCookies().containsKey(CasebookWebConstants.CASEBOOK_SESSION_KEY)) {
            return this.redirect(new byte[0], "/home", httpResponse);
        }

        if (!httpRequest.getBodyParameters().get("password").equals(httpRequest.getBodyParameters().get("confirmPassword"))) {
            return this.badRequest(("Passwords do not match!").getBytes(), httpResponse);
        }

        User user = new User();

        user.setUsername(httpRequest.getBodyParameters().get("username"));
        user.setPassword(httpRequest.getBodyParameters().get("password"));

        this.userRepository.saveUser(user);

        return this.redirect(String.format("Successfully registered User - %s", user.getUsername()).getBytes(), "/login", httpResponse);
    }

    @GetMapping(route = "/logout")
    public byte[] logout(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (!httpRequest.getCookies().containsKey(CasebookWebConstants.CASEBOOK_SESSION_KEY)) {
            return this.redirect(new byte[0], "/login", httpResponse);
        }

        httpResponse.addCookie(CasebookWebConstants.CASEBOOK_SESSION_KEY, "removed; expires=" + new Date(0).toString());

        httpResponse.getSession().invalidate();

        return this.redirect(("Successfully logged out!").getBytes(), "/", httpResponse);
    }

    @GetMapping(route = "/users")
    public byte[] allUsers(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (!httpRequest.getCookies().containsKey(CasebookWebConstants.CASEBOOK_SESSION_KEY)) {
            return this.redirect(new byte[0], "/login", httpResponse);
        }

        String currentUserUsername = httpResponse.getSession().getAttributes().get("username").toString();
        User currentUser = this.userRepository.findByUsername(currentUserUsername);

        HashMap<String, String> viewData = new HashMap<>();

        List<User> allUsers = this.userRepository.findAll();
        StringBuilder otherUsersUsernames = new StringBuilder();

        for (User user : allUsers) {
            if (!user.getUsername().equals(currentUser.getUsername())
                    &&
                    currentUser
                            .getFriends()
                            .stream()
                            .noneMatch(x -> x.getUsername().equals(user.getUsername()))) {
                otherUsersUsernames
                        .append("<div class=\"row d-flex justify-content-around\">")
                        .append("<h3>" + user.getUsername() + "</h3>")
                        .append("<a class=\"btn btn-primary\" href=\"/add?friend="
                                + user.getUsername()
                                + "\">Add Friend</a>")
                        .append("</div>");
            }
        }

        viewData.put("otherUsers", otherUsersUsernames.toString());

        return this.processPageRequest("/users", viewData, httpResponse);
    }

    @GetMapping(route = "/friends")
    public byte[] allFriends(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (!httpRequest.getCookies().containsKey(CasebookWebConstants.CASEBOOK_SESSION_KEY)) {
            return this.redirect(new byte[0], "/login", httpResponse);
        }

        String currentUserUsername = httpResponse.getSession().getAttributes().get("username").toString();

        User currentUser = this.userRepository.findByUsername(currentUserUsername);
        StringBuilder friendsUsernames = new StringBuilder();

        if (currentUser.getFriends().isEmpty()) {
            friendsUsernames
                    .append("<h2>You have no friends!</h2>")
                    .append("<h3>Get a life!</h3>");
        } else {
            for (User friend : currentUser.getFriends()) {
                friendsUsernames
                        .append("<div class=\"row d-flex justify-content-around\">")
                        .append("<h3>" + friend.getUsername() + "</h3>")
                        .append("<a class=\"btn btn-danger\" href=\"/remove?friend="
                                + friend.getUsername()
                                + "\">Remove Friend</a>")
                        .append("</div>");
            }
        }

        HashMap<String, String> viewData = new HashMap<>();

        viewData.put("friends", friendsUsernames.toString());

        return this.processPageRequest("/friends", viewData, httpResponse);
    }

    @GetMapping(route = "/add")
    public byte[] addFriend(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (!httpRequest.getCookies().containsKey(CasebookWebConstants.CASEBOOK_SESSION_KEY)) {
            return this.redirect(new byte[0], "/login", httpResponse);
        }

        String username = httpResponse.getSession().getAttributes().get("username").toString();
        String friendUsername = httpRequest.getQueryParameters().get("friend");

        User currentUser = this.userRepository.findByUsername(username);
        User friendUser = this.userRepository.findByUsername(friendUsername);

        this.userRepository.addFriend(currentUser, friendUser);

        return this.redirect(
                String.format("Successfully added friend - %s"
                        , friendUser.getUsername()).getBytes(),
                "/users",
                httpResponse);
    }

    @GetMapping(route = "/remove")
    public byte[] removeFriend(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (!httpRequest.getCookies().containsKey(CasebookWebConstants.CASEBOOK_SESSION_KEY)) {
            return this.redirect(new byte[0], "/login", httpResponse);
        }

        String username = httpResponse.getSession().getAttributes().get("username").toString();
        String friendUsername = httpRequest.getQueryParameters().get("friend");

        User currentUser = this.userRepository.findByUsername(username);
        User friendUser = this.userRepository.findByUsername(friendUsername);

        this.userRepository.removeFriend(currentUser, friendUser);

        return this.redirect(
                String.format("Successfully removed friend - %s"
                        , friendUser.getUsername()).getBytes(),
                "/friends",
                httpResponse);
    }
}
