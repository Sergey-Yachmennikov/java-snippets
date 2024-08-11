package search;

import java.io.File;
import java.util.List;

public class FileSearch {

    public static void searchFiles(File root, List<File> fileList) {
        if (root.isDirectory()) {
            File[] directoryFiles = root.listFiles();
            if (directoryFiles != null) {
                for (File file : directoryFiles) {
                    if (file.isDirectory()) {
                        searchFiles(file, fileList);
                    } else {
                        if (file.getName().toLowerCase().endsWith(".jpg")) {
                            fileList.add(file);
                        }
                    }
                }
            }
        }
    }
}
