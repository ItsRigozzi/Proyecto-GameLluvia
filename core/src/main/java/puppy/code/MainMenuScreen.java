package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * REQUISITO GM2.1: Aplicación del Patrón de Diseño Singleton (Cliente).
 * * * * Esta clase actúa como un "Cliente" del Singleton (DungeonKnightGame).
 * * Recibe la instancia única del juego para acceder a los recursos globales.
 */
public class MainMenuScreen implements Screen {

	final DungeonKnightGame game;
	private SpriteBatch batch;
	private BitmapFont font;
    private Texture backgroundTexture;
    private Music musicaMenu;
    private Stage stage;
    private Skin skin;

	public MainMenuScreen(final DungeonKnightGame game) {
            /**
             * (GM2.1) Se recibe la instancia única (Singleton) de DungeonKnightGame
             * en el constructor.
             */
            this.game = game;
            
            /**
             * (GM2.1) Se accede a los recursos globales (batch y font)
             * gestionados por el Singleton.
             */
            this.batch = game.getBatch();
            this.font = game.getFont();
            
            stage = new Stage(new ScreenViewport());
            skin = new Skin();
            
            skin.add("buttonUp", new Texture(Gdx.files.internal("inicio_normal.png")));
            skin.add("buttonDown", new Texture(Gdx.files.internal("inicio_click.png")));
            skin.add("configUp", new Texture(Gdx.files.internal("conf_normal.png")));
            skin.add("configDown", new Texture(Gdx.files.internal("conf_click.png")));
            
            ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
            buttonStyle.imageUp = skin.newDrawable("buttonUp");
            buttonStyle.imageDown = skin.newDrawable("buttonDown");
            ImageButton playButton = new ImageButton(buttonStyle);
            
            ImageButton.ImageButtonStyle configStyle = new ImageButton.ImageButtonStyle();
            configStyle.imageUp = skin.newDrawable("configUp");
            configStyle.imageDown = skin.newDrawable("configDown");
            ImageButton configButton = new ImageButton(configStyle);
            
            
            float buttonWidth = 120; 
            float buttonHeight = 120; 
            float screenCenterX = (Gdx.graphics.getWidth() / 2f) - (buttonWidth / 2f);
            float screenCenterY = 280;
            
            playButton.setSize(buttonWidth, buttonHeight);
            playButton.setPosition(screenCenterX - buttonWidth - 15, screenCenterY);
            
            configButton.setSize(buttonWidth, buttonHeight);
            configButton.setPosition(screenCenterX + 15, screenCenterY);
            
            playButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new GameScreen(game));
                    dispose(); 
                }
            });
            
            configButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new SettingsScreen(game, MainMenuScreen.this));
                }
            });
            stage.addActor(playButton);
            stage.addActor(configButton);
            
            backgroundTexture = new Texture(Gdx.files.internal("fondo_menu.png")); 
            musicaMenu = Gdx.audio.newMusic(Gdx.files.internal("The_Old_Tower_Inn.mp3"));
            musicaMenu.setLooping(true);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); 
	    batch.setProjectionMatrix(stage.getCamera().combined);

	    batch.begin();
	    batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

	    int[] scores = game.getHighScores(); 

	    font.getData().setScale(1.5f);
	    font.draw(batch, "MEJORES PUNTAJES:", 20, 150);
	    font.draw(batch, "1. " + scores[0], 20, 110);
	    font.draw(batch, "2. " + scores[1], 20, 80);
	    font.draw(batch, "3. " + scores[2], 20, 50);
	    font.getData().setScale(1);

	    batch.end();

	    stage.act(delta); 
	    stage.draw();     
	}

	@Override
	public void show() {
		musicaMenu.play();
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void dispose() {
		musicaMenu.stop();
        musicaMenu.dispose();
        backgroundTexture.dispose();
        skin.dispose();
        stage.dispose();
	}
}