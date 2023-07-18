package ru.job4j.noi;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * В данной программе производится запись строки в созданный коннектор (пайп) в отдельной нити,
 * а потом производится чтение этой строки из коннектора в другой нити.
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public class PipedUsage {
    public static void main(String[] args) throws IOException {

        final PipedInputStream in = new PipedInputStream();
        final PipedOutputStream out = new PipedOutputStream();

        Thread firstThread = new Thread(() -> {
            try {
                out.write("Job4j".getBytes());
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread secondThread = new Thread(() -> {
            try {
                int ch;
                while ((ch = in.read()) != -1) {
                    System.out.print((char) ch);
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        in.connect(out);
        firstThread.start();
        secondThread.start();
    }
}
