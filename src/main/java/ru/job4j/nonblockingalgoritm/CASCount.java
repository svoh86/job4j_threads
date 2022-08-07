package ru.job4j.nonblockingalgoritm;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 0. CAS - операции.
 * Реализовать неблокирующий счетчик.
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    /**
     * expectedCount - переменная, которой присвоим текущее значение сount.
     * newCount - переменная, которой присвоим инкремент count.
     * Будем это делать до тех пор, пока count и expectedCount неравны.
     */
    public void increment() {
        int expectedCount;
        int newCount;
        do {
            expectedCount = count.get();
            newCount = expectedCount + 1;
        } while (!count.compareAndSet(expectedCount, newCount));
    }

    public int get() {
        return count.get();
    }
}
