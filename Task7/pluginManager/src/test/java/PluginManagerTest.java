import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PluginManagerTest {

    @Test
    void givenMyPluginManager_whenLoadedPluginsWithSameNames_thenBothPluginsPersist()
    {
        //given
        PluginManager<Plugin> pluginManager = new PluginManager<>(".\\src\\main\\resources\\pluginFolder");

        //when
        Plugin p1 = pluginManager.load("plugin1","PluginImpl");
        Plugin p2 = pluginManager.load("plugin2","PluginImpl");

        //then
        Assertions.assertNotNull(p1);
        Assertions.assertNotNull(p2);
    }

}
