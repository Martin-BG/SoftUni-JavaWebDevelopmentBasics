package javache.constants;

import java.io.File;

public final class CsvConstants {

    public static final File FILE = new File(CasebookConstants.CASEBOOK_RESOURCE_PATH + "usersDB.csv");
    public static final String DELIMITER = "" + '\0';

    private CsvConstants() {
    }
}
