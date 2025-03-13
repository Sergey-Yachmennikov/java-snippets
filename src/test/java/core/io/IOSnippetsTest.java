package core.io;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class IOSnippetsTest {

    private final String FILE_NAME_NIO = "src/test/java/core/io/nio-file.txt";
    private final String FILE_NAME_IO = "src/test/java/core/io/io-file.txt";
    private final String TEXT = "src/test/java/core/io/text.txt";
    private final String FOLDER = "src/test/java/core/io/folder";
    private final String DIR = "src/test/java/core/io/dir";
    private final String BASE_URL = "src/test/java/core/io";

    @Test
    void createFileNIO() throws IOException {
        Path newFilePath = Paths.get(FILE_NAME_NIO);
        Files.createFile(newFilePath);
        assertTrue(true);
    }

    @Test
    void createFileIO() throws IOException {
        File newFile = new File(FILE_NAME_IO);
        boolean success = newFile.createNewFile();
        assertTrue(success);
    }

    @Test
    void readFromExistingFile() {
        try(FileInputStream fin = new FileInputStream(TEXT)){
            int i;
            while((i = fin.read()) != -1) {
                System.out.println((char) i);
            }
        } catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
        assertTrue(true);
    }

    @Test
    void readFromByteArray() {
        byte[] byteArray = new byte[] {127,2,3,4,5,6,7,8,9,0};
        try(ByteArrayInputStream fin = new ByteArrayInputStream(byteArray)) {
            int i;
            while((i = fin.read()) != -1){
                System.out.println(i);
            }
        } catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
        assertTrue(true);
    }

    @Test
    void showFolderFiles() {
        File folder = new File(FOLDER);
        File[] files = folder.listFiles();
        System.out.println(Arrays.toString(files));
        assertTrue(true);
    }

    @Test
    void mkdir() throws IOException {
        File folder = new File(DIR);
        folder.mkdir();

        for (int i = 0; i < 20; i++) {
            File file = new File(DIR, "test_" + (i + 1) + ".txt");
            file.createNewFile();
        }

        for (File f : folder.listFiles()) {
            System.out.println(f.getName());
        }
        assertTrue(true);
    }

    @Test
    void fileWriter() {
        try (FileWriter fileWriter = new FileWriter(BASE_URL + "/file-write-test.txt", true)) {
            String text = "Hello, world!";
            fileWriter.write(text);
            fileWriter.append("\n");
            fileWriter.append('E');
            fileWriter.append("\n");
            fileWriter.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(true);
    }

    @Test
    void fileReader() {
        try (FileReader fileReader = new FileReader(BASE_URL + "/file-write-test.txt")) {
            int c = 0;
            while ((c = fileReader.read()) != -1) {
                System.out.println((char) c);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(true);
    }

    @Test
    void fileOutputStream() {
        String text = "Hello, world!";
        try (FileOutputStream fileOutputStream = new FileOutputStream(BASE_URL + "/file-output-stream.txt")) {
           byte[] buffer = text.getBytes();
           fileOutputStream.write(buffer, 0 , buffer.length);
            System.out.println("File has been written");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(true);
    }

    @Test
    void fileInputStream() {
        try (FileInputStream fileInputStream = new FileInputStream(BASE_URL + "/file-output-stream.txt")) {

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(true);
    }

    @Test
    void copyContentFromFileToAnotherOne() {
        try (
             FileInputStream fis = new FileInputStream(BASE_URL + "/notes.txt");
             FileOutputStream fos = new FileOutputStream(BASE_URL + "/new_notes.txt")
        ) {
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer, 0, buffer.length);
            fos.write(buffer, 0, buffer.length);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(true);
    }

    @Test
    void scannerTest1() {
        Scanner scanner = new Scanner("Dick1\n" + "Dick2\n" + "Dick3\n" );
        while (scanner.hasNext()) {
            String s = scanner.nextLine();
            System.out.println(s);
        }

        assertTrue(true);
    }

    @Test
    void objectSerialization() {
        User user = new User();
        user.setName("Test");

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BASE_URL + "/object.txt"))) {
            oos.writeObject(user);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        assertTrue(true);
    }

    @Test
    void objectDeserialization() {
        try (ObjectInputStream oos = new ObjectInputStream(new FileInputStream(BASE_URL + "/object.txt"))) {
            User user = (User) oos.readObject();
            System.out.println(user);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        assertTrue(true);
    }
}