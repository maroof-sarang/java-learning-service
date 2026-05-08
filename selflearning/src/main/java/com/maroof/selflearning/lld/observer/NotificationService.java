package com.maroof.selflearning.lld.observer;

import java.util.ArrayList;
import java.util.List;

public class NotificationService {

    private final List<Observer> observers =
            new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        observers.forEach(Observer::update);
    }
}
