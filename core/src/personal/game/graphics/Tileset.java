package personal.game.graphics;

import java.util.Map;

import world.WorldTile;

/**
 * A set of named tiles that is based on a sprite sheet.
 */
public class Tileset {
	final Map<String, WorldTile> tiles;
	
	public Tileset(Map<String, WorldTile> tiles) {
		this.tiles = tiles;
	}
	
	public WorldTile get(String name) {
		return tiles.get(name);
	}
}
