package com.basejava.webapp;

public class DeadLock {
    private final static Object LOCK1 = new Object();
    private final static Object LOCK2 = new Object();

    public static void main(String[] args) {
        deadLock(LOCK1, LOCK2);
        deadLock(LOCK2, LOCK1);
    }

    private static void deadLock(Object... lock) {
        new Thread(() -> {
            System.out.println(getName() + " waiting for LOCK1");
            synchronized (lock[0]) {
                System.out.println(getName() + " holding LOCK1");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(getName() + " waiting for LOCK2");
                synchronized (lock[1]) {
                    System.out.println(getName() + " holding LOCK1 & LOCK2");
                }
            }
        }).start();
    }

    private static String getName() {
        return Thread.currentThread().getName();
    }
}
