package com.maroof.selflearning.service;

import com.maroof.selflearning.dto.EmployeeRequest;
import com.maroof.selflearning.dto.EmployeeResponse;
import com.maroof.selflearning.exception.CustomException;
import com.maroof.selflearning.lld.Notification;
import com.maroof.selflearning.lld.NotificationFactory;
import com.maroof.selflearning.lld.strategy.CreditCardPayment;
import com.maroof.selflearning.lld.strategy.PaymentStrategy;
import com.maroof.selflearning.lld.strategy.UpiPayment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EmployeeService {

    @Value("${app.notification.type}")
    private String notificationType;

    @Value("${app.payment.type}")
    private String paymentType;

    @Value("${app.feature.logging.enabled}")
    private boolean loggingEnabled;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    private final AtomicLong idGenerator = new AtomicLong();

    public EmployeeResponse createEmployee(EmployeeRequest request) {

        if (loggingEnabled) {
            logger.info("Received request to create employee");
        }
        validateRequest(request);

        EmployeeResponse response =
                buildEmployeeResponse(request);

        logger.info("Employee created successfully with email: {}",
                request.getEmail());

        sendNotification();

        processPayment();

        return response;
    }

    private EmployeeResponse buildEmployeeResponse(
            EmployeeRequest request) {

        return EmployeeResponse.builder()
                .id(generateId())
                .name(request.getName())
                .email(request.getEmail())
                .build();
    }

    private void validateRequest(EmployeeRequest request) {
        if (request == null) {
            throw new CustomException("Request cannot be null");
        }

        if (request.getName() == null || request.getName().isBlank()) {
            throw new CustomException("Name is required");
        }

        if ("admin".equalsIgnoreCase(request.getName())) {
            throw new CustomException("Reserved employee name");
        }
    }

    private Long generateId() {
        return idGenerator.incrementAndGet();
    }

    private void sendNotification() {
        Notification notification =
                NotificationFactory.create(notificationType);
        notification.send();
    }

    private void processPayment() {

        PaymentStrategy paymentStrategy;

        if ("upi".equalsIgnoreCase(paymentType)) {
            paymentStrategy = new UpiPayment();
        } else {
            paymentStrategy = new CreditCardPayment();
        }

        paymentStrategy.pay();
    }
}
