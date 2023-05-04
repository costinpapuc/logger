package org.logger;

import static java.util.concurrent.CompletableFuture.runAsync;
import static org.logger.Logger.*;

public class Main {

    public static void main(String[] args) {
        debug("This is a debug message");
        info("This is a info message");
        runAsync(() -> {
            warn("This is a warn message");
            error("This is a error message");
        });
    }
}
