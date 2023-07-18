package ru.job4j.noi;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * В данной программе демонстрируется чтение из файла в буфер при помощи канала
 * с последующим выводом данных на консоль.
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public class NioDemo {
    public static void main(String[] args) {
        int count;
        try (SeekableByteChannel byteChannel = Files.newByteChannel(Paths.get("data/nio.txt"))) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            do {
                count = byteChannel.read(buffer);
                if (count != -1) {
                    buffer.rewind();
                    for (int i = 0; i < count; i++) {
                        System.out.print((char) buffer.get());
                    }
                }
            } while (count != -1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
