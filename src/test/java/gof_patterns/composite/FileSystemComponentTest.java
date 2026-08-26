package gof_patterns.composite;

import org.junit.jupiter.api.Test;


class FileSystemComponentTest {

    @Test
    void compositeTest() {
        File file1 = new File("file1.txt", 100);
        File file2 = new File("file2.txt", 200);
        File file3 = new File("file3.txt", 300);

        Directory subDir = new Directory("subdir");
        subDir.add(file2);
        subDir.add(file3);

        Directory root = new Directory("root");
        root.add(file1);
        root.add(subDir);

        // Клиентский код работает только с FileSystemComponent
        System.out.println("Structure:");
        root.print("");

        System.out.println("Total size: " + root.getSize() + " bytes");
    }

}