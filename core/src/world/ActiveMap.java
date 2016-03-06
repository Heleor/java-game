package world;

import static personal.game.Constants.TILE_SIZE;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class ActiveMap {
	private int tileCols;
	private int tileRows;
	
	private WorldTile[][] tiles;
	
	private Pixmap pixmap;
	private Texture texture;
	
	public ActiveMap(int width, int height, WorldTile[][] tiles) {
		this.tileCols = width;
		this.tileRows = height;
		this.tiles = tiles;
		
		this.pixmap = new Pixmap(tileCols * TILE_SIZE, tileRows * TILE_SIZE, Pixmap.Format.RGBA8888);
		draw();
	}
	
	public int getWidth() {
		return tileCols * TILE_SIZE;
	}
	
	public int getHeight() {
		return tileRows * TILE_SIZE;
	}
	
	public Rectangle getArea() {
		return new Rectangle(0,0,getWidth(), getHeight());
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(texture, 0,0, getWidth(), getHeight());
	}
	
	/**
	 * Update the internal texture for this map.
	 */
	private void draw() {
		for (int i = 0; i < tileCols; i++) {
			for (int j = 0; j < tileRows; j++) {
				tiles[i][j].tile.drawTo(pixmap, i * TILE_SIZE, j * TILE_SIZE);
			}
		}
		texture = new Texture(pixmap);
	}

	public void renderCollision(ShapeRenderer shapes) {
		for (int i = 0; i < tileCols; i++) {
			for (int j = 0; j < tileRows; j++) {
				WorldTile c = tiles[i][j];
				if (!c.passable) {
					shapes.setColor(1.0f, 0.0f, 0.0f, 0.5f);
				} else if (c.slowing > 0) {
					shapes.setColor(1.0f, 1.0f - (c.slowing / 4.0f), 0.0f, 0.5f);
				} else {
					continue;
				}
				shapes.rect(i * TILE_SIZE, (tileRows - (j + 1)) * TILE_SIZE, TILE_SIZE, TILE_SIZE);
			}
		}
	}
}
