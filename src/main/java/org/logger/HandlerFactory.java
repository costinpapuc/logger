package org.logger;

import org.logger.filters.LogLevelFilter;
import org.logger.formatters.SimpleLogRecordFormatter;
import org.logger.handlers.*;

import java.util.Optional;
import java.util.stream.Stream;

public class HandlerFactory {
    private static final String TARGET_CONSOLE = "console";
    private static final String TARGET_FILE = "file";
    private static final String DEFAULT_LOG_FILE_NAME = "log.txt";
    private static final LogLevel DEFAULT_LOG_LEVEL = LogLevel.INFO;

    public static Handler<LogRecord> getHandler() {
        String targets = System.getProperty("org.logger.targets");
        if (emptyString(targets)) {
            String target = TARGET_CONSOLE;
            return new FilteredHandler<>(getFilter(target), getHandlerByTarget(target));
        } else {
            String[] targetsArray = targets.split(",");
            if (targetsArray.length > 1) {
                return new MultiHandler<>(
                        Stream.of(targetsArray)
                                .map(target -> new FilteredHandler<>(getFilter(target), getHandlerByTarget(target)))
                                .toList()
                );
            } else {
                return new FilteredHandler<>(getFilter(targets), getHandlerByTarget(targets));
            }
        }
    }

    private static LogLevelFilter getFilter(String target) {
        String targetLevel = System.getProperty("org.logger.targets." + target + ".level");
        String level = emptyString(targetLevel) ? System.getProperty("org.logger.level") : targetLevel;
        LogLevel logLevel = getLogLevelOrDefault(level);
        return new LogLevelFilter(logLevel);
    }

    private static LogLevel getLogLevelOrDefault(String logLevel) {
        if (emptyString(logLevel)) {
            return DEFAULT_LOG_LEVEL;
        }

        try {
            return LogLevel.valueOf(logLevel.toUpperCase());
        } catch (Exception e) {
            System.out.println("LOGGER: Unknown level " + logLevel + ". Default to '" + DEFAULT_LOG_LEVEL + "' level.");
            return DEFAULT_LOG_LEVEL;
        }
    }

    private static Handler<LogRecord> getHandlerByTarget(String target) {
        if (TARGET_FILE.equalsIgnoreCase(target)) {
            String fileName = Optional.ofNullable(System.getProperty("org.logger.targets.file.filename"))
                    .filter(fn -> !emptyString(fn)).orElse(DEFAULT_LOG_FILE_NAME);
            return new FileHandler(fileName, new SimpleLogRecordFormatter());
        } else if (TARGET_CONSOLE.equalsIgnoreCase(target)) {
            return new ConsoleHandler(new SimpleLogRecordFormatter());
        }

        System.out.println("LOGGER: Unknown target " + target + ". Default to 'console' target.");
        return new ConsoleHandler(new SimpleLogRecordFormatter());
    }

    private static boolean emptyString(String value) {
        return value == null || value.length() == 0;
    }
}
