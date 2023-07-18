package ru.job4j.threadlocal;

/**
 * @author Svistunov Mikhail
 * @version 1.0
 */
public class SecondThread extends Thread{
    @Override
    public void run() {
        ThreadLocalDemo.tl.set("Это поток 2.");
        System.out.println(ThreadLocalDemo.tl.get());
    }
}
