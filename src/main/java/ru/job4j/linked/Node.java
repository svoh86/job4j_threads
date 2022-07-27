package ru.job4j.linked;

/**
 * 3. Immutable объекты
 * Правила создания Immutable объекта:
 * 1. Все поля отмечены final.
 * 2. Состояние объекта не изменяется после создания объекта (убрать Сеттеры и т.д.)
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public class Node<T> {
    private final Node<T> next;
    private final T value;

    public Node(Node<T> next, T value) {
        this.next = next;
        this.value = value;
    }

    public Node<T> getNext() {
        return next;
    }

    public T getValue() {
        return value;
    }
}
