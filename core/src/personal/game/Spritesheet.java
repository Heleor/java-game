package personal.game;

import java.util.Random;

import com.badlogic.gdx.graphics.Pixmap;

/**
 * Loads and parses a spritesheet.
 */
public class Spritesheet {
	public class Tile {
		final int x,y,w,h;
		
		public Tile(int x, int y, int w, int h) {
			this.x = x; this.y = y; this.w = w; this.h = h;
		}
		
		public void drawTo(Pixmap target, int x, int y) {
			target.drawPixmap(image, x, y, this.x, this.y, this.w, this.h);
		}
	}
	
	private final Pixmap image;
	private Tile[][] tiles; // tiles[row][col]
	
	// TODO: Does this change?
	private static final int paddingX = 1;
	private static final int paddingY = 1;
	
	private static final int tileWidth = 16;
	private static final int tileHeight = 16;
	
	private static final int tileCols = 24;
	private static final int tileRows = 25;
	
	public Spritesheet(Pixmap image) {
		this.image = image;
		
		populate();
	}
	
	private void populate() {
		tiles = new Tile[tileCols][tileRows];
		
		int jumpX = tileWidth + paddingX;
		int jumpY = tileHeight + paddingY;

		for (int col = 0; col < tileCols; col++) {
		for (int row = 0; row < tileRows; row++) {
			// 0 -> 1, 1-> 1 + 16 + 1, 2 -> 1 + 16 + 1 + 16 + 1
			int x = col * jumpX + paddingX;
			int y = row * jumpY + paddingY;
			tiles[col][row] = new Tile(x,y,tileWidth,tileHeight);
		}}
	}
	
	public Tile get(int col, int row) {
		return tiles[col][row];
	}
	
	// Not a very useful method.
	public Tile getRandom() {
		Random random = new Random();
		return get(random.nextInt(tileCols), random.nextInt(tileRows));
	}
}
