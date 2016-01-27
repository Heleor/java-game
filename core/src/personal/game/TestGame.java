package personal.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
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

		Pixmap sprites = new Pixmap(Gdx.files.internal("overworld.png"));
		Spritesheet spritesheet = new Spritesheet(sprites);
		
		Pixmap map = new Pixmap(tileCols * tileWidth, tileRows * tileHeight, Pixmap.Format.RGBA8888);
		
		for (int i = 0; i < tileCols; i++) {
			for (int j = 0; j < tileRows; j++) {
				spritesheet.getRandom().drawTo(map, i * tileWidth, j * tileHeight);
			}
		}
		
		img = new Texture(map);
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
