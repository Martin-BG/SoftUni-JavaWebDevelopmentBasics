package javache.server;

import javache.constants.MessagesConstants;
import javache.constants.ServerConstants;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.FutureTask;

public class Server {

    private final int port;

    public Server(final int port) {
        this.port = port;
    }

    public void run() throws IOException {
        try (final ServerSocket serverSocket = new ServerSocket(this.port)) {
            System.out.println(MessagesConstants.LISTENING_MESSAGE + this.port);

            serverSocket.setSoTimeout(ServerConstants.SOCKET_TIMEOUT_MILLISECONDS);

            while (true) {
                try (final Socket clientSocket = serverSocket.accept()) {

                    clientSocket.setSoTimeout(ServerConstants.SOCKET_TIMEOUT_MILLISECONDS);

                    final ConnectionHandler connectionHandler =
                            new ConnectionHandler(clientSocket, new RequestHandler());

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
