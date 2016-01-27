package personal.game;

import personal.game.graphics.Spritesheet.Tile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class MapLoader {
	
	static class RawMap {
		static class TileSetRef {
			String file;
		}
		
		TileSetRef tileset;
		int width;
		int height;
		
		String base_tile;
		String[][] tiles; // tiles[row][col]
	}
	
	public static TiledMap load(FileHandle file) {
		Json json = new Json();
		RawMap raw = json.fromJson(RawMap.class, file);
		
		Tileset tileset = TilesetLoader.load(Gdx.files.internal(raw.tileset.file));

		// This will have to pivot, as we want row-major order in the file
		// (for human readability) and column-major order in the code (for x/y confusion)
		Tile[][] tiles = new Tile[raw.width][raw.height];
		for (int row = 0; row < raw.height; row++) {
			
			System.out.println("Row " + row);
			// If the row is not defined
			if (raw.tiles.length <= row) {
				for (int col = 0; col < raw.width; col++) {
					tiles[col][row] = tileset.tiles.get(raw.base_tile);
				}
			} else {
				for (int col = 0; col < raw.width; col++) {
					System.out.println("Col " + col);
					if (raw.tiles[row].length <= col) {
						tiles[col][row] = tileset.tiles.get(raw.base_tile);
					} else {
						String tilename = raw.tiles[row][col];
						tiles[col][row] = tileset.tiles.get(tilename);						
					}
				}				
			}
		}
		
		return new TiledMap(raw.width, raw.height, tiles);
	}
}
