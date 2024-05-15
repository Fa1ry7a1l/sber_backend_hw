package task1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Task2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean hasContent = false;
        while (!hasContent)
        {
            try{
                System.out.println("Введите ссылку");
                String link = scanner.nextLine();
                String content = readContent(link);
                System.out.println("Получен контент\n\n");
                System.out.println(content);
                hasContent = true;
            } catch (MalformedURLException e) {
                System.err.println("Некорректный адрес, попробуйте еще раз");
            } catch (IOException e) {
                System.err.println("Неудается получить доступ к сайту. Попробуйте другой URL или обратитесь к сайту позднее.");
            }


        }
    }

    public static String  readContent(String link) throws MalformedURLException, IOException {
        URL url = new URL(link);
        String inputLine;
        StringBuilder sb = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            while ((inputLine = br.readLine()) != null)
                sb.append(inputLine);
        }

        return sb.toString();
    }
}
