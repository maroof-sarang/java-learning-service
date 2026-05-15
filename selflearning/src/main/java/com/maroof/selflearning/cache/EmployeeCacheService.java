package com.maroof.selflearning.cache;

import com.maroof.selflearning.dto.EmployeeResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmployeeCacheService {

    private final Map<String, EmployeeResponse> cache =
            new HashMap<>();

    public EmployeeResponse get(String email) {
        return cache.get(email);
    }

    public void put(String email,
                    EmployeeResponse response) {

        cache.put(email, response);
    }
}
