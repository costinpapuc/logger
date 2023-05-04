package org.logger.formatters;

public interface Formatter<R> {
    
    String format(R record);
}