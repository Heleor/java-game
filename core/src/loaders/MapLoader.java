package loaders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import loaders.MapLoader.RawMap.RawConnection;
import personal.game.Constants;
import personal.game.graphics.Tileset;
import world.Connection;
import world.PrototypeMap;
import world.WorldTile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Rectangle;
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
		
		static class RawConnection {
			String type;
			String map;
			String edge = null;
		}
		
		ArrayList<RawConnection> connections = null;
	}
	
	private final RawMap raw;
	private final Tileset tileset;
	
	private MapLoader(FileHandle file) {
		Json json = new Json();
		this.raw = json.fromJson(RawMap.class, file);
		this.tileset = TilesetLoader.load(Gdx.files.internal(raw.tileset.file));
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
		
		Map<Connection, String> connections = new HashMap<>();
		if (raw.connections != null) {
			for (RawConnection c : raw.connections) {
				if (c.type.equals("edge")) {
					Rectangle rectangle = null;
					switch (c.edge) {
					case "left":
						rectangle = new Rectangle(
								-5,0,
								5,raw.height * Constants.TILE_SIZE);
						break;
					case "right":
						rectangle = new Rectangle(
								raw.width * Constants.TILE_SIZE,0,
								5,raw.height * Constants.TILE_SIZE);
						break;
					case "top":
						rectangle = new Rectangle(
								0, raw.height * Constants.TILE_SIZE,
								raw.width * Constants.TILE_SIZE, 5);
						break;
					case "bottom":
						rectangle = new Rectangle(
								0, -5,
								raw.width * Constants.TILE_SIZE, 5);
						break;
					}
					
					//Connection connection = new Connection(rectangle);
					//connections.put(connection, c.map);
				}
			}
		}
		
		return new PrototypeMap(raw.width, raw.height, tiles, connections);
	}
	
	public static PrototypeMap load(FileHandle file) {
		MapLoader loader = new MapLoader(file);
		return loader.getMap();
	}
}
