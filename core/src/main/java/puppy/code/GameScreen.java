package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
        private Texture fondoFase2;
        private Texture texturaBolaFuegoAzul;
        private Texture texturaCura;
        private boolean enTransicionFase2 = false;
        private float tiempoTransicion = 3.0f;
        private Texture texturaEscudo;
        private Texture texturaHeroeAura;
        private Texture texturaVelocidad;

	   
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
                    fondoFase2 = new Texture(Gdx.files.internal("fondo_fase2.png"));
                    texturaBolaFuegoAzul = new Texture(Gdx.files.internal("bola_fuego_azul.png"));
                    texturaCura = new Texture(Gdx.files.internal("cura.png"));
                    texturaEscudo = new Texture(Gdx.files.internal("escudo.png"));
                    texturaHeroeAura = new Texture(Gdx.files.internal("heroe_aura.png"));
                    texturaVelocidad = new Texture(Gdx.files.internal("velocidad.png"));

                    // 3. Crea los objetos
                    heroe = new Heroe(texturaHeroe, texturaHeroeAura, sonidoHerido);
                    controladorProyectiles = new ControladorProyectiles(texturaMoneda, texturaBolaFuego, texturaBolaFuegoAzul, texturaCura, texturaEscudo, texturaVelocidad, sonidoMoneda, musicaFondo);

                    // camera
                    camera = new OrthographicCamera();
                    camera.setToOrtho(false, 800, 480);
                    controladorProyectiles.crear();
            }

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); 
	    camera.update();
	    batch.setProjectionMatrix(camera.combined);

	    if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
	        pause(); // Llama al método pause() que arreglamos
	        return; // Detiene la ejecución de este frame
	    }
	    
	    batch.begin();

	    if (controladorProyectiles.isFase2()) {
	        batch.draw(fondoFase2, 0, 0, 800, 480);
	    } else {
	        batch.draw(backgroundTexture, 0, 0, 800, 480);
	    }


	    font.draw(batch, "Puntos: " + heroe.getPuntos(), 5, 475);
	    font.draw(batch, "Vidas : " + heroe.getVidas(), 670, 475);
	    font.draw(batch, "HighScore : " + game.getTopScore(), camera.viewportWidth / 2 - 50, 475);

	    if (!heroe.estaHerido()) {
	        heroe.actualizarMovimiento();

	        if (!controladorProyectiles.isFase2() && heroe.getPuntos() >= 50) {
	            
	            // Pausa el juego y cambia a la pantalla de transición
	            game.setScreen(new Fase2Screen(game, this)); 
	            controladorProyectiles.limpiarProyectiles();
	            
	        } else {
	            if (!controladorProyectiles.actualizarMovimiento(heroe)) {
	                // (Lógica de Game Over...)
	            	game.checkAndSaveScore(heroe.getPuntos());

	            	game.setScreen(new GameOverScreen(game));
	                dispose();
	            }
	        }
	    }
	    
	    heroe.dibujar(batch);
	    controladorProyectiles.dibujarProyectiles(batch);
	    
	    batch.end();
	}


	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	  // continuar con sonido de lluvia
	  controladorProyectiles.continuar();
	  Gdx.input.setInputProcessor(null);
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
		Gdx.input.setInputProcessor(null);

	}
	public void activarFase2() {
	    controladorProyectiles.activarFase2();
	    heroe.permitirMovimientoVertical();
	}
	
	public void dispose(){
		heroe.destruir();
        controladorProyectiles.destruir();
        backgroundTexture.dispose();

    }

}
