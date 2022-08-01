package ru.job4j.cash;

/**
 * Класс Account имеет два поля:
 * ID - уникальный идентификатор, amount - баланс пользователя.
 */
public record Account(int id, int amount) {
}
