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
public final class ParseFileContent {
    public String getContent(ParseFile file) throws IOException {
        return checkContent(file, character -> character > 0);
    }

    public String getContentWithoutUnicode(ParseFile file) throws IOException {
        return checkContent(file, character -> character > 0x80);
    }

    private static String checkContent(ParseFile file, Predicate<Character> filter) throws IOException {
        try (InputStream i = new BufferedInputStream(new FileInputStream(file.getFile()))) {
            StringBuilder output = new StringBuilder();
            int data;
            while ((data = i.read()) != -1) {
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }
            return output.toString();
        }
    }
}
