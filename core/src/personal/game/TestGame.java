package personal.game;

import static personal.game.Constants.TILE_SIZE;
import loaders.CharacterLoader;
import loaders.MapLoader;
import world.PrototypeMap;
import character.Character;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TestGame extends ApplicationAdapter {
	static final int FPS = 30;
	static final float PERIOD = 1.0f / FPS;
	
	SpriteBatch batch;
	
	Texture img;
	Character character;

	int tileCols = 10;
	int tileRows = 8;
	
	int VIEW_WIDTH = TILE_SIZE * tileCols;
	int VIEW_HEIGHT = TILE_SIZE * tileRows;
	
	OrthographicCamera camera;
	
	@Override
	public void create () {
		camera = new OrthographicCamera(VIEW_WIDTH, VIEW_HEIGHT);
		camera.position.set(VIEW_WIDTH / 2, VIEW_HEIGHT / 2, 0);
		camera.update();
		
		batch = new SpriteBatch();
		
		PrototypeMap map = MapLoader.load(Gdx.files.internal("house.map.json"));
		img = map.newMap().render();
		
		character = CharacterLoader.load(Gdx.files.internal("link.character.json"));
		character.setAnimation("walk_r");
	}
	
	public void update() {
		character.update();
		character.x++;
		if (character.x > VIEW_WIDTH) {
			character.x = -16;
			character.y += 16;
			if (character.y >= VIEW_HEIGHT) {
				character.y = 0;
			}
		}
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
		batch.draw(img,0,0,VIEW_WIDTH, VIEW_HEIGHT);
		character.draw(batch);
		batch.end();
	}
}
