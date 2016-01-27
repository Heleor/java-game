package personal.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TestGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	
	int tileWidth = 16;
	int tileHeight = 16;
	
	int tileCols = 10;
	int tileRows = 9;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		TiledMap map = MapLoader.load(Gdx.files.internal("house.map.json"));
		img = map.render();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.0f, 0.9f, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//batch.enableBlending();
		batch.begin();
		batch.draw(img,0,0,640,480);
		batch.end();
	}
}
