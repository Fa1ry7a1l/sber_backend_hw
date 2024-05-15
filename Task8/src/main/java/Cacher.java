import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Cacher implements InvocationHandler {

    private final HashMap<ArrayList<Object>, Object> cachedData = new HashMap<>();
    private final Object delegate;

    private final Set<String> cachedFiles = new TreeSet<>();

    private final String basePath;

    public Cacher(Object delegate, String basePath) {
        this.delegate = delegate;
        this.basePath = basePath;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Cache cache = method.getAnnotation(Cache.class);
        if (cache == null)
            return method.invoke(delegate, args);


        return switch (cache.savingType()) {
            case SavingType.MEMORY -> memoryCacheResult(cache, method, args);
            case SavingType.FILE -> fileCacheResult(cache, method, args);
        };

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

    private String generateFileName(Cache cache, Method method) {
        String res = cache.cacheName().isEmpty() ? method.getName() : cache.cacheName();
        return res + (cache.zip() ? ".zip" : ".bin");
    }


    private Object fileCacheResult(Cache cache, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException, IOException {
        var cacheKey = generateKey(cache, method, args);
        var fileName = generateFileName(cache, method);
        if (cachedFiles.contains(fileName)) {
            Object result = tryFindCachedResultInFile(cacheKey, basePath + "\\" + fileName);
            if (result != null)
                return result;
        }
        var result = method.invoke(delegate, args);
        if (result instanceof List) {
            var limitedRes = limit((List<Object>) result,cache.listLimit());
            cacheResult(cacheKey, basePath + "\\" + fileName, limitedRes);
        } else {
            cacheResult(cacheKey, basePath + "\\" + fileName, result);

        }
        cachedFiles.add(fileName);
        return result;
    }

    ArrayList<Object> limit(List<Object> l, int count)
    {
        ArrayList<Object> res = new ArrayList<Object>();
        for(int i = 0;i<l.size() && i < count;i++)
        {
            res.add(l.get(i));
        }
        return res;
    }


    private void cacheResult(ArrayList<Object> cacheKey, String filePath, Object result) throws IOException {
        CacheDto cacheDto = new CacheDto(cacheKey, result);
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Не удается создать файл " + filePath, e);
            }
        }
        if (filePath.endsWith(".zip")) {
            writeZipFile(file, cacheDto);
        } else {
            writeUsualFile(file, cacheDto);
        }
    }

    private void writeZipFile(File file, CacheDto cacheDto) {
        try (ZipOutputStream zipStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            zipStream.putNextEntry(new ZipEntry("bef"));
            ObjectOutputStream objectStream = new ObjectOutputStream(zipStream);
            objectStream.writeObject(cacheDto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeUsualFile(File file, CacheDto cacheDto) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(cacheDto);
        } catch (NotSerializableException e) {
            throw new IOException("Ошибка сериализации: объект не сериализуемый", e);
        } catch (IOException e) {
            throw new IOException("Ошибка ввода/вывода при сериализации", e);
        }
    }

    private Object tryFindCachedResultInFile(ArrayList<Object> cacheKey, String filePath) {
        File file = new File(filePath);
        boolean exists = file.exists();
        if (!exists) {
            return null;
        }
        if (filePath.endsWith(".zip")) {
            return tryFindInZipFile(cacheKey, file);
        }
        return tryFindInUsualFile(cacheKey, file);
    }

    private Object tryFindInUsualFile(ArrayList<Object> cacheKey, File file) {
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream inputStream = new ObjectInputStream(fis)) {
            while (fis.available() > 0) {
                CacheDto cacheDto = (CacheDto) inputStream.readObject();
                if (cacheDto.getKey().equals(cacheKey)) {
                    return cacheDto.getValue();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Не удалось найти класс ", e);
        }
        return null;
    }

    private Object tryFindInZipFile(ArrayList<Object> cacheKey, File file) {
        try (FileInputStream fis = new FileInputStream(file);
             ZipInputStream zis = new ZipInputStream(fis);
        ) {
            while (zis.getNextEntry() != null) {//это чудище при закрытии потока закрывало основной поток, что ломало логику программы
                ObjectInputStream ois = new ObjectInputStream(zis);
                CacheDto cacheDto = (CacheDto) ois.readObject();
                if (cacheDto.getKey().equals(cacheKey)) {
                    return cacheDto.getValue();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Не удалось найти класс ", e);
        }
        return null;
    }


    private Object memoryCacheResult(Cache cache, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        var cacheKey = generateKey(cache, method, args);
        if (cachedData.containsKey(cacheKey)) {
            return cachedData.get(cacheKey);
        } else {
            var result = method.invoke(delegate, args);
            if (result instanceof List) {
                var limitedRes = limit((List<Object>) result,cache.listLimit());

                cachedData.put(cacheKey, limitedRes);

            } else {
                cachedData.put(cacheKey, result);


            }
            return result;
        }

    }

}
