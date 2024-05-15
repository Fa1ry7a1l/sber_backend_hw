import java.io.*;
import java.nio.file.Files;

public class FileUtils {


    /**
     * проверяет 2 файла на равенство (побайтово)
     * */
    public static boolean compareFiles(File a, File b) {
        byte[] arrayA;
        byte[] arrayB;
        try {
            arrayA = Files.readAllBytes(a.toPath());
            arrayB = Files.readAllBytes(b.toPath());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (arrayA.length != arrayB.length)
            return false;

        for (int i = 0; i < arrayA.length; i++) {
            if (arrayA[i] != arrayB[i])
                return false;
        }
        return true;
    }
}
