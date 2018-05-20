package javache;

public class RequestHandler {
    public byte[] handleRequest(final String requestContent) {
        System.out.println(requestContent);
        return new byte[0]; // TODO implement
    }
}
