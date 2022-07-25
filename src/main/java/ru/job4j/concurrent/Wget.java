package ru.job4j.concurrent;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

/**
 * 1.2. Режим ожидания.
 * Класс симулирует процесс загрузки.
 * Символ \r указывает, что каретку каждый раз нужно вернуть в начало строки.
 * Это позволяет через промежуток времени обновить строчку.
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public class Wget {
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    try {
                        for (int index = 0; index <= 100; index++) {
                            Thread.sleep(1000);
                            System.out.print("\rLoading : " + index + "%");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        thread.start();
    }
}
