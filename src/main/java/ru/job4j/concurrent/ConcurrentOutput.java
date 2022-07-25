package ru.job4j.concurrent;

/**
 * Класс описывает запуск нити.
 * В любой программе по умолчанию есть главная нить.
 * В этой нити выполняются операторы из метода main().
 * Чтобы создать еще одну нить, необходимо воспользоваться классом java.lang.Thread.
 * Метод start() указывает виртуальной машине, что операторы описанные в конструкторе
 * нужно запустить в отдельной нити.
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public class ConcurrentOutput {
    public static void main(String[] args) {
        Thread another = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        another.start();
        second.start();
        System.out.println(Thread.currentThread().getName());
    }
}
