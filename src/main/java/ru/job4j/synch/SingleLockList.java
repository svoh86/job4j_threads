package ru.job4j.synch;


import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 4. ThreadSafe динамический список. Нужно создать коллекцию,
 * которая будет корректно работать в многопоточной среде.
 * Чтобы обеспечивать потокобезопасность, нужно каждый метод сделать synchonized.
 * Здесь используется шаблон декоратор. Мы добавляем новое поведение для любой коллекции java.util.List.
 * В java уже есть аналогичный метод Collections.synchronizedList().
 * Этот метод делает обертку над коллекциями типа List.
 * Важно. Объект - это ссылка, поэтому нам нужно работать с копией данных.
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {
    @GuardedBy("this")
    private final List<T> list;

    /**
     * В конструкторе будет копия списка
     *
     * @param list список
     */
    public SingleLockList(List<T> list) {
        this.list = copy(list);
    }

    public synchronized void add(T value) {
        list.add(value);
    }

    public synchronized T get(int index) {
        return list.get(index);
    }

    /**
     * Получаем копию листа
     *
     * @param list лист
     * @return копия листа
     */
    private List<T> copy(List<T> list) {
        return new ArrayList<>(list);
    }

    /**
     * Самый простой и надежный способ сделать копию данных -
     * это использовать snapshots (копирование всех элементов).
     * И уже с него сделать итератор.
     * Этот итератор будет работать в режиме fail-safe -
     * все изменения после получения коллекции не будут отображаться в итераторе.
     * fail-fast - другой режим. При изменении данных во время итерации,
     * коллекция выкинет исключение ConcurrentModificationException.
     *
     * @return итератор с копии листа
     */
    @Override
    public synchronized Iterator<T> iterator() {
        return copy(this.list).iterator();
    }
}
