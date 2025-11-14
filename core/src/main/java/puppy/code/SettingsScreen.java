package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
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

public class SettingsScreen implements Screen {

	final DungeonKnightGame game;
	@SuppressWarnings("unused")
    private final Screen previousScreen; 
    private SpriteBatch batch;
    private Stage stage;
    private Skin skin;
    private BitmapFont font;
    private Preferences prefs;
    private String controlSeleccionado;

    public SettingsScreen(final DungeonKnightGame game, final Screen previousScreen) {
    	this.game = game;
        this.previousScreen = previousScreen;
        this.batch = game.getBatch();
        this.font = game.getFont();
        
        stage = new Stage(new ScreenViewport());
        skin = new Skin();

        prefs = Gdx.app.getPreferences("dungeonknight_settings");

        int controlMode = prefs.getInteger("controlMode", 0); 
        if (controlMode == 0) {
            controlSeleccionado = "FLECHAS";
        } else {
            controlSeleccionado = "WASD";
        }

        skin.add("backUp", new Texture(Gdx.files.internal("salir_normal.png")));
        skin.add("backDown", new Texture(Gdx.files.internal("salir_click.png")));
        
        skin.add("arrowIcon", new Texture(Gdx.files.internal("flechas.png")));
        skin.add("wasdIcon", new Texture(Gdx.files.internal("WASD.png")));
        
        ImageButton.ImageButtonStyle backStyle = new ImageButton.ImageButtonStyle();
        backStyle.imageUp = skin.newDrawable("backUp");
        backStyle.imageDown = skin.newDrawable("backDown");

        ImageButton.ImageButtonStyle arrowStyle = new ImageButton.ImageButtonStyle();
        arrowStyle.imageUp = skin.newDrawable("arrowIcon");
        arrowStyle.imageDown = skin.newDrawable("arrowIcon");

        ImageButton.ImageButtonStyle wasdStyle = new ImageButton.ImageButtonStyle();
        wasdStyle.imageUp = skin.newDrawable("wasdIcon");
        wasdStyle.imageDown = skin.newDrawable("wasdIcon");

        ImageButton backButton = new ImageButton(backStyle);
        ImageButton arrowButton = new ImageButton(arrowStyle);
        ImageButton wasdButton = new ImageButton(wasdStyle);

        backButton.setSize(100, 100);
        backButton.setPosition(20, 20);

	    float controlButtonSize = 120;
	    arrowButton.setSize(controlButtonSize, controlButtonSize); 
	    arrowButton.setPosition(250, 200); 
	
	    wasdButton.setSize(controlButtonSize, controlButtonSize);
	    wasdButton.setPosition(430, 200);
        
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(previousScreen);
                dispose();
            }
        });

        arrowButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                prefs.putInteger("controlMode", 0);
                prefs.flush();
                controlSeleccionado = "FLECHAS";
            }
        });
        
        // Botón WASD
        wasdButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                prefs.putInteger("controlMode", 1);
                prefs.flush();
                controlSeleccionado = "WASD";
            }
        });
        
        stage.addActor(backButton);
        stage.addActor(arrowButton);
        stage.addActor(wasdButton);
    }
    
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(stage.getCamera().combined);
        
        batch.begin();

        font.getData().setScale(3);
        font.draw(batch, "AJUSTES", 300, 450); 

        font.getData().setScale(2);
        font.draw(batch, "Controles:", 100, 270);
        
        font.getData().setScale(1.5f);
        font.draw(batch, "Seleccionado: " + controlSeleccionado, 300, 150);

        font.getData().setScale(1);
        batch.end();
        
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        skin.dispose();
        stage.dispose();
    }
    
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}