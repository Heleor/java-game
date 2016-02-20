package personal.game;

import static personal.game.Constants.TILE_SIZE;
import world.World;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TestGame extends ApplicationAdapter {
	static final int FPS = 60;
	static final float PERIOD = 1.0f / FPS;
	
	static final int VIEW_WIDTH = TILE_SIZE * 10; 
	static final int VIEW_HEIGHT = TILE_SIZE * 8;

	SpriteBatch batch;
	
	World world;
	OrthographicCamera camera;
	
	@Override
	public void create () {
		camera = new OrthographicCamera(VIEW_WIDTH, VIEW_HEIGHT);
		camera.position.set(VIEW_WIDTH / 2, VIEW_HEIGHT / 2, 0);
		camera.update();
		
		batch = new SpriteBatch();
		
		world = new World();
		world.initialize("link", "house");
	}
	
	public void update() {
		world.advance();
	}
	
	float sumDT = 0;
	
	public void trackTime() {
		float dt = Gdx.graphics.getDeltaTime();
		sumDT += dt;
		
		while(sumDT >= PERIOD) {
			sumDT -= PERIOD;
			update();
		}
	}

	@Override
	public void render() {
		trackTime();
		
		Gdx.gl.glClearColor(1.0f, 0, 0, 1);

		camera.update();
		batch.setProjectionMatrix(camera.combined);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		world.render(batch);
		batch.end();
	}
}
