package ru.job4j;

/**
 * 2. Модель памяти Java
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public final class DCLSingleton {
    private static volatile DCLSingleton inst;

    private DCLSingleton() {
    }

    public static DCLSingleton instOf() {
        if (inst == null) {
            synchronized (DCLSingleton.class) {
                if (inst == null) {
                    inst = new DCLSingleton();
                }
            }
        }
        return inst;
    }
}
