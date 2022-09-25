package com.nagimov.toip.lab3;

import java.util.concurrent.atomic.AtomicInteger;

class Philosopher implements Runnable{

    private Object leftStick;
    private Object rightStick;
    private Counter counter;

    public Philosopher(Object leftStick, Object rightStick, Counter counter) {
        this.leftStick = leftStick;
        this.rightStick = rightStick;
        this.counter = counter;
    }

    private void doAction(String action) throws InterruptedException {
        String currentThreadName = Thread.currentThread().getName();
        System.out.println(currentThreadName + " " + action);
        Thread.sleep(((int) (Math.random() * 100)));
    }

    @Override
    public void run() {

        try {
            while (true) {

                // thinking
                doAction(": думает");
                Thread.sleep(3000);
                synchronized (leftStick) {
                    doAction(": взял левую палочку");
                    Thread.sleep(1000);
                    synchronized (rightStick) {
                        // eating
                        doAction(": взял правую палочку и ест");
                        Thread.sleep(5000);
                        doAction(": положил правую палочку");
                        Thread.sleep(1000);
                    }

                    // Back to thinking
                    doAction(": положил левую палочку и вернулся к размышлениям");
                    counter.increment();
                    System.out.println(Thread.currentThread().getName() + " пообедал " + counter.getValue() + " раз(а)");
                    System.out.println("-----------------------------------------------------");
                    Thread.sleep(3000);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
    }
}
