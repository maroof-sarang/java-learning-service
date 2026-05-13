package com.maroof.selflearning.lld.template;

public abstract class DataProcessor {

    public final void process() {
        readData();
        validateData();
        saveData();
    }

    abstract void readData();

    abstract void validateData();

    abstract void saveData();
}