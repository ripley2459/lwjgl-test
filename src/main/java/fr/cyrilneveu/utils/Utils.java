package fr.cyrilneveu.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Utils {
    public static String readFile(String file) {
        try {
            String content = Files.readString(Path.of(file));
            return content;
        } catch (IOException e) {
            throw new RuntimeException("Error reading file [" + file + "]", e);
        }
    }
}
