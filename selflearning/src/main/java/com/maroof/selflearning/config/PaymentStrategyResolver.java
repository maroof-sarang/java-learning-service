package com.maroof.selflearning.config;

import com.maroof.selflearning.lld.strategy.CreditCardPayment;
import com.maroof.selflearning.lld.strategy.PaymentStrategy;
import com.maroof.selflearning.lld.strategy.UpiPayment;
import org.springframework.stereotype.Component;

@Component
public class PaymentStrategyResolver {

    public PaymentStrategy resolve(String paymentType) {

        if ("upi".equalsIgnoreCase(paymentType)) {
            return new UpiPayment();
        }

        return new CreditCardPayment();
    }
}
