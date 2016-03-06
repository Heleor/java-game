package world;

import personal.game.graphics.Spritesheet.Tile;

public class WorldTile {
	public final Tile tile;
	public boolean passable = true;
	public int slowing = 0; // 0-3
	
	public WorldTile(Tile tile) {
		this.tile = tile;
	}
}
