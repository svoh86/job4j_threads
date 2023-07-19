package ru.job4j.pools;

import java.util.Arrays;

/**
 * 3. ForkJoinPool
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public class MergeSort {
    public static int[] sort(int[] array) {
        return sort(array, 0, array.length - 1);
    }

    private static int[] sort(int[] array, int from, int to) {
        /*
         * при следующем условии, массив из одного элемента
         * делить нечего, возвращаем элемент
         */
        if (from == to) {
            int[] ar = new int[array.length];
            ar = new int[]{array[from]};
            return ar;
        }
        /*
          попали сюда, значит в массиве более одного элемента
          находим середину и объединяем отсортированные части
         */
        int mid = (from + to) / 2;
        return merge(
                // сортируем левую часть
                sort(array, from, mid),
                // сортируем правую часть
                sort(array, mid + 1, to)
        );
    }

    /**
     * Метод объединения двух отсортированных массивов
     *
     * @param left  left
     * @param right right
     * @return int[]
     */

    public static int[] merge(int[] left, int[] right) {
        int li = 0;
        int ri = 0;
        int resI = 0;
        int[] result = new int[left.length + right.length];
        while (resI != result.length) {
            if (li == left.length) {
                result[resI++] = right[ri++];
            } else if (ri == right.length) {
                result[resI++] = left[li++];
            } else if (left[li] <= right[ri]) {
                result[resI++] = left[li++];
            } else {
                result[resI++] = right[ri++];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] array = {3, 4, 2, 5, 1};
        int[] rsl = sort(array);
        System.out.println(Arrays.toString(rsl));
    }
}
