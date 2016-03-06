package world;

import static personal.game.Constants.TILE_SIZE;

import java.util.List;

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
	
	public ActiveMap(int width, int height, 
			WorldTile[][] tiles, List<CollisionArea> collisions) {
		this.tileCols = width;
		this.tileRows = height;
		this.tiles = tiles;
		this.collisions = collisions;
		
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
	
	public void render(SpriteBatch batch) {
		batch.draw(texture, 0,0, getWidth(), getHeight());
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
}
