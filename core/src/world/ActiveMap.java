package world;

import static personal.game.Constants.TILE_SIZE;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
}
