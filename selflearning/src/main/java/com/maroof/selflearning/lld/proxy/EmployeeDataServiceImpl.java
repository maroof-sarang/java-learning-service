package com.maroof.selflearning.lld.proxy;

public class EmployeeDataServiceImpl
        implements EmployeeDataService {

    @Override
    public void fetchData() {
        System.out.println("Fetching employee data");
    }
}
