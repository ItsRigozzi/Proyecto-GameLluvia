package puppy.code.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import puppy.code.DungeonKnightGame;

/**
 * REQUISITO GM2.1: Aplicación del Patrón de Diseño Singleton (Creador).
 * * * * Esta clase lanzadora es la responsable de crear la ÚNICA instancia
 * * de la clase DungeonKnightGame (el Singleton) al iniciar la aplicación.
 */
public class Lwjgl3Launcher {
    public static void main(String[] args) {
        createApplication();
    }

    private static Lwjgl3Application createApplication() {
        /**
         * (GM2.1) Aquí se instancia el Singleton.
         * Se crea el objeto DungeonKnightGame una sola vez y se pasa al Lwjgl3Application
         * para que gestione el ciclo de vida del juego.
         */
        return new Lwjgl3Application(new DungeonKnightGame(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("Dungeon Knight");
        configuration.useVsync(true);
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);
        
        configuration.setResizable(false);
        configuration.setWindowedMode(800, 480);
        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
        return configuration;
    }
}