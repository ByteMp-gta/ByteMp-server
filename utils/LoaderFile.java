package utils;

import java.nio.file.Files;
import java.nio.file.Path;

public class LoaderFile {

    public static int loadIntPort(String file) {

        Path filePath = Path.of(file);

        if (Files.isDirectory(filePath)) {
            throw new IllegalArgumentException("The provided path is a directory, not a file.");
        }

        if (!Files.exists(filePath)) {
           RunLuaServer.run();
        }

        try {

            String content = Files.readString(filePath).trim();
            return Integer.parseInt(content);

        } catch (Exception e) {

            throw new RuntimeException("Failed to read or parse the port number from the file.", e);
            
        }
    }

}
