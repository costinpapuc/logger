package org.logger.handlers;

import org.logger.LogRecord;
import org.logger.formatters.Formatter;

public class FileHandler implements Handler<LogRecord> {
    private final String fileName;
    private final Formatter<LogRecord> formatter;

    public FileHandler(String fileName, Formatter<LogRecord> formatter) {
        this.fileName = fileName;
        this.formatter = formatter;
    }

    @Override
    public void handle(LogRecord record) {
        String log = formatter.format(record);
        writeToFile(log);
    }

    private void writeToFile(String log) {
        System.out.println("FILE(" + fileName + ") " + log);
    }
}