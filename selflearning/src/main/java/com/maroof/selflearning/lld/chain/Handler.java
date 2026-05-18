package com.maroof.selflearning.lld.chain;

public abstract class Handler {

    protected Handler nextHandler;

    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract void handle();
}