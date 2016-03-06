package character;

import java.util.Map;

import personal.game.graphics.Animation;
import personal.game.input.InputFrame;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Character {
	final Map<String, Animation> animations;
	
	enum Direction { UP, DOWN, LEFT, RIGHT };
	
	Animation current;
	Direction facing;
	
	public float x;
	public float y;
	
	public Character(Map<String, Animation> animations) {
		this.animations = animations;
		this.current = animations.entrySet().iterator().next().getValue();
		
		x = 0;
		y = 0;
		facing = Direction.DOWN;
	}
	
	public void changeAnimation(String animation) {
		Animation next = animations.get(animation);
		if (current == next) {
			return;
		}
		current = next;
		current.restart();
	}
	
	public void update(InputFrame input) {
		InputFrame prevInput = input.previous();
		
		// Shortcut methods.
		boolean pl = prevInput.key(Input.Keys.LEFT);
		boolean l = input.key(Input.Keys.LEFT);
		boolean pr = prevInput.key(Input.Keys.RIGHT);
		boolean r = input.key(Input.Keys.RIGHT);
		boolean pu = prevInput.key(Input.Keys.UP);
		boolean u = input.key(Input.Keys.UP);
		boolean pd = prevInput.key(Input.Keys.DOWN);
		boolean d = input.key(Input.Keys.DOWN);
		
		// Cancel out opposite presses.
		if (l && r) { l = false; r = false; }
		if (pl && pr) { pl = false; pr = false; }
		if (u && d) { u = false; d = false; }
		if (pu && pd) { pu = false; pd = false; }
		
		// Update positions based on current frame.
		// ... This isn't even Java.
		if (u) { y++; }
		if (d) { y--; }
		if (r) { x++; }
		if (l) { x--; }
		
		// Animation changes if the previous direction is no longer
		// being traveled *and* we're no longer moving.
		if (!(u || d || r || l)) { 
			// No longer moving.
			current.stop();
		} else {
			boolean sameDir = false;
			sameDir |= facing == Direction.UP && u;
			sameDir |= facing == Direction.DOWN && d;
			sameDir |= facing == Direction.LEFT && l;
			sameDir |= facing == Direction.RIGHT && r;
			if (sameDir) {
				// Continue the current animation.
			} else {
				if (u) {
					facing = Direction.UP;
					changeAnimation("walk_u");
				} else if (d) {
					facing = Direction.DOWN;
					changeAnimation("walk_d");
				} else if (l) {
					facing = Direction.LEFT;
					changeAnimation("walk_l");
				} else if (r) {
					facing = Direction.RIGHT;
					changeAnimation("walk_r");
				}
			}
			current.start();
		}
		
		current.update();
	}
	
	public void draw(SpriteBatch batch) {
		TextureRegion region = current.current();
		batch.draw(region,x,y);
	}
}
