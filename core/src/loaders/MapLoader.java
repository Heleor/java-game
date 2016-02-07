package loaders;

import personal.game.graphics.Tileset;
import world.ActiveMap;
import world.PrototypeMap;
import world.WorldTile;

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
	
	public static PrototypeMap load(FileHandle file) {
		Json json = new Json();
		RawMap raw = json.fromJson(RawMap.class, file);
		
		Tileset tileset = TilesetLoader.load(Gdx.files.internal(raw.tileset.file));

		// This will have to pivot, as we want row-major order in the file
		// (for human readability) and column-major order in the code (for x/y confusion)
		WorldTile[][] tiles = new WorldTile[raw.width][raw.height];
		for (int row = 0; row < raw.height; row++) {
			// If the row is not defined
			if (raw.tiles.length <= row) {
				for (int col = 0; col < raw.width; col++) {
					tiles[col][row] = tileset.get(raw.base_tile);
				}
			} else {
				for (int col = 0; col < raw.width; col++) {
					if (raw.tiles[row].length <= col) {
						tiles[col][row] = tileset.get(raw.base_tile);
					} else {
						String tilename = raw.tiles[row][col];
						tiles[col][row] = tileset.get(tilename);						
					}
				}				
			}
		}
		
		return new PrototypeMap(raw.width, raw.height, tiles);
	}
}
