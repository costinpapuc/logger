package org.logger;

import java.time.Instant;

public record LogRecord(LogLevel level, String message, String threadName, Instant timestamp) {
}





