package personal.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Character {
	final Tileset images;
	
	String currentFrame;
	float x;
	float y;
	
	public Character(Tileset images) {
		this.images = images;
		currentFrame = "stand";
		x = 0;
		y = 0;
	}
	
	public void setAnimation(String animation) {
		currentFrame = animation;
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(images.tiles.get(currentFrame).getRegion(),x,y);
	}
}
