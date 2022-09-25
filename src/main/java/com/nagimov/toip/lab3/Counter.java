package com.nagimov.toip.lab3;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {

        private AtomicInteger value;

        public Counter(AtomicInteger value) {
            this.value = value;
        }

        public void increment() {
            value.incrementAndGet();
        }

        public void decrement() {
            value.decrementAndGet();
        }

        public AtomicInteger getValue() {
            return value;
        }
}
