package remote_preparation.io;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileCopier {

    private static final int BUFFER_SIZE = 8192;

    public static void copyManual(String source, String target) throws IOException {
        try (
             FileReader reader = new FileReader(source);
             FileWriter writer = new FileWriter(target)
        ) {

            int symbol;
            while ((symbol = reader.read()) != -1) {
                writer.write(symbol);
            }
        }
    }

    public static void bufferedCopy(String source, String target) throws IOException {
        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(new FileInputStream(source), StandardCharsets.UTF_8));
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(new FileOutputStream(target), StandardCharsets.UTF_8))
        ) {
            char[] buffer = new char[BUFFER_SIZE];
            int charsRead;
            while ((charsRead = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, charsRead);
            }

        }
    }

    public static void copy(Path source, Path target) throws IOException {
        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
    }

    // подсчет строк в файле
    public long countLines(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8))) {

            long count = 0;
            while (reader.readLine() != null) {
                count++;
            }
            return count;
        }
    }
}
