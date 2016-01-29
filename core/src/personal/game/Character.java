package personal.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Character {
	final Tileset images;
	
	String currentFrame;
	
	public Character(Tileset images) {
		this.images = images;
		currentFrame = "stand";
	}
	
	public void setAnimation(String animation) {
		currentFrame = animation;
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(images.tiles.get(currentFrame).getRegion(),0,0);
	}
}
