import java.io.*;

public class Main {
    public static void main(String[] args) {
        String path = "numbers.txt";

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(path), "utf-8"))) {
            for (int i = 0; i < 2000; i++) {
                writer.write(nextRandom(1, 50) + "\n");

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    static int nextRandom(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

}
