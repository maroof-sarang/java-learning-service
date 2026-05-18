package com.maroof.selflearning.service;

import com.maroof.selflearning.cache.EmployeeCacheService;
import com.maroof.selflearning.config.PaymentStrategyResolver;
import com.maroof.selflearning.dto.EmployeeRequest;
import com.maroof.selflearning.dto.EmployeeResponse;
import com.maroof.selflearning.exception.CustomException;
import com.maroof.selflearning.lld.Notification;
import com.maroof.selflearning.lld.NotificationFactory;
import com.maroof.selflearning.lld.adapter.NotificationAdapter;
import com.maroof.selflearning.lld.strategy.PaymentStrategy;
import com.maroof.selflearning.util.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EmployeeService {

    private final String notificationType;
    private final String paymentType;
    private final boolean loggingEnabled;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    private final AtomicLong idGenerator = new AtomicLong();

    private final NotificationAdapter notificationAdapter;

    private final PaymentStrategyResolver paymentStrategyResolver;

    private final EmployeeCacheService employeeCacheService;

    public EmployeeService(NotificationAdapter notificationAdapter,
                           PaymentStrategyResolver paymentStrategyResolver,
                           EmployeeCacheService employeeCacheService,
                           @Value("${app.notification.type}") String notificationType,
                           @Value("${app.payment.type}") String paymentType,
                           @Value("${app.feature.logging.enabled}") boolean loggingEnabled) {
        this.notificationAdapter = notificationAdapter;
        this.paymentStrategyResolver = paymentStrategyResolver;
        this.employeeCacheService = employeeCacheService;

        this.notificationType = notificationType;
        this.paymentType = paymentType;
        this.loggingEnabled = loggingEnabled;
    }

    /**
     * Creates a new employee response based on request input.
     * Performs validation, notification handling,
     * and payment processing orchestration.
     */
    public EmployeeResponse createEmployee(EmployeeRequest request) {

        String requestId =
                RequestContext.generateRequestId();

        if (loggingEnabled) {
            logger.info("Request Id: {} - Received request to create employee",
                    requestId);
        }
        validateRequest(request);

        EmployeeResponse cachedResponse =
                employeeCacheService.get(request.getEmail());

        if (cachedResponse != null) {
            if (loggingEnabled) {
                logger.info(
                        "Request Id: {} - Returning employee from cache",
                        requestId
                );
            }

            return cachedResponse;
        }

        EmployeeResponse response =
                buildEmployeeResponse(request);

        if (loggingEnabled) {
            logger.info(
                    "Request Id: {} - Employee created successfully with email: {}",
                    requestId,
                    request.getEmail()
            );
        }
        sendNotification();

        processPayment();

        sendLegacyNotification();

        employeeCacheService.put(
                request.getEmail(),
                response
        );

        return response;
    }

    /**
     * Builds employee response object from request data.
     */
    private EmployeeResponse buildEmployeeResponse(
            EmployeeRequest request) {

        return EmployeeResponse.builder()
                .id(generateId())
                .name(request.getName())
                .email(request.getEmail())
                .build();
    }

    /**
     * Performs defensive and business validations
     * for incoming employee requests.
     */
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

    /**
     * Processes employee payment flow using
     * configurable strategy selection.
     */
    private void processPayment() {

        PaymentStrategy paymentStrategy =
                paymentStrategyResolver.resolve(paymentType);

        paymentStrategy.pay();
    }

    /**
     * Sends notification using adapter integration
     * for legacy notification service compatibility.
     */
    private void sendLegacyNotification() {

        notificationAdapter.send();
    }
}
