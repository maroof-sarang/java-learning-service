package com.maroof.selflearning.lld.facade;

public class OrderFacade {

    private final OrderService orderService =
            new OrderService();

    private final PaymentService paymentService =
            new PaymentService();

    public void placeOrder() {

        orderService.createOrder();

        paymentService.processPayment();
    }
}
