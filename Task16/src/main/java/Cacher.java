import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Cacher implements InvocationHandler {
    private final Object delegate;

    private DbConnection connection;

    public Cacher(Object delegate, DbConnection connection) {
        this.delegate = delegate;
        this.connection = connection;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Cache cache = method.getAnnotation(Cache.class);
        if (cache == null)
            return method.invoke(delegate, args);

        return fileCacheResult(cache, method, args);

    }

    private ArrayList<Object> generateKey(Cache cache, Method method, Object[] args) {
        ArrayList<Object> res = new ArrayList<>();

        res.add(method.getName());

        var excludedParams = new TreeSet<Integer>();
        for (int i = 0; i < cache.exclude().length; i++) {
            excludedParams.add(cache.exclude()[i]);
        }


        var params = method.getParameters();
        for (int i = 0; i < params.length; i++) {
            if (!excludedParams.contains(i)) {
                res.add(args[i]);
            }
        }

        return res;
    }


    private Object fileCacheResult(Cache cache, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException, IOException {
        var cacheKey = generateKey(cache, method, args);
        var str = connection.get(Serializator.toString(cacheKey));

        if (str != null) {
            return  Serializator.fromString(str);
        }
        var result = method.invoke(delegate, args);
        if (result instanceof List) {
            var limitedRes = limit((List<Object>) result, cache.listLimit());
            cacheResult(cacheKey, limitedRes);
        } else {
            cacheResult(cacheKey, result);

        }
        return result;
    }

    ArrayList<Object> limit(List<Object> l, int count) {
        ArrayList<Object> res = new ArrayList<Object>();
        for (int i = 0; i < l.size() && i < count; i++) {
            res.add(l.get(i));
        }
        return res;
    }


    private void cacheResult(ArrayList<Object> cacheKey, Object result){

        connection.put(Serializator.toString(cacheKey),Serializator.toString(result));
    }


}
