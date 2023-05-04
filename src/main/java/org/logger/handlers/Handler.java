package org.logger.handlers;

public interface Handler<R> {

    void handle(R record);
}