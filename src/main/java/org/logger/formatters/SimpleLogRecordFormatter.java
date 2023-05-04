package org.logger.formatters;

import org.logger.LogRecord;

public class SimpleLogRecordFormatter implements Formatter<LogRecord> {

    @Override
    public String format(LogRecord record) {
        return String.format("%s %s [%s]: %s", record.timestamp(), record.level(), record.threadName(), record.message());
    }
}