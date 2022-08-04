package ru.job4j;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.assertj.core.api.Assertions.*;

/**
 * 3. Junit тест для блокирующей очереди.
 */
class SimpleBlockingQueueTest {
    /**
     * Здесь мы проверяем, что очередь пустая или нить выключили.
     * Если producer закончил свою работу и сразу подаст сигнал об отключении consumer,
     * то мы не сможем прочитать все данные.
     * С другой стороны, если мы успели прочитать все данные и находимся в режиме wait,
     * пришедший сигнал запустит нить и проверит состояние очереди и завершит цикл.
     * Потребитель закончит свою работу.
     * <p>
     * Сначала дожидаемся завершения работы producer.
     * Далее посылаем сигнал, что consumer можно остановиться.
     * Ждем пока consumer прочитает все данные и завершит свою работу.
     *
     * @throws InterruptedException исключение
     */
    @Test
    void whenFetchAllThenGetIt() throws InterruptedException {
        CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    for (int i = 1; i < 10; i++) {
                        try {
                            queue.offer(i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                },
                "Producer"
        );
        Thread consumer = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                },
                "Consumer "
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.interrupt();
        assertThat(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9)).isEqualTo(buffer);
    }
}