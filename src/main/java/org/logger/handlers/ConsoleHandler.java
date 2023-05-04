package org.logger.handlers;

import org.logger.LogRecord;
import org.logger.formatters.Formatter;

public class ConsoleHandler implements Handler<LogRecord> {
    private final Formatter<LogRecord> formatter;

    public ConsoleHandler(Formatter<LogRecord> formatter) {
        this.formatter = formatter;
    }

    @Override
    public void handle(LogRecord record) {
        String log = formatter.format(record);
        writeToConsole(log);
    }

    private void writeToConsole(String log) {
        System.out.println("CONSOLE " + log);
    }
}