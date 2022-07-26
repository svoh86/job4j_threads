package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

/**
 * 1. Visibility. Общий ресурс вне критической секции
 * Класс парсера файла.
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public final class ParseFileSaveContent {
    public void saveContent(String content, ParseFile file) throws IOException {
        try (OutputStream o = new BufferedOutputStream(new FileOutputStream(file.getFile()))) {
            for (int i = 0; i < content.length(); i++) {
                o.write(content.charAt(i));
            }
        }
    }
}
