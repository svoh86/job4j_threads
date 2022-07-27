package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import java.nio.file.Paths;

/**
 * 4. Скачивание файла с ограничением.
 * Чтобы ограничить скорость скачивания, нужно засечь время скачивания 1024 байт.
 * Если время меньше указанного, то нужно выставить паузу за счет Thread.sleep.
 * Пауза должна вычисляться, а не быть константой.
 *
 * @author Svistunov Mikhail
 * @version 1.1
 * скорость скачивания - байты в секунду. 1 мб/с = 1048576 байт/c.
 * https://proof.ovh.net/files/10Mb.dat
 */
public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(
                     Paths.get(new URL(url).getPath()).getFileName().toString())) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int downloadData = 0;
            long start = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                downloadData += bytesRead;
                if (downloadData >= speed) {
                    long duration = System.currentTimeMillis() - start;
                    if (duration < 1000) {
                        Thread.sleep(1000 - duration);
                    }
                    downloadData = 0;
                    start = System.currentTimeMillis();
                }
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validate(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }

    private static void validate(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Enter url and speed");
        }
        if (!args[0].startsWith("https")) {
            throw new IllegalArgumentException("Parameter url must start with \"https\"");
        }
        if (Integer.parseInt(args[1]) <= 0) {
            throw new IllegalArgumentException("Speed should be positive");
        }
    }
}
