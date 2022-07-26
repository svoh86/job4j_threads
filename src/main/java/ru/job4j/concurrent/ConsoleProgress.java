package ru.job4j.concurrent;

/**
 * 3. Прерывание нити.
 * Этот класс будет использован для вывода процесса загрузки в консоль.
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        String[] process = {"|", "/", "-", "\\"};
        int i = 0;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                System.out.print("\rLoading ... " + process[i % process.length]);
                i++;
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(1000);
        progress.interrupt();
    }
}
