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
        return accounts.replace(account.id(), account) != null;
    }

    public synchronized boolean delete(int id) {
        return accounts.remove(id) != null;
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public boolean transfer(int fromId, int toId, int amount) {
        boolean flag = false;
        Optional<Account> accountFrom = getById(fromId);
        Optional<Account> accountTo = getById(toId);
        if (accountFrom.isPresent() && accountTo.isPresent()
            && accountFrom.get().amount() >= amount) {
            update(new Account(accountFrom.get().id(), accountFrom.get().amount() - amount));
            update(new Account(accountTo.get().id(), accountTo.get().amount() + amount));
            flag = true;
        } else {
            System.out.println("Not enough funds or account isn`t exist");
        }
        return flag;
    }
}
