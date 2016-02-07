package world;


public class PrototypeMap {
	final int cols;
	final int rows;
	
	final WorldTile[][] tiles; // tiles[col][row]
	
	public PrototypeMap(int cols, int rows, WorldTile[][] tiles) {
		this.cols = cols;
		this.rows = rows;
		this.tiles = tiles;
	}
	
	public ActiveMap newMap() {
		return new ActiveMap(cols, rows, tiles);
	}
}
