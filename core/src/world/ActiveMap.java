package world;

import static personal.game.Constants.TILE_SIZE;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import personal.game.Direction;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class ActiveMap {
	private int tileCols;
	private int tileRows;
	
	private WorldTile[][] tiles;
	private List<CollisionArea> collisions;
	
	private Pixmap pixmap;
	private Texture texture;
	
	private World world;
	private Map<Direction, String> connections;
	
	public ActiveMap(World world, int width, int height, 
			WorldTile[][] tiles, List<CollisionArea> collisions,
			Map<Direction, String> connections) {
		this.world = world;
		
		this.tileCols = width;
		this.tileRows = height;
		this.tiles = tiles;
		this.collisions = collisions;
		this.connections = connections;
		
		this.pixmap = new Pixmap(tileCols * TILE_SIZE, tileRows * TILE_SIZE, Pixmap.Format.RGBA8888);
		draw();
	}
	
	public int getWidth() {
		return tileCols * TILE_SIZE;
	}
	
	public int getHeight() {
		return tileRows * TILE_SIZE;
	}
	
	public Rectangle getArea() {
		return new Rectangle(0,0,getWidth(), getHeight());
	}
	
	public void render(SpriteBatch batch, float xOff, float yOff) {
		batch.draw(texture, xOff,yOff, getWidth(), getHeight());
	}
	
	public void render(SpriteBatch batch) {
		render(batch, 0, 0);
	}
	
	/**
	 * Update the internal texture for this map.
	 */
	private void draw() {
		for (int i = 0; i < tileCols; i++) {
			for (int j = 0; j < tileRows; j++) {
				tiles[i][j].tile.drawTo(pixmap, i * TILE_SIZE, j * TILE_SIZE);
			}
		}
		texture = new Texture(pixmap);
	}

	public void renderCollision(ShapeRenderer shapes) {
		for (CollisionArea area : collisions) {
			if (!area.passable) {
				shapes.setColor(1.0f, 0.0f, 0.0f, 0.5f);
			} else if (area.slow > 0) {
				shapes.setColor(1.0f, 1.0f - (area.slow / 4.0f), 0.0f, 0.5f);
			} else {
				shapes.setColor(0.5f, 0.0f, 0.5f, 0.5f);
			}
			shapes.rect(area.area.x, area.area.y, area.area.width, area.area.height);
		}
	}

	public List<CollisionArea> collisions(Rectangle collision) {
		List<CollisionArea> matches = new LinkedList<>();
		for (CollisionArea area : collisions) {
			if (area.area.overlaps(collision)) {
				matches.add(area);
			}
		}
		return matches;
	}

	public void transition(Direction direction) {
		if (connections.containsKey(direction)) {
			world.transitionTo(connections.get(direction), direction);
		}
	}
}
