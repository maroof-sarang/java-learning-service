package com.maroof.selflearning.lld.strategy;

public class UpiPayment implements PaymentStrategy {

    @Override
    public void pay() {
        System.out.println("Paid using UPI");
    }
}
