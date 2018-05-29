package javache.server;

import javache.constants.MessagesConstants;
import javache.constants.ServerConstants;
import javache.database.services.UserService;
import javache.http.api.HttpSessionStorage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.FutureTask;

public class Server {

    private final int port;
    private final HttpSessionStorage sessionStorage;
    private final UserService userService;

    public Server(final int port,
                  final HttpSessionStorage sessionStorage,
                  final UserService userService) {
        this.port = port;
        this.sessionStorage = sessionStorage;
        this.userService = userService;
    }

    public void run() throws IOException {
        try (final ServerSocket serverSocket = new ServerSocket(this.port)) {
            System.out.println(MessagesConstants.LISTENING_MESSAGE + this.port);

            serverSocket.setSoTimeout(ServerConstants.SOCKET_TIMEOUT_MILLISECONDS);

            while (true) {
                try (final Socket clientSocket = serverSocket.accept()) {

                    clientSocket.setSoTimeout(ServerConstants.SOCKET_TIMEOUT_MILLISECONDS);

                    final ConnectionHandler connectionHandler =
                            new ConnectionHandler(clientSocket,
                                    new RequestHandler(this.sessionStorage, this.userService));

                    final FutureTask<?> task =
                            new FutureTask<>(connectionHandler, null);

                    task.run();
                } catch (SocketTimeoutException e) {
                    System.out.println(MessagesConstants.TIMEOUT_DETECTION_MESSAGE); // TODO - proper exception handling
                }
            }
        }
    }
}
