package personal.game;

import static personal.game.Constants.TILE_SIZE;
import personal.game.input.InputBuffer;
import personal.game.input.InputFrame;
import world.World;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;

public class TestGame extends ApplicationAdapter {
	static final int FPS = 60;
	static final float PERIOD = 1.0f / FPS;
	
	static final int VIEW_WIDTH = TILE_SIZE * 10; 
	static final int VIEW_HEIGHT = TILE_SIZE * 8;

	InputBuffer input;
	World world;
	
	boolean showCollisions = false;
	
	@Override
	public void create () {
		input = new InputBuffer();
		world = new World();
		world.initialize("link", "house");
	}
	
	public void advance() {
		InputFrame frame = input.update();
		world.advance(frame);
	}
	
	public void update() {
		input.poll();
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
			showCollisions = !showCollisions;
		}
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
			world.transitionTo("house", Direction.RIGHT);
		}
	}
	
	float sumDT = 0;
	
	public void trackTime() {
		update();
		
		float dt = Gdx.graphics.getDeltaTime();
		sumDT += dt;
		
		while(sumDT >= PERIOD) {
			sumDT -= PERIOD;
			advance();
		}
	}

	@Override
	public void render() {
		trackTime();
		
		Gdx.gl.glClearColor(1.0f, 0, 0, 1);

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		world.render();
		
		if (showCollisions) {
			world.renderCollisions();
		}
	}
}
