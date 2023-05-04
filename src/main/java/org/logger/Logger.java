package org.logger;

import org.logger.handlers.*;

import java.time.Instant;
import java.util.concurrent.*;

public class Logger {
    private static final Handler<LogRecord> handler;
    private static final ExecutorService executorService;

    static {
        handler = HandlerFactory.getHandler();
        executorService = createSingleThreadTimeoutExecutorService();
    }

    private static ExecutorService createSingleThreadTimeoutExecutorService() {
        ThreadPoolExecutor e =
                new ThreadPoolExecutor(1, 1,
                        100, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        e.allowCoreThreadTimeOut(true);
        return e;
    }

    public static void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    public static void info(String message) {
        log(LogLevel.INFO, message);
    }

    public static void warn(String message) {
        log(LogLevel.WARNING, message);
    }

    public static void error(String message) {
        log(LogLevel.ERROR, message);
    }

    private static void log(LogLevel level, String message) {
        LogRecord logRecord = new LogRecord(level, message, Thread.currentThread().getName(), Instant.now());
        executorService.execute(() -> handler.handle(logRecord));
    }
}