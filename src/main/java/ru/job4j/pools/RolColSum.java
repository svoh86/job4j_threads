package ru.job4j.pools;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 4. CompletableFuture
 * Задача - подсчитать суммы по строкам и столбцам квадратной матрицы.
 * Реализовать последовательную версию программы.
 * Реализовать асинхронную версию программы.
 * i - я задача считает сумму по i столбцу и i строке.
 *
 * @author Svistunov Mikhail
 * @version 1.0
 * Внесено изменение для проверки внесения изменения в последний коммит
 */
public class RolColSum {
    public record Sums(int rowSum, int colSum) {
    }

    public static Sums[] sum(int[][] matrix) {
        int n = matrix.length;
        Sums[] sums = new Sums[n];
        for (int i = 0; i < n; i++) {
            int sumRow = 0;
            int sumCol = 0;
            for (int j = 0; j < n; j++) {
                sumRow += matrix[i][j];
                sumCol += matrix[j][i];
            }
            sums[i] = new Sums(sumRow, sumCol);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int n = matrix.length;
        Sums[] sums = new Sums[n];
        for (int i = 0; i < n; i++) {
            sums[i] = getTask(matrix, i).get();
        }

        return sums;
    }

    private static CompletableFuture<Sums> getTask(int[][] matrix, int start) {
        return CompletableFuture.supplyAsync(
                () -> {
                    int sumRow = 0;
                    int sumCol = 0;
                    for (int j = 0; j < matrix.length; j++) {
                        sumRow += matrix[start][j];
                        sumCol += matrix[j][start];
                    }
                    return new Sums(sumRow, sumCol);
                }
        );

    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}};
        long start = System.currentTimeMillis();
        System.out.println(Arrays.toString(sum(matrix)));
        long finish = System.currentTimeMillis();
        System.out.println(finish - start);
        start = System.currentTimeMillis();
        System.out.println(Arrays.toString(asyncSum(matrix)));
        finish = System.currentTimeMillis();
        System.out.println(finish - start);
    }
}
