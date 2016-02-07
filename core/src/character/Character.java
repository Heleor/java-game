package character;

import java.util.Map;

import personal.game.graphics.Animation;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Character {
	final Map<String, Animation> animations;
	
	Animation current;
	
	public float x;
	public float y;
	
	public Character(Map<String, Animation> animations) {
		this.animations = animations;
		this.current = animations.entrySet().iterator().next().getValue();
		
		x = 0;
		y = 0;
	}
	
	public void setAnimation(String animation) {
		current = animations.get(animation);
		current.start();
	}
	
	public void update() {
		current.update();
	}
	
	public void draw(SpriteBatch batch) {
		TextureRegion region = current.current();
		batch.draw(region,x,y);
	}
}
