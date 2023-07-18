package ru.job4j;

/**
 * @author Svistunov Mikhail
 * @version 1.0
 */
public class SingletonHolder {

    private SingletonHolder() {
    }

    public static SingletonHolder getInstance() {
        return Holder.INSTANCE;
    }

    private static final class Holder {
        private static final SingletonHolder INSTANCE = new SingletonHolder();
    }
}
