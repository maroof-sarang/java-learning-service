package com.maroof.selflearning.service;

import com.maroof.selflearning.dto.EmployeeRequest;
import com.maroof.selflearning.dto.EmployeeResponse;
import org.springframework.stereotype.Service;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EmployeeService {

    private final AtomicLong idGenerator = new AtomicLong();

    public EmployeeResponse createEmployee(EmployeeRequest request) {
        validateRequest(request);

        return EmployeeResponse.builder()
                .id(generateId())
                .name(request.getName())
                .email(request.getEmail())
                .build();
    }

    private void validateRequest(EmployeeRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        if (request.getName() == null || request.getName().isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }
    }

    private Long generateId() {
        return idGenerator.incrementAndGet();
    }
}
