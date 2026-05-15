package com.maroof.selflearning.lld.proxy;

public class EmployeeDataProxy
        implements EmployeeDataService {

    private final EmployeeDataServiceImpl service =
            new EmployeeDataServiceImpl();

    @Override
    public void fetchData() {

        System.out.println("Checking access before fetch");

        service.fetchData();
    }
}
