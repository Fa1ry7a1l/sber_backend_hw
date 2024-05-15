import java.io.*;
import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {
        Test1();
        Test2();
        Test3();
    }


    /**
     * Тестирует что время работы с кэшем меньше, чем без него
     */
    public static void Test1() {
        System.out.println("Тест 1 =============================================\n" +
                "демонстрация что время работы кэшированных данных меньше, чем оригинальных");
        TestClassInterface testClass = new TestClass();
        testClass = (TestClassInterface) Proxy.newProxyInstance(testClass.getClass().getClassLoader(),
                testClass.getClass().getInterfaces(), new Cacher(testClass, "."));

        var start = System.nanoTime();
        var result1 = testClass.defaultFactorial(500);
        var end = System.nanoTime();
        System.out.println("длительность факториала(500) 1й раз - " + (end - start));

        System.out.println(result1);
        start = System.nanoTime();
        var result = testClass.defaultFactorial(10);
        end = System.nanoTime();
        System.out.println("Факториал 10, чтобы показать что мы запоминаем значения - " + (end - start));

        System.out.println(result);


        start = System.nanoTime();
        result1 = testClass.defaultFactorial(500);
        end = System.nanoTime();
        System.out.println("длительность факториала(500) 2й раз - " + (end - start));

        System.out.println(result1);
    }

    public static void Test2() {
        System.out.println("Тест 2 =============================================\n" +
                "демонстрация игнорируемых параметров. Для определения уникальности запуска используется только первая переменная");

        TestClassInterface testClass = new TestClass();
        testClass = (TestClassInterface) Proxy.newProxyInstance(testClass.getClass().getClassLoader(),
                testClass.getClass().getInterfaces(), new Cacher(testClass, "."));

        var start = System.nanoTime();
        var result1 = testClass.manyParametrsSum(1, 2, 3, 4);
        var end = System.nanoTime();
        System.out.println("сумма чисел первый раз 1й раз - " + result1 + " за " + (end - start) + " наносекунд");
        start = System.nanoTime();
        var result = testClass.manyParametrsSum(1, -500, -500, -500);
        end = System.nanoTime();
        System.out.println("сумма чисел первый раз 2й раз - " + result + ", хотя должно было бы быть -1499, за " + (end - start) + " наносекунд");
        start = System.nanoTime();
        var result3 = testClass.manyParametrsSum(2, 1, 3, 4);
        end = System.nanoTime();
        System.out.println("сумма чисел первый раз 1й раз - " + result3 + ", как и должно быть, просто поменял ключевой параметр на другой " + (end - start) + " наносекунд");


    }

    public static void Test3() {
        System.out.println("Тест 3 =============================================\n" +
                "демонстрация обрезания List, сохранение идет в zip архив");

        TestClassInterface testClass = new TestClass();
        testClass = (TestClassInterface) Proxy.newProxyInstance(testClass.getClass().getClassLoader(),
                testClass.getClass().getInterfaces(), new Cacher(testClass, "."));

        var start = System.nanoTime();
        var result1 = testClass.ListResultWithZip(1000);
        var end = System.nanoTime();
        System.out.println("длина списка первый раз 1й раз - " + result1.size() + " за " + (end - start) + " наносекунд");


        start = System.nanoTime();
        var result2 = testClass.ListResultWithZip(1000);
        end = System.nanoTime();
        System.out.println("длина списка первый раз 2й раз - " + result2.size() + " хотя должно было быть 1000 за " + (end - start) + " наносекунд");

    }

}
