package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {

    @Test
    void offer() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    for (int i = 0; i < 5; i++) {
                        queue.offer(i);
                    }
                },
                "Producer"
        );
        Thread consumer = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    try {
                        queue.poll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                },
                "Consumer "
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
    }
}