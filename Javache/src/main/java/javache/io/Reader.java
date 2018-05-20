package javache.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Reader {

    private Reader() {}

    public static String readAllLines(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        final StringBuilder result = new StringBuilder();

        while (bufferedReader.ready()) {
            result.append((char)bufferedReader.read());
        }
        return result.toString();
    }
}
