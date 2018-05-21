package javache.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.FutureTask;

import static javache.constants.WebConstants.SOCKET_TIMEOUT_MILLISECONDS;

public class Server {

    private final int port;

    public Server(final int port) {
        this.port = port;
    }

    public void run() throws IOException {
        try (final ServerSocket serverSocket = new ServerSocket(this.port)) {

            serverSocket.setSoTimeout(SOCKET_TIMEOUT_MILLISECONDS);

            while (true) {
                try (final Socket clientSocket = serverSocket.accept()) {

                    clientSocket.setSoTimeout(SOCKET_TIMEOUT_MILLISECONDS);

                    final ConnectionHandler connectionHandler =
                            new ConnectionHandler(clientSocket, new RequestHandler());

                    final FutureTask<?> task =
                            new FutureTask<>(connectionHandler, null);

                    task.run();
                } catch (SocketTimeoutException e) {
                    System.out.println("Time Out"); // TODO - proper exception handling
                }
            }
        }
    }
}
