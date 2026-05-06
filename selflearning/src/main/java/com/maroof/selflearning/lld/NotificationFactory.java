package com.maroof.selflearning.lld;

public class NotificationFactory {

    public static Notification create(String type) {

        if ("email".equalsIgnoreCase(type)) {
            return new EmailNotification();
        }

        if ("sms".equalsIgnoreCase(type)) {
            return new SmsNotification();
        }

        throw new IllegalArgumentException("Invalid notification type");
    }
}
