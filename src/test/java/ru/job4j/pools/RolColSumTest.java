package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;

class RolColSumTest {
    int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}};
    RolColSum.Sums[] expected = {
            new RolColSum.Sums(6, 12),
            new RolColSum.Sums(15, 15),
            new RolColSum.Sums(24, 18)};

    @Test
    void whenSum() {
        RolColSum.Sums[] sums = RolColSum.sum(matrix);
        assertThat(sums).isEqualTo(expected);
    }

    @Test
    void whenAsyncSum() throws ExecutionException, InterruptedException {
        RolColSum.Sums[] sums = RolColSum.asyncSum(matrix);
        assertThat(sums).isEqualTo(expected);
    }
}