package personal.game;

import personal.game.graphics.Spritesheet.Tile;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class TiledMap {
	static final int TILE_SIZE = 16;
	
	private int tileCols;
	private int tileRows;
	
	private Tile[][] tiles;
	
	public TiledMap(int width, int height, Tile[][] tiles) {
		this.tileCols = width;
		this.tileRows = height;
		this.tiles = tiles;
	}
	
	public Texture render() {
		Pixmap map = new Pixmap(tileCols * TILE_SIZE, tileRows * TILE_SIZE, Pixmap.Format.RGBA8888);
		
		for (int i = 0; i < tileCols; i++) {
			for (int j = 0; j < tileRows; j++) {
				tiles[i][j].drawTo(map, i * TILE_SIZE, j * TILE_SIZE);
			}
		}
		
		return new Texture(map);
	}
}
