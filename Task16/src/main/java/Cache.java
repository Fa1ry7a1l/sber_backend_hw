import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cache {

    /**
     * какие переменные игнорировать
     */
    int[] exclude() default {};

    /**
     * сколько элементов листа сохранять при возвращаемом значениие - лист
     * */
    int listLimit() default Integer.MAX_VALUE;


}


