package character;

import java.util.Map;

import personal.game.graphics.Animation;
import personal.game.input.InputFrame;

import com.badlogic.gdx.Input;
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
		Animation next = animations.get(animation);
		if (current == next) {
			return;
		}
		current = next;
		current.start();
	}
	
	public void update(InputFrame input) {
		if (input.key(Input.Keys.LEFT)) {
			x--;
			setAnimation("walk_l");
		}
		if (input.key(Input.Keys.RIGHT)) {
			x++;
			setAnimation("walk_r");
		}
		if (input.key(Input.Keys.UP)) {
			y++;
			setAnimation("walk_u");
		}
		if (input.key(Input.Keys.DOWN)) {
			y--;
			setAnimation("walk_d");
		}
		
		
		current.update();
	}
	
	public void draw(SpriteBatch batch) {
		TextureRegion region = current.current();
		batch.draw(region,x,y);
	}
}
