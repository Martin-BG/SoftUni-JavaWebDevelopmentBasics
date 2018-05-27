package javache;

import javache.constants.ServerConstants;
import javache.database.services.UserService;
import javache.http.impl.HttpSessionStorageImpl;
import javache.server.Server;

import java.io.IOException;

public class StartUp {

    public static void main(final String[] args) {
        start(args);
    }

    private static void start(final String[] args) {
        final int port = (args.length > 1) ?
                Integer.parseInt(args[1]) :
                ServerConstants.SERVER_PORT;

        final Server server = new Server(
                port,
                new HttpSessionStorageImpl(),
                new UserService());

        try {
            server.run();
        } catch (IOException e) {
            e.printStackTrace(); // TODO - proper exception handling
        }
    }
}
