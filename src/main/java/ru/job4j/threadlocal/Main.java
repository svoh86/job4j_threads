package ru.job4j.threadlocal;

/**
 * @author Svistunov Mikhail
 * @version 1.0
 */
public class Main {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

   static class MyRunnable implements Runnable {
        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            System.out.println(name + " first threadLocal: "
                               + threadLocal.get());
            threadLocal.set(name + " thread value");
            System.out.println(name + " and threadLocal: "
                               + threadLocal.get());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        threadLocal.set("From main thread");
        Thread thread1 = new Thread(new MyRunnable(), "first_thread");
        thread1.start();
        Thread thread2 = new Thread(new MyRunnable(), "second_thread");
        thread2.start();
        thread1.join();
        thread2.join();

        System.out.println("fromMainThread: " + threadLocal.get());
    }
}
