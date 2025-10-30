package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;


public class MainMenuScreen implements Screen {

	final DungeonKnightGame game;
	private SpriteBatch batch;
	private BitmapFont font;
	private OrthographicCamera camera;
        
        private Texture backgroundTexture;

	public MainMenuScreen(final DungeonKnightGame game) {
            this.game = game;
            this.batch = game.getBatch();
            this.font = game.getFont();
            camera = new OrthographicCamera();
            camera.setToOrtho(false, 800, 480);
            backgroundTexture = new Texture(Gdx.files.internal("fondo_menu.png"));
	}

	@Override
	public void render(float delta) {
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
                batch.draw(backgroundTexture, 0, 0, 800, 480);
                
		font.getData().setScale(2, 2);
		font.draw(batch, "Aventura del Caballero!!! ", 300, 320);
                
                font.getData().setScale(1.5f, 1.5f);
		font.draw(batch, "Toca en cualquier lugar para comenzar!", 220, 80);
                
                font.getData().setScale(1, 1);
		batch.end();

		if (Gdx.input.isTouched()) {
			game.setScreen(new GameScreen(game));
			dispose();
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		backgroundTexture.dispose();
	}

}
