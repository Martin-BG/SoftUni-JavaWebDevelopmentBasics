package javache.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public final class Reader {

    private Reader() {
    }

    public static String readAllLines(final InputStream inputStream) throws IOException {
        final BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        final StringBuilder result = new StringBuilder();

        while (bufferedReader.ready()) {
            result.append((char) bufferedReader.read());
        }

        return result.toString();
    }
}
