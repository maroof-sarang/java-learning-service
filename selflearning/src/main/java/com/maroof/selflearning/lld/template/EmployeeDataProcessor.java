package com.maroof.selflearning.lld.template;

public class EmployeeDataProcessor extends DataProcessor{

    @Override
    void readData() {
        System.out.println("Reading employee data");
    }

    @Override
    void validateData() {
        System.out.println("Validating employee data");
    }

    @Override
    void saveData() {
        System.out.println("Saving employee data");
    }
}