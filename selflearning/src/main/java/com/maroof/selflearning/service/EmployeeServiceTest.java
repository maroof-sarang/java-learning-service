package com.maroof.selflearning.service;

import com.maroof.selflearning.cache.EmployeeCacheService;
import com.maroof.selflearning.config.PaymentStrategyResolver;
import com.maroof.selflearning.dto.EmployeeRequest;
import com.maroof.selflearning.dto.EmployeeResponse;
import com.maroof.selflearning.lld.adapter.NotificationAdapter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeServiceTest {

    private final NotificationAdapter notificationAdapter =
            new NotificationAdapter();

    private final PaymentStrategyResolver paymentStrategyResolver =
            new PaymentStrategyResolver();

    private final EmployeeCacheService employeeCacheService =
            new EmployeeCacheService();

    private final EmployeeService employeeService =
            new EmployeeService(
                    notificationAdapter,
                    paymentStrategyResolver,
                    employeeCacheService,
                    "email",
                    "upi",
                    true
            );

    @Test
    void shouldCreateEmployeeSuccessfully() {

        EmployeeRequest request = new EmployeeRequest();
        request.setName("Test");
        request.setEmail("test@test.com");

        EmployeeResponse response =
                employeeService.createEmployee(request);

        assertNotNull(response);
        assertEquals("Test", response.getName());
        assertEquals("test@test.com", response.getEmail());
    }

    @Test
    void shouldThrowExceptionForReservedName() {

        EmployeeRequest request = new EmployeeRequest();
        request.setName("admin");
        request.setEmail("admin@test.com");

        Exception exception = assertThrows(RuntimeException.class, () ->
                employeeService.createEmployee(request));

        assertEquals("Reserved employee name",
                exception.getMessage());
    }

}
