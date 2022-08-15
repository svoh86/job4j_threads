package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

/**
 * 1. Реализовать ThreadPool.
 * Клиент берет ресурс из пула, выполняет свою работу и возвращает обратно в пул.
 * Количество нитей всегда одинаковое и равно size.
 * В каждую нить передается блокирующая очередь tasks.
 * В методе run мы должны получить задачу из очереди tasks.
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public class ThreadPool {
    /**
     * Инициализация пула должна быть по количеству ядер в системе (size).
     */
    private final int size = Runtime.getRuntime().availableProcessors();
    private final List<Thread> threads = new LinkedList<>();
    /**
     * tasks - это блокирующая очередь.
     * Если в очереди нет элементов, то нить переводится в состояние Thread.State.WAITING.
     * Когда приходит новая задача, всем нитям в состоянии Thread.State.WAITING
     * посылается сигнал проснуться и начать работу.
     *
     * @see SimpleBlockingQueue
     */
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);

    public ThreadPool() {
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(
                    () -> {
                        try {
                            while (!Thread.currentThread().isInterrupted()) {
                                tasks.poll().run();
                            }
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    },
                    "Thread" + i
            ));
        }
        for (Thread thread : threads) {
            thread.start();
        }
    }

    /**
     * Метод добавляет задачи в блокирующую очередь tasks.
     *
     * @param job задача
     * @throws InterruptedException исключение
     */
    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    /**
     * Метод завершит все запущенные задачи.
     */
    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        threadPool.work(() -> System.out.println("first"));
        threadPool.work(() -> System.out.println("second"));
        threadPool.work(() -> System.out.println("third"));
        threadPool.shutdown();
    }
}
