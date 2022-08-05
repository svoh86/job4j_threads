package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 1. Неблокирующий кеш
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    /**
     * if (memory.containsKey(model.getId())) {
     * return false;
     * }
     * memory.put(model.getId(), model);
     * return true;
     * Эта реализация не потокобезопасная. Ее использовать нельзя.
     * Хоть сама ConccurentHashMap потокобезопасная,
     * но последовательные вызовы методов нет. Это не атомарные операции.
     * Для решения этих задач нужно использовать CAS методы.
     * Метод putIfAbsent выполняет методы сравнения и вставки, только делает это атомарно.
     *
     * @param model модель данных
     * @return boolean
     */
    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    /**
     * В кеше должна быть возможность проверять актуальность данных.
     * Для этого в модели данных используется поле int version.
     * Это поле должно увеличиваться на единицу каждый раз,
     * когда модель изменили, то есть вызвали метод update.
     * Даже если все поля остались не измененными поле version должно увеличиться на 1.
     * ConcurentHashMap имеет метод computeIfPresent.
     * Этот метод принимает функцию BiFunction и позволяет атомарно обновить нужную ячейку.
     * В нашем случае, если version отличаются нужно выкинуть исключение.
     *
     * @param model модель данных
     * @return boolean
     * @see Main
     */
    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (k, v) -> {
            if (v.getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            return new Base(model.getId(), model.getVersion() + 1);
        }) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }
}
