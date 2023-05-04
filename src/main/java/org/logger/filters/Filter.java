package org.logger.filters;

public interface Filter<R> {
    
    boolean filter(R record);
}