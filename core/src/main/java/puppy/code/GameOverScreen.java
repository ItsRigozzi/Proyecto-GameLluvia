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

public class GameOverScreen implements Screen {
	@SuppressWarnings("unused")
	private final DungeonKnightGame game;
	private SpriteBatch batch;	   
	private BitmapFont font;
    private Texture backgroundTexture;
    private Music musicaGameOver;
    private Stage stage;
    private Skin skin;

	public GameOverScreen(final DungeonKnightGame game) {
		this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();
        
        stage = new Stage(new ScreenViewport());
        skin = new Skin();
        
        skin.add("restartUp", new Texture(Gdx.files.internal("reiniciar_normal.png")));
        skin.add("restartDown", new Texture(Gdx.files.internal("reiniciar_click.png")));
        skin.add("exitUp", new Texture(Gdx.files.internal("salir_normal.png")));
        skin.add("exitDown", new Texture(Gdx.files.internal("salir_click.png")));
        
        ImageButton.ImageButtonStyle restartStyle = new ImageButton.ImageButtonStyle();
        restartStyle.imageUp = skin.newDrawable("restartUp");
        restartStyle.imageDown = skin.newDrawable("restartDown");
        
        ImageButton.ImageButtonStyle exitStyle = new ImageButton.ImageButtonStyle();
        exitStyle.imageUp = skin.newDrawable("exitUp");
        exitStyle.imageDown = skin.newDrawable("exitDown");
        ImageButton restartButton = new ImageButton(restartStyle);
        ImageButton exitButton = new ImageButton(exitStyle);
        
        float buttonWidth = 120;
        float buttonHeight = 120;
        float screenCenterX = Gdx.graphics.getWidth() / 2f;
        float buttonY = 200;
        restartButton.setSize(buttonWidth, buttonHeight);
        restartButton.setPosition(screenCenterX - buttonWidth - 20, buttonY);
        
        exitButton.setSize(buttonWidth, buttonHeight);
        exitButton.setPosition(screenCenterX + 20, buttonY);
        
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game)); 
                dispose(); 
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game)); 
                dispose();
            }
        });
        
        stage.addActor(restartButton);
        stage.addActor(exitButton);
        
        backgroundTexture = new Texture(Gdx.files.internal("fondo_gameover.png"));
        musicaGameOver = Gdx.audio.newMusic(Gdx.files.internal("game_over_iv.mp3"));
        musicaGameOver.setLooping(false);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(stage.getCamera().combined);
        
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        font.getData().setScale(2.5f);
        font.draw(batch, "Tu aventura termina aquí...", 180, 350);
        
        font.getData().setScale(1, 1);
        batch.end();
        
        stage.act(delta);
        stage.draw();
	}

	@Override
	public void show() {
		musicaGameOver.play();
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
		musicaGameOver.stop();
        musicaGameOver.dispose();
        backgroundTexture.dispose();
        skin.dispose();
        stage.dispose();
	}

}
