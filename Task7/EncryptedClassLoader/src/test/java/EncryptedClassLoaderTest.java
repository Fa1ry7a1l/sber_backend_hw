import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

public class EncryptedClassLoaderTest {

    /**не тест, просто показывает как шифруется файл*/
   /* @Test
    void GenerateFile() throws IOException {
        int key = 1;

        var bytes = Files.readAllBytes(Path.of(".\\src\\main\\resources\\Plugin.class"));

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (bytes[i] + key);
        }

        Files.write(Path.of(".\\src\\main\\resources\\Plugin.enc"), bytes);
    }*/

    @Test
    void givenEncryptedClassloader_whenClassLoaded_thenClassNameIsPlugin() throws ClassNotFoundException {
        //given
        EncryptedClassLoader encryptedClassLoader = new EncryptedClassLoader(1, new File(".\\src\\main\\resources\\Plugin.enc"), null);

        //when
        Class c = encryptedClassLoader.findClass("Plugin");

        //then
        Assertions.assertEquals("Plugin",c.getName());
    }
}
