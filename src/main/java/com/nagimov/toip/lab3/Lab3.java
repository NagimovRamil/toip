package com.nagimov.toip.lab3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Lab3 {
    public void execute() {

        final Philosopher[] philosophers = new Philosopher[5];
        Object[] sticks = new Object[philosophers.length];
        Counter[] counters = new Counter[philosophers.length];

        for (int i = 0; i < sticks.length; i++) {
            sticks[i] = new Object();
            counters[i] = new Counter(new AtomicInteger(0));
        }

        for (int i = 0; i < philosophers.length; i++) {
            Object leftStick = sticks[i];
            Object rightStick = sticks[(i + 1) % sticks.length];
            Counter counter = counters[i];

            if (i == philosophers.length - 1) {
                philosophers[i] = new Philosopher(rightStick, leftStick, counter);
            } else {
                philosophers[i] = new Philosopher(leftStick, rightStick, counter);
            }

            Thread t = new Thread(philosophers[i], "Философ " + (i + 1));
            t.start();
        }
    }
}
