package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class PausaScreen implements Screen {

	private final DungeonKnightGame game;
	private final GameScreen juego;
	private SpriteBatch batch;	   
	private BitmapFont font;
	private OrthographicCamera camera;
	private Stage stage;
    private Skin skin;

	public PausaScreen (final DungeonKnightGame game, GameScreen juego) {
		this.game = game;
        this.juego = juego;
        this.batch = game.getBatch();
        this.font = game.getFont();
        
        stage = new Stage(new ScreenViewport());
        skin = new Skin();
        skin.add("resumeUp", new Texture(Gdx.files.internal("inicio_normal.png")));
        skin.add("resumeDown", new Texture(Gdx.files.internal("inicio_click.png")));
        skin.add("restartUp", new Texture(Gdx.files.internal("reiniciar_normal.png")));
        skin.add("restartDown", new Texture(Gdx.files.internal("reiniciar_click.png")));
        skin.add("exitUp", new Texture(Gdx.files.internal("salir_normal.png")));
        skin.add("exitDown", new Texture(Gdx.files.internal("salir_click.png")));
        
        ImageButton.ImageButtonStyle resumeStyle = new ImageButton.ImageButtonStyle();
        resumeStyle.imageUp = skin.newDrawable("resumeUp");
        resumeStyle.imageDown = skin.newDrawable("resumeDown");

        ImageButton.ImageButtonStyle restartStyle = new ImageButton.ImageButtonStyle();
        restartStyle.imageUp = skin.newDrawable("restartUp");
        restartStyle.imageDown = skin.newDrawable("restartDown");

        ImageButton.ImageButtonStyle exitStyle = new ImageButton.ImageButtonStyle();
        exitStyle.imageUp = skin.newDrawable("exitUp");
        exitStyle.imageDown = skin.newDrawable("exitDown");

        
        ImageButton resumeButton = new ImageButton(resumeStyle);
        ImageButton restartButton = new ImageButton(restartStyle);
        ImageButton exitButton = new ImageButton(exitStyle);
        
        float buttonWidth = 120;
        float buttonHeight = 120;
        float screenCenterX = (Gdx.graphics.getWidth() / 2f) - (buttonWidth / 2f);
        
        float buttonY_Resume = 300;
        float buttonY_Restart = 180;
        float buttonY_Exit = 60;
        
        resumeButton.setSize(buttonWidth, buttonHeight);
        resumeButton.setPosition(screenCenterX, buttonY_Resume);
        
        restartButton.setSize(buttonWidth, buttonHeight);
        restartButton.setPosition(screenCenterX, buttonY_Restart);
        
        exitButton.setSize(buttonWidth, buttonHeight);
        exitButton.setPosition(screenCenterX, buttonY_Exit);
        
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(juego); 
                dispose(); 
            }
        });
        
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Crea una NUEVA pantalla de juego (empieza de cero)
                game.setScreen(new GameScreen(game)); 
                dispose();
            }
        });
        
        // Botón SALIR (al Menú)
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game)); // Vuelve al menú
                dispose();
            }
        });

        stage.addActor(resumeButton);
        stage.addActor(restartButton);
        stage.addActor(exitButton);
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0, 0.5f);
		batch.setProjectionMatrix(stage.getCamera().combined);

		batch.begin();
        font.getData().setScale(2.5f);
        font.draw(batch, "PAUSA", 350, 450);
        font.getData().setScale(1, 1);
        batch.end();

        stage.act(delta);
        stage.draw();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		skin.dispose();
        stage.dispose();
	}

}

