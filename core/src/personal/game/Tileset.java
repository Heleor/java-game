package personal.game;

import java.util.Map;

import personal.game.graphics.Spritesheet.Tile;

/**
 * A set of named tiles that is based on a sprite sheet.
 */
public class Tileset {
	final Map<String, Tile> tiles;
	
	public Tileset(Map<String, Tile> tiles) {
		this.tiles = tiles;
	}
}
