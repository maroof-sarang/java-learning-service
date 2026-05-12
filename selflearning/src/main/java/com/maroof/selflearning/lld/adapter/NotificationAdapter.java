package com.maroof.selflearning.lld.adapter;

public class NotificationAdapter {

    private final LegacyNotificationService legacyService =
            new LegacyNotificationService();

    public void send() {
        legacyService.sendLegacyMessage();
    }
}
