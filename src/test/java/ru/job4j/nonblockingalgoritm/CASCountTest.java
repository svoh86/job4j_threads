package ru.job4j.nonblockingalgoritm;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CASCountTest {

    @Test
    void whenExecute10ThreadThen10() throws InterruptedException {
        CASCount casCount = new CASCount();
        Thread first = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        casCount.increment();
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        casCount.increment();
                    }
                }
        );
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(casCount.get()).isEqualTo(10);
    }
}