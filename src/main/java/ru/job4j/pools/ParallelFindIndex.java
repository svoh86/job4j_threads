package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * 3. ForkJoinPool
 * метод fork() организует асинхронное выполнение новой задачи.
 * Это аналогично тому, что мы запустили бы рекурсивный метод еще раз.
 * метод join(). Этот метод ожидает завершения задачи и возвращает результат её выполнения,
 * но во время ожидания поток не блокируется, а может начать выполнение других задач.
 * метод invoke(). Этот метод служит для запуска выполнения
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public class ParallelFindIndex extends RecursiveTask<Integer> {
    private final int[] array;
    private final int from;
    private final int to;
    private final int find;

    public ParallelFindIndex(int[] array, int from, int to, int find) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.find = find;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return indexOf(array, from, to, find);
        }
        int mid = (from + to) / 2;
        ParallelFindIndex leftSort = new ParallelFindIndex(array, from, mid, find);
        ParallelFindIndex rightSort = new ParallelFindIndex(array, mid + 1, to, find);
        leftSort.fork();
        rightSort.fork();
        int left = leftSort.join();
        int right = rightSort.join();
        return left == -1 ? right : left;
    }

    private int indexOf(int[] array, int from, int to, int find) {
        int rsl = -1;
        for (int i = from; i <= to; i++) {
            if (array[i] == find) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }

    public static int findIndex(int[] array, int find) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelFindIndex(array, 0, array.length - 1, find));
    }

    public static void main(String[] args) {
        int[] array = new int[100];
        for (int i = 0; i < 100; i++) {
            array[i] = i;
        }
        int rsl = findIndex(array, 77);
        System.out.println(rsl);
    }
}
