package org.softuni.javache.util;

public class LoggingService {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String INFO = "\u001B[36m" + "[INFO] ";
    private static final String WARNING = "\u001B[33m" + "[WARNING] ";
    private static final String ERROR = "\u001B[31m" + "[ERROR] ";

    public void info(String content) {
        System.out.println(INFO + content + ANSI_RESET);
    }

    public void warning(String content) {
        System.out.println(WARNING + content + ANSI_RESET);
    }

    public void error(String content) {
        System.out.println(ERROR + content + ANSI_RESET);
    }
}
