package com.smnprn.simpleaudioplayer.log;

import org.apache.log4j.*;

public class LogSetter {
    ConsoleAppender consoleAppender;
    FileAppender fileAppender;

    public void createConsoleAppender() {
        consoleAppender = new ConsoleAppender();
        consoleAppender.setThreshold(Level.INFO);
        consoleAppender.setLayout(new PatternLayout("%d [%p | %c | %C{1}] %m%n"));
        consoleAppender.activateOptions();
    }

    public void createFileAppender() {
        fileAppender = new FileAppender();
        fileAppender.setThreshold(Level.WARN);
        fileAppender.setLayout(new PatternLayout("%d [%p | %c | %C{1}] %m%n"));
        fileAppender.setFile("src/main/java/com/smnprn/simpleaudioplayer/log/log_files/log.txt");
        fileAppender.activateOptions();
    }

    public void addAppenders() {
        Logger.getRootLogger().addAppender(consoleAppender);
        Logger.getRootLogger().addAppender(fileAppender);
    }

    public void configureLog() {
        createConsoleAppender();
        createFileAppender();
        addAppenders();
    }
}
