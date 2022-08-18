package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 2. ExecutorService рассылка почты.
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public class EmailNotification {
    private final ExecutorService pool;

    public EmailNotification() {
        this.pool = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );
    }

    /**
     * Метод должен через ExecutorService отправлять почту.
     * Через ExecutorService создайте задачу,
     * которая будет создавать данные для пользователя и передавать их в метод send.
     *
     * @param user user
     */
    public void emailTo(User user) {
        pool.submit(() -> {
                    String subject = String.format("Notification %s to email %s", user.username(), user.email());
                    String body = String.format("Add a new event to %s", user.username());
                    send(subject, body, user.email());
                }
        );
    }

    /**
     * Метод закрывает pool
     */
    public void close() {
        pool.shutdown();
        while (!this.pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Метод должен быть пустой.
     *
     * @param subject subject
     * @param body    body
     * @param email   email
     */
    public void send(String subject, String body, String email) {
    }
}
