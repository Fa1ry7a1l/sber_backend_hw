import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;

public class PluginManager<T> {
    private final String pluginRootDirectory;

    public PluginManager(String pluginRootDirectory) {
        File f = new File(pluginRootDirectory);
        if (f.isFile())
            throw new IllegalArgumentException("Передали путь на файл вместо пути на папку с плагинами");
        this.pluginRootDirectory = f.getPath();
    }

    public T load(String pluginName, String pluginClassName) {
        File file = new File(pluginRootDirectory + "/" + pluginName + "/");
        var files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                try {

                    URLClassLoader classLoader = new URLClassLoader(new URL[]{f.toURI().toURL()});
                    Class clazz = classLoader.loadClass(pluginClassName);
                    return (T) clazz.newInstance();

                } catch (MalformedURLException e) {
                    /*если тут летит ошибка, то я накосячил где то при формировании url для получения класса*/
                    /*оно лететь не должно, но catch тут нужен чтобы не выносить в сигнатуру*/
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    /*ну не повезло, не тот файл, ищем дальше*/
                    /*не приудмал ничего лучше, потому что класс может лежать в файле с названием отлмчным  от имени класса*/
                    System.out.println("Не нашел");
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException("Ошибка при загрузке класса", e);
                }
            }


        }
        return null;
    }
}
