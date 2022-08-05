package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CacheTest {

    @Test
    void whenAdd() {
        Cache cache = new Cache();
        Base first = new Base(1, 0);
        assertThat(cache.add(first)).isTrue();
        assertThat(cache.add(first)).isFalse();
    }

    @Test
    void whenDelete() {
        Cache cache = new Cache();
        Base first = new Base(1, 0);
        cache.add(first);
        cache.delete(first);
        assertThat(cache.add(first)).isTrue();
    }

    @Test
    void whenAddThenUpdate() {
        Cache cache = new Cache();
        Base first = new Base(1, 0);
        Base second = new Base(1, 0);
        cache.add(first);
        assertThat(cache.update(second)).isTrue();
    }

    @Test
    void whenAddThenUpdateAnotherVersion() {
        Cache cache = new Cache();
        Base first = new Base(1, 0);
        Base second = new Base(1, 1);
        cache.add(first);
        assertThatExceptionOfType(OptimisticException.class)
                .isThrownBy(() -> cache.update(second));
    }
}