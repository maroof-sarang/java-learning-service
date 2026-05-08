package com.maroof.selflearning.lld.observer;

public class EmailObserver implements Observer {

    @Override
    public void update() {
        System.out.println("Email notification received");
    }
}
