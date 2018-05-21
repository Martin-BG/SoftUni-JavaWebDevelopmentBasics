package javache.server;

import javache.io.Reader;
import javache.io.Writer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ConnectionHandler extends Thread {

    private final Socket clientSocket;
    private final RequestHandler requestHandler;

    ConnectionHandler(final Socket clientSocket, final RequestHandler requestHandler) {
        this.clientSocket = clientSocket;
        this.requestHandler = requestHandler;
    }

    @Override
    public void run() {
        try (final InputStream clientSocketInputStream = this.clientSocket.getInputStream();
             final OutputStream clientSocketOutputStream = this.clientSocket.getOutputStream()) {

            final String requestContent = Reader.readAllLines(clientSocketInputStream);

            final byte[] responseContent = this.requestHandler.handleRequest(requestContent);

            Writer.writeBytes(responseContent, clientSocketOutputStream);
        } catch (IOException e) {
            e.printStackTrace(); // TODO - proper exception handling
        }
    }
}
