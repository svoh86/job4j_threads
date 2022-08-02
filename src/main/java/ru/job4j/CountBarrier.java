package ru.job4j;

/**
 * 0. Управление нитью через wait.
 * Класс, который блокирует выполнение по условию счетчика.
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public class CountBarrier {
    private final Object monitor = this;
    /**
     * Переменная total содержит количество вызовов метода count().
     */
    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    /**
     * Метод count изменяет состояние программы.
     */
    public synchronized void count() {
        monitor.notifyAll();
        count++;
    }

    /**
     * Нити, которые выполняют метод await, могут начать работу если поле count >= total.
     * Если оно не равно, то нужно перевести нить в состояние wait.
     */
    public synchronized void await() {
        while (count < total) {
            try {
                monitor.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
