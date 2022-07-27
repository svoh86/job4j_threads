package ru.job4j;

/**
 * 1. Синхронизация общих ресурсов.
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public final class Cache {
    private static Cache cache;

    public static synchronized Cache instOf() {
        if (cache == null) {
            cache = new Cache();
        }
        return cache;
    }
}
