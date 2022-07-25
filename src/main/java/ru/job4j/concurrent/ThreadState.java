package ru.job4j.concurrent;

/**
 * 1.1. Состояние нити.
 * Получить состояние нити - getState().
 * Этот метод возвращает перечисления Thread.State.
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED
               && second.getState() != Thread.State.TERMINATED) {
            System.out.println(first.getState() + " " + second.getState());
        }
        System.out.println("Работа завершена");
    }
}
