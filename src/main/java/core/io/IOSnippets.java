package core.io;

import java.io.File;
import java.io.IOException;

public class IOSnippets {

    public static void createFile() throws IOException {
        File.createTempFile("file", "txt");
    }
}
