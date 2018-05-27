package javache.database.util;

import javache.constants.CsvConstants;
import javache.constants.ServerConstants;
import javache.database.entities.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.CREATE;

public class CsvFileManager {

    public CsvFileManager() {
    }

    public void saveAll(Iterable<User> objects) {
        final StringBuilder sb = new StringBuilder();

        objects.forEach(user -> sb
                .append(user.getId()).append(CsvConstants.DELIMITER)
                .append(user.getName()).append(CsvConstants.DELIMITER)
                .append(user.getPassword()).append(CsvConstants.DELIMITER)
                .append(System.lineSeparator()));

        try (final BufferedWriter bufferedWriter =
                     Files.newBufferedWriter(CsvConstants.FILE.toPath(), ServerConstants.SERVER_ENCODING, CREATE)) {
            bufferedWriter.write(sb.toString().trim());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, User> readAll() {
        try (BufferedReader br = Files.newBufferedReader(CsvConstants.FILE.toPath())) {
            return br.lines()
                    .map(line -> {
                                final String[] split = line.split(CsvConstants.DELIMITER);
                                return new User(split[0], split[1], split[2]);
                            }
                    ).collect(Collectors.toMap(User::getName, user -> user));
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }
}
