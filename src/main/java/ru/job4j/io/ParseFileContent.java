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

    public void saveContent(String content, ParseFile file) throws IOException {
        try (OutputStream o = new BufferedOutputStream(new FileOutputStream(file.getFile()))) {
            for (int i = 0; i < content.length(); i++) {
                o.write(content.charAt(i));
            }
        }
    }

    private static String checkContent(ParseFile file, Predicate<Character> filter) throws IOException {
        try (InputStream i = new BufferedInputStream(new FileInputStream(file.getFile()))) {
            String output = "";
            int data;
            while ((data = i.read()) != -1) {
                if (filter.test((char) data)) {
                    output += (char) data;
                }
            }
            return output;
        }
    }
}
