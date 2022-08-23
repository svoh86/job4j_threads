package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ParallelFindIndexTest {
    private final Integer[] array = {0, -10, 1, 11, 3, 76, 5, 8, 10, 65, -13, 56, 45, 4};
    private final Character[] arrayChars = {'d', 'b', 'a', 'e', 'c'};

    @Test
    void whenFindIndex() {
        int index = ParallelFindIndex.findIndex(array, -13);
        assertThat(index).isEqualTo(10);
    }

    @Test
    void whenNotFindIndex() {
        int index = ParallelFindIndex.findIndex(array, 57);
        assertThat(index).isEqualTo(-1);
    }

    @Test
    void whenFindIndexInArrayChars() {
        int index = ParallelFindIndex.findIndex(arrayChars, 'e');
        assertThat(index).isEqualTo(3);
    }

    @Test
    void whenNotFindIndexInArrayChars() {
        int index = ParallelFindIndex.findIndex(arrayChars, 'f');
        assertThat(index).isEqualTo(-1);
    }
}