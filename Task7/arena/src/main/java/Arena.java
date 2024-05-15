import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class Arena {
    public static void main(String[] args) {
        var jars = loadJars();
        Class[] pluginClasses = getAllPlayers(jars);
        int currentChampionIndex = 0;

        if (jars.length > 0) {
            try {
                RockPaperScissorsPlayable currentChampion = (RockPaperScissorsPlayable) pluginClasses[0].newInstance();

                for (int i = 1; i < jars.length; i++) {
                    RockPaperScissorsPlayable secondChampion = (RockPaperScissorsPlayable) pluginClasses[i].newInstance();
                    System.out.printf("Бой %s и %s \n", jars[currentChampionIndex].getName(),jars[i].getName());
                    boolean isFirsWin = fight(currentChampion,secondChampion);
                    if(isFirsWin)
                    {
                        System.out.printf("%s покидает арену\n",jars[i].getName());
                    }
                    else
                    {
                        System.out.printf("%s покидает арену\n",jars[currentChampionIndex].getName());
                        currentChampion = secondChampion;
                        currentChampionIndex = i;
                    }

                    System.out.println();
                    System.out.println();

                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }

            System.out.println();
            System.out.printf("%s победил \n", jars[currentChampionIndex].getName());
        } else {
            System.out.println("Нет jar файлов - нет и соревнования");
        }
    }


    /**
     * проводит строго 3 битвы
     *
     * @return true, если первый победил
     */
    private static boolean fight(RockPaperScissorsPlayable firstChampion, RockPaperScissorsPlayable secondChampion) {
        //счетчики для первого и второго чемпиона
        int f = 0, s = 0;
        for (int i = 0; i < 3; i++) {
            if (singleFight(firstChampion, secondChampion)) {
                f++;
            } else {
                s++;
            }
        }
        boolean res = f - s > 0;
        System.out.printf("Со счетом %d : %d победил %s игрок\n", f, s, res ? "первый" : "второй");
        return res;
    }

    /**
     * вроводит битву пока не победит кто то из игроков
     *
     * @return true, если победил первый игрок
     */
    private static boolean singleFight(RockPaperScissorsPlayable firstChampion, RockPaperScissorsPlayable secondChampion) {
        RockPaperScissorsEnum resF;
        RockPaperScissorsEnum resS;
        do {
            resF = firstChampion.play();
            resS = secondChampion.play();
            if (resF == resS) {
                System.out.println("Оба игрока выкинулу " + resS + " потовряем");
            }
        } while (resF == resS);
        boolean res = RockPaperScissorsEnum.isFirstWin(resF, resS);
        System.out.printf("игроки выкинули %s и %s;  %s игрок победил \n", resF, resS, res ? "первый" : "второй");
        return res;
    }


    private static Class[] getAllPlayers(File[] jars) {
        Class[] pluginClasses = new Class[jars.length];

        for (int i = 0; i < jars.length; i++) {
            try {
                URL jarURL = jars[i].toURI().toURL();
                URLClassLoader classLoader = new URLClassLoader(new URL[]{jarURL});
                pluginClasses[i] = classLoader.loadClass("RockPaperScissorsPlayer");
            } catch (MalformedURLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return pluginClasses;
    }

    public static File[] loadJars() {

        File pluginDir = new File(".\\arena\\src\\main\\resources");
        System.out.println(pluginDir.exists());

        return pluginDir.listFiles(file -> file.isFile() && file.getName().endsWith(".jar"));

    }
}
