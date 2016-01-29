package personal.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TestGame extends ApplicationAdapter {
	SpriteBatch batch;
	
	Texture img;
	Character character;
	
	int tileWidth = 16;
	int tileHeight = 16;
	
	int tileCols = 10;
	int tileRows = 9;
	
	int VIEW_WIDTH = tileWidth * tileCols;
	int VIEW_HEIGHT = tileHeight * tileRows;
	
	OrthographicCamera camera;
	
	@Override
	public void create () {
		camera = new OrthographicCamera(VIEW_WIDTH, VIEW_HEIGHT);
		camera.position.set(VIEW_WIDTH / 2, VIEW_HEIGHT / 2, 0);
		camera.update();
		
		batch = new SpriteBatch();
		
		TiledMap map = MapLoader.load(Gdx.files.internal("house.map.json"));
		img = map.render();
		
		character = new Character(TilesetLoader.load(Gdx.files.internal("link.tileset.json")));
		character.setAnimation("stand");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.0f, 0.9f, 0, 1);

		camera.update();
		batch.setProjectionMatrix(camera.combined);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(img,0,0,VIEW_WIDTH, VIEW_HEIGHT);
		character.draw(batch);
		batch.end();
	}
}
