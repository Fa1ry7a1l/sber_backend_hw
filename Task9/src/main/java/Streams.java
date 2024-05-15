import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class Streams<T> {

    LinkedList<T> list;

    private Streams(Collection<? extends T> collection) {
        this.list = new LinkedList<>(collection);
    }


    public static <T> Streams<T> of(Collection<? extends T> collection) {
        return new Streams<T>(collection);
    }

    public Streams<T> filter(Predicate<? super T> predicate) {
        LinkedList<T> futureResult = new LinkedList<>();
        for (var element : list) {
            if (predicate.test(element)) {
                futureResult.addLast(element);
            }
        }
        return new Streams<>(futureResult);
    }

    public <NewT> Streams<NewT> transform(Function<? super T, ? extends NewT> transformFunction) {
        LinkedList<NewT> futureResult = new LinkedList<>();
        for (var element : list) {
            futureResult.addLast(transformFunction.apply(element));
        }
        return new Streams<>(futureResult);

    }

    public <KeyT, ValT> Map<KeyT, ValT> toMap(Function<? super T, ? extends KeyT> keyFunction, Function<? super T, ? extends ValT> valFunction) {
        Map<KeyT, ValT> resultMap = new TreeMap<>();

        for (var element : list) {
            resultMap.put(keyFunction.apply(element), valFunction.apply(element));
        }

        return resultMap;
    }

    /**
     * просто чтобы было как тестировать
     * */
    public List<T> toList()
    {
        return new ArrayList<>(list);
    }



}

