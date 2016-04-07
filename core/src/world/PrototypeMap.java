package world;

import static personal.game.Constants.TILE_SIZE;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.math.Rectangle;

public class PrototypeMap {
	final int cols;
	final int rows;
	
	final WorldTile[][] tiles; // tiles[col][row]
	final List<CollisionArea> collisions;
	final Map<Connection, String> connections;
	
	public PrototypeMap(int cols, int rows, 
			WorldTile[][] tiles, 
			Map<Connection, String> connections) {
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
		return new ActiveMap(cols, rows, tiles, collisions);
	}

	public Collection<String> getConnections() {
		return connections.values();
	}
}
