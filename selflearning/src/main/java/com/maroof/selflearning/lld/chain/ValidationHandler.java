package com.maroof.selflearning.lld.chain;

public class ValidationHandler extends Handler {
    @Override
    public void handle() {

        System.out.println("Validation completed");

        if (nextHandler != null) {
            nextHandler.handle();
        }
    }
}
