import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class EncryptedClassLoader extends ClassLoader {
    private final int key;
    private final File dir;

    public EncryptedClassLoader(int key, File dir, ClassLoader parent) {
        super(parent);
        this.key = key;
        this.dir = dir;
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] classData = loadClassData(name);
            return defineClass(name, classData, 0, classData.length);
        } catch (IOException e) {
            throw new ClassNotFoundException("Class " + name + " not found", e);
        }
    }

    private byte[] loadClassData(String name) throws IOException {


        byte[] encryptedData = Files.readAllBytes(dir.toPath());
        decode(encryptedData);
        return encryptedData;
    }


    private void decode(byte[] data) {
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) (data[i] - key);
        }
    }
}
