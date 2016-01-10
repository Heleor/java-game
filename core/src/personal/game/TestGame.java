package personal.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Blending;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TestGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture b;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		Pixmap data = new Pixmap(Gdx.files.internal("character_sheet.png"));
		
		Pixmap map = new Pixmap(data.getWidth(), data.getHeight(), Pixmap.Format.RGBA8888);
		map.drawPixmap(data, 0, 0);
		
		int original_color = data.getPixel(10, 10);
		
		Pixmap.setBlending(Pixmap.Blending.None);
		map.setColor(Color.rgba8888(1.0f, 0.0f, 0.0f, 0.0f));
		for (int x = 0; x < map.getWidth(); x++) {
			for (int y = 0; y < map.getHeight(); y++) {
				if (map.getPixel(x, y) == original_color) {
					map.drawPixel(x, y);
				}
			}
		}
		Pixmap.setBlending(Pixmap.Blending.SourceOver);

		
		img = new Texture(map);
		b = new Texture(data);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.0f, 0.9f, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//batch.enableBlending();
		batch.begin();
		batch.draw(img,0,0,320,480);
		batch.draw(b,320,0,320,480);
		batch.end();
	}
}
