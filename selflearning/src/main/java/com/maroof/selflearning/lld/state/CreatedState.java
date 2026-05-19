package com.maroof.selflearning.lld.state;

public class CreatedState implements OrderState {

    @Override
    public void handle() {
        System.out.println("Order created");
    }
}
