package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 1. Реализовать шаблон Producer Consumer.
 * Это блокирующая очередь, ограниченная по размеру.
 * В данном шаблоне Producer помещает данные в очередь,
 * а Consumer извлекает данные из очереди.
 * Если очередь заполнена полностью, то при попытке добавления поток Producer блокируется,
 * до тех пор пока Consumer не извлечет очередные данные,
 * т.е. в очереди появится свободное место.
 * И наоборот если очередь пуста поток Consumer блокируется,
 * до тех пор пока Producer не поместит в очередь данные.
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
@ThreadSafe
public class SimpleBlockingQueue<T> {
    private final int total;
    private final Object monitor = this;
    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    public SimpleBlockingQueue(int total) {
        this.total = total;
    }

    /**
     * Поток ждет, если размер очереди больше или равен total.
     * Иначе добавляет и вызывает notify().
     *
     * @param value значение
     */
    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() >= total) {
            monitor.wait();
        }
        queue.offer(value);
        System.out.println("Producer add: " + value);
        System.out.println("Queue size is: " + queue.size());
        monitor.notify();
    }

    /**
     * Если в очереди нет элементов - поток ждет пока в offer добавится элемент
     * и вызовется notify().
     * Потом берем элемент из очереди и вызываем notify().
     *
     * @return элемент из очереди
     */
    public synchronized T poll() throws InterruptedException {
        while (queue.size() == 0) {
            monitor.wait();
        }
        T element = queue.poll();
        System.out.println("Consumer retrieved: " + element);
        monitor.notify();
        return element;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}
