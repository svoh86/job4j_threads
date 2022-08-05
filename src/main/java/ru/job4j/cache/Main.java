package ru.job4j.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * 1. Неблокирующий кеш
 * Например. Два пользователя прочитали объект task ID = 1.
 * Первый пользователь изменил поле имя и второй сделал то же самое.
 * Теперь пользователи сохраняют изменения. Для этого они вызывают метод update.
 * В этом случае возникает ситуация, которая называется "последний выиграл".
 * То есть в кеше сохраняться данные только последнего пользователя.
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {
        Map<Integer, Base> map = new HashMap<>();
        Base base = new Base(1, 0);
        map.put(base.getId(), base);
        Base user1 = map.get(1);
        user1.setName("User 1");
        Base user2 = map.get(1);
        user1.setName("User 2");
        map.put(user1.getId(), user1);
        map.put(user2.getId(), user2);
        System.out.println(map);
    }
}
