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
            System.out.println(Thread.currentThread().getName() + " waiting for LOCK1");
            synchronized (lock[0]) {
                System.out.println(Thread.currentThread().getName() + " holding LOCK1");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + " waiting for LOCK2");
                synchronized (lock[1]) {
                    System.out.println(Thread.currentThread().getName() + " holding LOCK1 & LOCK2");
                }
            }
        }).start();
    }
}
