package ru.job4j.ref;

import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 4. Thread без общих ресурсов.
 * Как избавиться от общих ресурсов?
 * Можно сделать копию общего ресурса.
 * В этом случае каждая нить работает с локальной копией.
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
@ThreadSafe
public class UserCache {
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    public void add(User user) {
        users.put(id.incrementAndGet(), User.of(user.getName()));
    }

    public User findById(int id) {
        return User.of(users.get(id).getName());
    }

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        for (User user : users.values()) {
            list.add(User.of(user.getName()));
        }
        return list;
    }
}
