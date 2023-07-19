package ru.job4j.pools;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author Svistunov Mikhail
 * @version 1.0
 */
public class ParallelMergeSort extends RecursiveTask<int[]> {
    private final int[] array;
    private final int from;
    private final int to;

    public ParallelMergeSort(int[] array, int from, int to) {
        this.array = array;
        this.from = from;
        this.to = to;
    }

    @Override
    protected int[] compute() {
        if (from == to) {
            return new int[]{array[from]};
        }
        int mid = (from + to) / 2;
        /*
        Создаем задачи для сортировки частей
         */
        ParallelMergeSort leftSort = new ParallelMergeSort(array, from, mid);
        ParallelMergeSort rightSort = new ParallelMergeSort(array, mid + 1, to);
        /*
        Производим деление.
        Оно будет происходить, пока в частях не останется по одному элементу
        */
        leftSort.fork();
        rightSort.fork();
        /*
        Объединяем полученные результаты
        */
        int[] left = leftSort.join();
        int[] right = rightSort.join();
        return MergeSort.merge(left, right);
    }

    public static int[] sort(int[] array) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelMergeSort(array, 0, array.length - 1));
    }

    public static void main(String[] args) {
        int[] array = {3, 4, 2, 5, 1};
        int[] rsl = sort(array);
        System.out.println(Arrays.toString(rsl));
    }
}
