package com.maroof.selflearning.service;

import com.maroof.selflearning.dto.EmployeeRequest;
import com.maroof.selflearning.dto.EmployeeResponse;
import com.maroof.selflearning.exception.CustomException;
import com.maroof.selflearning.lld.Notification;
import com.maroof.selflearning.lld.NotificationFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    private final AtomicLong idGenerator = new AtomicLong();

    public EmployeeResponse createEmployee(EmployeeRequest request) {

        logger.info("Creating employee with email: {}", request.getEmail());

        validateRequest(request);

        EmployeeResponse employeeResponse = EmployeeResponse.builder()
                .id(generateId())
                .name(request.getName())
                .email(request.getEmail())
                .build();

        logger.info("Employee created successfully with email: {}",
                request.getEmail());

        sendNotification();

        return employeeResponse;
    }

    private void validateRequest(EmployeeRequest request) {
        if (request == null) {
            throw new CustomException("Request cannot be null");
        }

        if (request.getName() == null || request.getName().isBlank()) {
            throw new CustomException("Name is required");
        }
    }

    private Long generateId() {
        return idGenerator.incrementAndGet();
    }

    private void sendNotification() {
        Notification notification = NotificationFactory.create("email");
        notification.send();
    }
}
