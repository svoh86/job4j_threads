package ru.job4j.threadlocal;

/**
 * @author Svistunov Mikhail
 * @version 1.0
 */
public class FirstThread extends Thread{
    @Override
    public void run() {
        ThreadLocalDemo.tl.set("Это поток 1.");
        System.out.println(ThreadLocalDemo.tl.get());
    }
}
