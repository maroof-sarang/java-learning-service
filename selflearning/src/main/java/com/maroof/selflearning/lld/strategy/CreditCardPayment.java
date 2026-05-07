package com.maroof.selflearning.lld.strategy;

public class CreditCardPayment implements PaymentStrategy {

    @Override
    public void pay() {
        System.out.println("Paid using Credit Card");
    }
}
