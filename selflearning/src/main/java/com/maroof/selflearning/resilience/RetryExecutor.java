package com.maroof.selflearning.resilience;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RetryExecutor {

    private static final Logger logger =
            LoggerFactory.getLogger(RetryExecutor.class);

    public void execute(Runnable runnable) {

        int attempts = 0;

        while (attempts < 3) {

            try {

                runnable.run();

                return;

            } catch (Exception ex) {

                attempts++;

                logger.error(
                        "Retry attempt {} failed",
                        attempts
                );
            }
        }

        logger.error("Operation failed after retries");
    }
}
