package com.hawolt;

public class SynchronizedDouble {
    private final Object lock = new Object();

    private double d;

    public SynchronizedDouble() {
        this(0D);
    }

    public SynchronizedDouble(double d) {
        this.d = d;
    }

    public double get() {
        synchronized (lock) {
            return d;
        }
    }

    public void subtract(double d) {
        synchronized (lock) {
            this.d -= d;
        }
    }

    public double subtractAndGet(double d) {
        synchronized (lock) {
            return (this.d -= d);
        }
    }
}
