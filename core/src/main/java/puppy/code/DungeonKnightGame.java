package puppy.code;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * REQUISITO GM2.1: Aplicación del Patrón de Diseño Singleton.
 * * Esta clase principal (DungeonKnightGame) actúa como la implementación del Patrón Singleton.
 * Se instancia una única vez en el Lwjgl3Launcher y se pasa su referencia (this)
 * a todas las pantallas (Screen).
 * * Rol: Gestiona y centraliza el acceso a los recursos globales (SpriteBatch, BitmapFont)
 * y al estado global (highScores), asegurando que solo exista una instancia de estos.
 */
public class DungeonKnightGame extends Game {

    private SpriteBatch batch;
    private BitmapFont font;
    
	private Preferences prefs;
    public int[] highScores; // Array público para guardar el Top 3

	@Override
	public void create() {
        /**
         * (GM2.1) El Singleton inicializa los recursos globales que
         * serán compartidos por todas las pantallas (Clientes).
         */
		batch = new SpriteBatch();
		font = new BitmapFont(); 
        
        // Carga el archivo de guardado
        prefs = Gdx.app.getPreferences("dungeonknight_settings");
        
        // Inicializa el array de puntajes y carga los valores guardados
        highScores = new int[3];
        highScores[0] = prefs.getInteger("score1", 0); // Top 1
        highScores[1] = prefs.getInteger("score2", 0); // Top 2
        highScores[2] = prefs.getInteger("score3", 0); // Top 3
        
        /**
         * (GM2.1) El Singleton inyecta su propia instancia (this) en la 
         * pantalla de Menú, permitiendo el acceso global a sus recursos.
         */
		this.setScreen(new MainMenuScreen(this));
	}

    /**
     * Revisa si un nuevo puntaje entra en el Top 3 y lo guarda.
     * @param newScore El puntaje final del jugador.
     */
	public void checkAndSaveScore(int newScore) {
        // No guardamos puntajes de 0
        if (newScore <= 0) {
            return;
        }
        
        // Guardamos los puntajes actuales antes de moverlos
        int oldScore1 = highScores[0];
        int oldScore2 = highScores[1];

        // 1. Revisa si es mejor que el #1
        if (newScore > oldScore1) {
            highScores[0] = newScore;  // Nuevo #1
            highScores[1] = oldScore1; // Antiguo #1 baja a #2
            highScores[2] = oldScore2; // Antiguo #2 baja a #3
        } 
        // 2. Si no es #1, revisa si es mejor que el #2
        else if (newScore > oldScore2) {
            highScores[1] = newScore;  // Nuevo #2
            highScores[2] = oldScore2; // Antiguo #2 baja a #3
        } 
        // 3. Si no es #1 ni #2, revisa si es mejor que el #3
        else if (newScore > highScores[2]) {
            highScores[2] = newScore;  // Nuevo #3
        }
        

        // 4. Guardar los nuevos puntajes en el disco
        prefs.putInteger("score1", highScores[0]);
        prefs.putInteger("score2", highScores[1]);
        prefs.putInteger("score3", highScores[2]);
        prefs.flush(); // Aplica el guardado
    }

    /**
     * Devuelve la lista de los 3 mejores puntajes.
     * @return un array de int[3]
     */
    public int[] getHighScores() {
        return this.highScores;
    }
    
    /**
     * Devuelve solo el mejor puntaje (Top 1) para el HUD.
     */
    public int getTopScore() {
        return this.highScores[0];
    }

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
	}

    /**
     * (GM2.1) Getter público para que los Clientes (Pantallas)
     * accedan al recurso global SpriteBatch.
     */
	public SpriteBatch getBatch() {
		return batch;
	}

    /**
     * (GM2.1) Getter público para que los Clientes (Pantallas)
     * accedan al recurso global BitmapFont.
     */
	public BitmapFont getFont() {
		return font;
	}
    
}