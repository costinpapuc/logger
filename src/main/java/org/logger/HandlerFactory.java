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
        String level = System.getProperty("org.logger.targets." + target + ".level");
        if (level == null || level.length() == 0) {
            level = System.getProperty("org.logger.level");
        }
        LogLevel logLevel = Optional.ofNullable(level).filter(lvl -> !emptyString(lvl)).map(lvl -> {
            try {
                return LogLevel.valueOf(lvl.toUpperCase());
            } catch (Exception e) {
                System.out.println("LOGGER: Unknown level " + lvl + ". Default to 'INFO' level.");
                return LogLevel.INFO;
            }
        }).orElse(LogLevel.INFO);
        return new LogLevelFilter(logLevel);
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
