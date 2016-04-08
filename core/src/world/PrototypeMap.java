package world;

import static personal.game.Constants.TILE_SIZE;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import personal.game.Direction;

import com.badlogic.gdx.math.Rectangle;

public class PrototypeMap {
	final int cols;
	final int rows;
	
	final World world;
	final WorldTile[][] tiles; // tiles[col][row]
	final List<CollisionArea> collisions;
	final Map<Direction, String> connections;
	
	public PrototypeMap(
			World world,
			int cols, int rows, 
			WorldTile[][] tiles, 
			Map<Direction, String> connections) {
		this.world = world;
		
		this.cols = cols;
		this.rows = rows;
		this.tiles = tiles;
		this.collisions = new LinkedList<>();
		this.connections = connections;
		
		generateCollisionAreas();
	}
	
	private void generateCollisionAreas() {
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				CollisionArea area = tiles[i][j].collision();
				if (area == null) {
					continue;
				}
				area.area = new Rectangle(i * TILE_SIZE, (rows - (j + 1)) * TILE_SIZE, TILE_SIZE, TILE_SIZE);
				collisions.add(area);
			}
		}
	}
	
	public ActiveMap newMap() {
		return new ActiveMap(world, cols, rows, tiles, collisions, connections);
	}
}
