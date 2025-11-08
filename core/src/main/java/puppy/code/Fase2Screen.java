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

public class Fase2Screen implements Screen {

    final DungeonKnightGame game;
    private final GameScreen gameScreen;
    private SpriteBatch batch;
    private Stage stage;
    private Skin skin;
    private BitmapFont font;
    private Texture backgroundTexture;
    private Music fanfare;

    private boolean mostrandoDetalles = false;

    public Fase2Screen(final DungeonKnightGame game, final GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;
        this.batch = game.getBatch();
        this.font = game.getFont();
        
        stage = new Stage(new ScreenViewport());
        skin = new Skin();

        // --- Cargar Assets ---
        backgroundTexture = new Texture(Gdx.files.internal("pergamino.png"));
        fanfare = Gdx.audio.newMusic(Gdx.files.internal("fase2.mp3"));
        fanfare.setLooping(false);


        skin.add("continueUp", new Texture(Gdx.files.internal("inicio_normal.png")));
        skin.add("continueDown", new Texture(Gdx.files.internal("inicio_click.png")));
        skin.add("detailsUp", new Texture(Gdx.files.internal("detalle_normal.png")));
        skin.add("detailsDown", new Texture(Gdx.files.internal("detalle_click.png")));
        skin.add("exitUp", new Texture(Gdx.files.internal("salir_normal.png")));
        skin.add("exitDown", new Texture(Gdx.files.internal("salir_click.png")));

        ImageButton.ImageButtonStyle continueStyle = new ImageButton.ImageButtonStyle();
        continueStyle.imageUp = skin.newDrawable("continueUp");
        continueStyle.imageDown = skin.newDrawable("continueDown");

        ImageButton.ImageButtonStyle detailsStyle = new ImageButton.ImageButtonStyle();
        detailsStyle.imageUp = skin.newDrawable("detailsUp");
        detailsStyle.imageDown = skin.newDrawable("detailsDown");

        ImageButton.ImageButtonStyle exitStyle = new ImageButton.ImageButtonStyle();
        exitStyle.imageUp = skin.newDrawable("exitUp");
        exitStyle.imageDown = skin.newDrawable("exitDown");

        ImageButton continueButton = new ImageButton(continueStyle);
        ImageButton detailsButton = new ImageButton(detailsStyle);
        ImageButton exitButton = new ImageButton(exitStyle);

        float buttonWidth = 100;
        float buttonHeight = 100;
        float screenCenterX = (Gdx.graphics.getWidth() / 2f);
        
        continueButton.setSize(buttonWidth, buttonHeight);
        continueButton.setPosition(screenCenterX - (buttonWidth / 2f) - 120, 100);

        detailsButton.setSize(buttonWidth, buttonHeight);
        detailsButton.setPosition(screenCenterX - (buttonWidth / 2f), 100);
        
        exitButton.setSize(buttonWidth, buttonHeight);
        exitButton.setPosition(screenCenterX - (buttonWidth / 2f) + 120, 100);

        
        continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.activarFase2(); 
                game.setScreen(gameScreen);
                dispose(); 
            }
        });

        detailsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mostrandoDetalles = !mostrandoDetalles; 
            }
        });
        
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
                gameScreen.dispose(); 
                dispose();
            }
        });

        stage.addActor(continueButton);
        stage.addActor(detailsButton);
        stage.addActor(exitButton);
    }
    
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        fanfare.play(); 
    }

    @Override
    public void render(float delta) {
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(stage.getCamera().combined);
        
        batch.begin();
        // Dibuja el fondo de pergamino
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // --- TÍTULO ---
        font.getData().setScale(3);
        font.draw(batch, "¡Llegaste a la Fase 2!", 200, 440); // Subimos un poco el título

        if (mostrandoDetalles) {
            
            // --- 1. MUESTRA LOS DETALLES ---
            font.getData().setScale(1.5f);
            // Subimos la posición Y a 380 para que no tape los botones
            font.draw(batch, "Nuevas Amenazas:\n- Fuego Azul (Quita 2 Vidas)\n\n" +
                             "Nuevas Habilidades:\n- Movimiento Libre (Vertical)\n- Power-Ups Habilitados", 
                             250, 380); // <-- POSICIÓN CORREGIDA

        } else {
            font.getData().setScale(1.8f); // Un poco más grande
            // Posicionamos el texto de introducción donde estaban los detalles antes
            font.draw(batch, "Tu aventura evoluciona.\n\nPresiona (i) para ver los detalles...", 
                             200, 340);
        }
        
        font.getData().setScale(1);
        batch.end();
        
        // Dibuja los botones (el stage)
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        skin.dispose();
        stage.dispose();
        backgroundTexture.dispose();
        fanfare.dispose();
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