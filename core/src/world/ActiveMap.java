package world;

import static personal.game.Constants.TILE_SIZE;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class ActiveMap {
	private int tileCols;
	private int tileRows;
	
	private WorldTile[][] tiles;
	
	public ActiveMap(int width, int height, WorldTile[][] tiles) {
		this.tileCols = width;
		this.tileRows = height;
		this.tiles = tiles;
	}
	
	public Texture render() {
		Pixmap map = new Pixmap(tileCols * TILE_SIZE, tileRows * TILE_SIZE, Pixmap.Format.RGBA8888);
		
		for (int i = 0; i < tileCols; i++) {
			for (int j = 0; j < tileRows; j++) {
				tiles[i][j].tile.drawTo(map, i * TILE_SIZE, j * TILE_SIZE);
			}
		}
		
		return new Texture(map);
	}
}
