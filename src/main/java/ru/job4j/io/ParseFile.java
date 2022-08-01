package ru.job4j.io;

import java.io.*;

/**
 * 1. Visibility. Общий ресурс вне критической секции
 * Класс парсера файла.
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public synchronized File getFile() {
        return file;
    }
}
