package org.logger.handlers;

import org.logger.filters.Filter;

public class FilteredHandler<R> implements Handler<R> {
    private final Filter<R> filter;
    private final Handler<R> handler;

    public FilteredHandler(Filter<R> filter, Handler<R> handler) {
        this.filter = filter;
        this.handler = handler;
    }

    @Override
    public void handle(R record) {
        if (filter.filter(record)) {
            handler.handle(record);
        }
    }
}