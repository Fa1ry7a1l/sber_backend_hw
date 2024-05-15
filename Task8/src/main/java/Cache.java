import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cache {


    /**
     * Определяет где будут храниться кэшированнные данные
     * поумолчанию в памяти
     */
    SavingType savingType() default SavingType.MEMORY;

    /**
     * ключ для хранения
     */
    String cacheName() default "";

    /**
     * сжимать ли файл
     * только для savingType = SavingType.FILE
     */
    boolean zip() default false;


    /**
     * какие переменные игнорировать
     */
    int[] exclude() default {};

    /**
     * сколько элементов листа сохранять при возвращаемом значениие - лист
     * */
    int listLimit() default Integer.MAX_VALUE;


}


