package org.logger.handlers;

import org.logger.LogRecord;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MultiHandler<T extends Handler<LogRecord>> implements Handler<LogRecord> {
    private final List<T> handlers;

    public MultiHandler(List<T> handlers) {
        this.handlers = Optional.ofNullable(handlers).orElse(Collections.emptyList());
    }

    @Override
    public void handle(LogRecord record) {
        for(Handler<LogRecord> handler : handlers) {
            handler.handle(record);
        }
    }
}