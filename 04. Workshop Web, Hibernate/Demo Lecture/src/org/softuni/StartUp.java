package org.softuni;

import org.softuni.casebook.CasebookApplication;
import org.softuni.javache.Server;
import org.softuni.javache.WebConstants;
import org.softuni.javache.api.RequestHandler;
import org.softuni.javache.http.HttpSessionStorage;
import org.softuni.javache.http.HttpSessionStorageImpl;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class StartUp {
    public static void main(String[] args) {
        start(args);
    }

    private static HttpSessionStorage getSessionStorage() {
        return new HttpSessionStorageImpl();
    }

    private static Set<RequestHandler> initializeApplications() {
        return new HashSet<RequestHandler>() {{
            add(new CasebookApplication(getSessionStorage()));
        }};
    }

    private static void start(String[] args) {
        int port = WebConstants.DEFAULT_SERVER_PORT;

        if (args.length > 1) {
            port = Integer.parseInt(args[1]);
        }

        Server server = new Server(port, initializeApplications());

        try {
            server.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
