package execution_data_structures;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class FileSystem {
    private final String sandboxRoot;

    public FileSystem(String sandboxRoot) {
        this.sandboxRoot = sandboxRoot;
    }

    private static boolean isValid(String filename) {
        return !(filename.contains(".") | filename.contains("/")); /* prevents sandbox breaches */
    }

    public String read(String filename) throws IOException {
        if (!isValid(filename)) {
            throw new IOException(); /* TODO: make an exception logger and route to it instead */
        }
        List<String> lines = Files.readAllLines(Paths.get(sandboxRoot, filename));
        return String.join("\n", lines);
    }

    public void write(String filename, String content) throws IOException {
        if (!isValid(filename)) {
            throw new IOException();
        }
        Files.write(Paths.get(sandboxRoot, filename), content.getBytes());
    }

    public boolean exists(String filename) throws IOException {
        if (!isValid(filename)) {
            throw new IOException();
        }
        return new File(sandboxRoot, filename).isFile();
    }
}
