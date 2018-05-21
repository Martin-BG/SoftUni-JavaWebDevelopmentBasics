package javache;

import javache.constants.WebConstants;
import javache.server.Server;

import java.io.IOException;

public class StartUp {

    public static void main(String[] args) {

        final Server server = new Server(WebConstants.SERVER_PORT);

        try {
            server.run();
        } catch (IOException e) {
            e.printStackTrace(); // TODO - proper exception handling
        }
    }
}
