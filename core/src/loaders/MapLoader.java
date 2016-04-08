package loaders;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import personal.game.Direction;
import personal.game.graphics.Tileset;
import world.PrototypeMap;
import world.World;
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
		
		HashMap<String, String> aliases = null;
		
		String base_tile = null;
		String[][] tiles; // tiles[row][col]
		
		HashMap<String, String> connections = null;
	}
	
	private final World world;
	private final RawMap raw;
	private final Tileset tileset;
	
	private MapLoader(World world, FileHandle file) {
		Json json = new Json();
		this.raw = json.fromJson(RawMap.class, file);
		this.tileset = TilesetLoader.load(Gdx.files.internal(raw.tileset.file));
		this.world = world;
	}
	
	private WorldTile getTile(String key) {
		if (raw.aliases != null && raw.aliases.containsKey(key)) {
			return getTile(raw.aliases.get(key));
		}
		WorldTile tile = tileset.get(key);
		return tile != null ? tile : getTile(raw.base_tile);
	}
	
	public PrototypeMap getMap() {
		// This will have to pivot, as we want row-major order in the file
		// (for human readability) and column-major order in the code (for x/y confusion)
		WorldTile[][] tiles = new WorldTile[raw.width][raw.height];
		for (int row = 0; row < raw.height; row++) {
			// If the row is not defined
			if (raw.tiles.length <= row) {
				for (int col = 0; col < raw.width; col++) {
					tiles[col][row] = getTile(null);
				}
			} else {
				for (int col = 0; col < raw.width; col++) {
					if (raw.tiles[row].length <= col) {
						tiles[col][row] = getTile(null);
					} else {
						String tilename = raw.tiles[row][col];
						tiles[col][row] = getTile(tilename);						
					}
				}				
			}
		}
		
		Map<Direction, String> connections = new HashMap<>();
		if (raw.connections != null) {
			for (Entry<String, String> i : raw.connections.entrySet()) {
				connections.put(Direction.valueOf(i.getKey()), i.getValue());
			}
		}
		
		return new PrototypeMap(world, raw.width, raw.height, tiles, connections);
	}
	
	public static PrototypeMap load(World world, FileHandle file) {
		MapLoader loader = new MapLoader(world, file);
		return loader.getMap();
	}
}
