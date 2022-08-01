package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

/**
 * Класс для хранения счетов пользователя.
 * Доступ ко всем методам класса AccountStorage должен быть эксклюзивным,
 * то есть вам нужно использовать синхронизацию.
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
@ThreadSafe
public class AccountStorage {
    /**
     * Поле accounts - это общий ресурс для нитей,
     * поэтому можно этот объект использовать в качестве объекта монитора.
     */
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    /**
     * Добавляет account в хранилище.
     *
     * @param account, который нужно добавить.
     * @return true - если account нет в HashMap, так как вернется null.
     * false - если account в HashMap есть.
     */
    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    /**
     * Замена account в хранилище.
     *
     * @param account, который нужно заменить.
     * @return true - если key есть в HashMap.
     * false - если key нет в HashMap.
     */
    public synchronized boolean update(Account account) {
        int key = account.id();
        return accounts.replace(key, accounts.get(key), account);
    }

    public synchronized boolean delete(int id) {
        return accounts.remove(id, accounts.get(id));
    }

    public synchronized Optional<Account> getById(int id) {
        Optional<Account> rsl = Optional.empty();
        Account account = accounts.get(id);
        if (account != null) {
            rsl = Optional.of(account);
        }
        return rsl;
    }

    public boolean transfer(int fromId, int toId, int amount) {
        boolean flag = false;
        Account accountFrom = getById(fromId)
                .orElseThrow(() -> new IllegalArgumentException("Not found account by id"));
        Account accountTo = getById(toId)
                .orElseThrow(() -> new IllegalArgumentException("Not found account by id"));
        if (accountFrom.amount() >= amount) {
            update(new Account(accountFrom.id(), accountFrom.amount() - amount));
            update(new Account(accountTo.id(), accountTo.amount() + amount));
            flag = true;
        } else {
            System.out.println("Not enough funds");
        }
        return flag;
    }
}
