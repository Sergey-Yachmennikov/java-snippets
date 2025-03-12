package core.io;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class IOSnippetsTest {

    private final String FILE_NAME_NIO = "src/test/java/core/io/nio-file.txt";
    private final String FILE_NAME_IO = "src/test/java/core/io/io-file.txt";
    private final String TEXT = "src/test/java/core/io/text.txt";

    @Test
    void createFileNIO() throws IOException {
        Path newFilePath = Paths.get(FILE_NAME_NIO);
        Files.createFile(newFilePath);
    }

    @Test
    void createFileIO() throws IOException {
        File newFile = new File(FILE_NAME_IO);
        boolean success = newFile.createNewFile();
        assertTrue(success);
    }

    @Test
    void readExistingFile() {
        try(FileInputStream fin = new FileInputStream(TEXT)){
            int i;
            while((i = fin.read()) != -1){
                System.out.println((char) i);
            }
        } catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}