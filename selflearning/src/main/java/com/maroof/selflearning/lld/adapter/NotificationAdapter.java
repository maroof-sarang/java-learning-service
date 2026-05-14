package com.maroof.selflearning.lld.adapter;

import org.springframework.stereotype.Component;

@Component
public class NotificationAdapter {

    private final LegacyNotificationService legacyService =
            new LegacyNotificationService();

    public void send() {
        legacyService.sendLegacyMessage();
    }
}
