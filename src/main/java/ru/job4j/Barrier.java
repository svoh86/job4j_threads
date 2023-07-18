package ru.job4j;

/**
 * 0. Управление нитью через wait.
 * У объекта монитора есть методы wait, notifyAll.
 * Метод notifyAll будит все нити, которые ждали изменения состояния.
 * Метод wait переводит нить в состояние ожидания, если программа не может дальше выполняться.
 * Метод on и off меняют флаг с true на false. После каждого изменения программа будит нити, которые ждут изменений.
 * Переменная flag - это общий ресурс, поэтому мы с ней работаем только в критической секции.
 * Синхронизация и методы nofityAll и wait вызываются у объекта класса Barrier.
 * Когда нить заходит в метод check, то она проверяет flag. Если флаг = false, то нить засыпает.
 * Когда другая нить выполнит метод on или off, то у объекта монитора выполняется метод notifyAll.
 * Метод notifyAll переводит все нити из состояния wait в runnable.
 * Переключение нити из состояния wait в runnable операция не атомарная.
 * Это значит, что состояние программы может поменяться, когда нить начнет выполнять полезную работу.
 * Чтобы избежать проблем с согласованностью данных, метод wait всегда вызывается в цикле while,
 * а не в условном блоке if.
 * Это позволяет сделать постпроверку, когда нить перешла в состояние Runnable.
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public class Barrier {
    private boolean flag = false;

    private final Object monitor = this;

    public void on() {
        synchronized (monitor) {
            flag = true;
            monitor.notifyAll();
        }
    }

    public void off() {
        synchronized (monitor) {
            flag = false;
            monitor.notifyAll();
        }
    }

    public void check() {
        synchronized (monitor) {
            while (!flag) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
