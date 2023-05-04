package org.logger.filters;

import org.logger.LogLevel;
import org.logger.LogRecord;

public class LogLevelFilter implements Filter<LogRecord> {
    private final LogLevel logLevel;

    public LogLevelFilter(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    @Override
    public boolean filter(LogRecord record) {
        return record.level().ordinal() >= logLevel.ordinal();
    }
}