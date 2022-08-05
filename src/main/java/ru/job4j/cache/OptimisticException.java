package ru.job4j.cache;

/**
 * 1. Неблокирующий кеш
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public class OptimisticException extends RuntimeException {
    public OptimisticException(String message) {
        super(message);
    }
}
