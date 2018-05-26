package javache.io;

import javache.constants.ServerConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class Reader {

    private Reader() {
    }

    public static String readAllLines(final InputStream inputStream) throws IOException {
        final BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream, ServerConstants.SERVER_ENCODING));

        final StringBuilder result = new StringBuilder();

        while (bufferedReader.ready()) {
            result.append((char) bufferedReader.read());
        }

        return result.toString();
    }
}
