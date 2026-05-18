package com.maroof.selflearning.lld.chain;

public class ProcessingHandler extends Handler {
    @Override
    public void handle() {

        System.out.println("Processing completed");

        if (nextHandler != null) {
            nextHandler.handle();
        }
    }
}
