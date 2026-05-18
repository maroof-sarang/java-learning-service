package com.maroof.selflearning.util;

import java.util.UUID;

public class RequestContext {

    public static String generateRequestId() {
        return UUID.randomUUID().toString();
    }
}
