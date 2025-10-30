package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
	final DungeonKnightGame game;
        private OrthographicCamera camera;
	private SpriteBatch batch;	   
	private BitmapFont font;
	private Heroe heroe;
	private ControladorProyectiles controladorProyectiles;
        private Texture backgroundTexture; // <-- Linea para el fondo

	   
	//boolean activo = true;

            public GameScreen(final DungeonKnightGame game) {
                    this.game = game;
                    this.batch = game.getBatch();
                    this.font = game.getFont();

                    // Carga los assets usando los NUEVOS nombres de tu assets.txt

                    // 1. Carga los sonidos
                    Sound sonidoHerido = Gdx.audio.newSound(Gdx.files.internal("sonido_herido.ogg"));
                    Sound sonidoMoneda = Gdx.audio.newSound(Gdx.files.internal("sonido_moneda.wav"));
                    Music musicaFondo = Gdx.audio.newMusic(Gdx.files.internal("musica_fondo.mp3"));

                    // 2. Carga las texturas
                    Texture texturaHeroe = new Texture(Gdx.files.internal("heroe.png"));
                    Texture texturaMoneda = new Texture(Gdx.files.internal("moneda.png"));
                    Texture texturaBolaFuego = new Texture(Gdx.files.internal("bola_fuego.png"));
                    backgroundTexture = new Texture(Gdx.files.internal("fondo_castillo.png"));

                    // 3. Crea los objetos
                    heroe = new Heroe(texturaHeroe, sonidoHerido);
                    controladorProyectiles = new ControladorProyectiles(texturaMoneda, texturaBolaFuego, sonidoMoneda, musicaFondo);

                    // camera
                    camera = new OrthographicCamera();
                    camera.setToOrtho(false, 800, 480);
                    controladorProyectiles.crear();
            }

	@Override
	public void render(float delta) {
            //limpia la pantalla con color azul obscuro.
            // ScreenUtils.clear(0, 0, 0.2f, 1); //borramos la línea del color azul
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //Dejamos esta para limpiar

            //actualizar matrices de la cámara
            camera.update();
            //actualizar 
            batch.setProjectionMatrix(camera.combined);

            batch.begin(); //dibujo comienza

            // Dibuja el fondo, estirándolo a 800x480 (el tamaño de la cámara)
            batch.draw(backgroundTexture, 0, 0, 800, 480);
            // ------------------------------------

            //dibujar textos (El resto de tu código queda igual)
            font.draw(batch, "Puntos: " + heroe.getPuntos(), 5, 475);
            font.draw(batch, "Vidas : " + heroe.getVidas(), 670, 475);
            font.draw(batch, "HighScore : " + game.getHigherScore(), camera.viewportWidth / 2 - 50, 475);

            if (!heroe.estaHerido()) {
                // movimiento del tarro desde teclado
                heroe.actualizarMovimiento();
                // caida de la lluvia 
                if (!controladorProyectiles.actualizarMovimiento(heroe)) {
                    //actualizar HigherScore
                    if (game.getHigherScore() < heroe.getPuntos())
                        game.setHigherScore(heroe.getPuntos());
                    //ir a la ventana de finde juego y destruir la actual
                    game.setScreen(new GameOverScreen(game));
                    dispose();
                }
            }

            heroe.dibujar(batch);
            controladorProyectiles.dibujarProyectiles(batch);

            batch.end(); // <-- dibujor termina
        }

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	  // continuar con sonido de lluvia
	  controladorProyectiles.continuar();
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {
		controladorProyectiles.pausar();
		game.setScreen(new PausaScreen(game, this)); 
	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
        heroe.destruir();
        controladorProyectiles.destruir();
        backgroundTexture.dispose();

    }

}
