package character;

import java.util.Map;

import personal.game.graphics.Animation;
import personal.game.input.InputFrame;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Character {
	final Map<String, Animation> animations;
	
	enum Direction { UP, DOWN, LEFT, RIGHT, NONE };
	
	Animation current;
	Direction facing;
	
	public float x;
	public float y;
	
	Rectangle collision;
	
	public Character(Map<String, Animation> animations) {
		this.animations = animations;
		this.current = animations.entrySet().iterator().next().getValue();
		
		x = 0;
		y = 0;
		facing = Direction.NONE;
		collision = new Rectangle(x,y,16,8);
		
		updated();
	}
	
	private void updated() {
		current.update();
		
		collision.x = x;
		collision.y = y;
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
		// Shortcut methods.
		boolean l = input.key(Input.Keys.LEFT);
		boolean r = input.key(Input.Keys.RIGHT);
		boolean u = input.key(Input.Keys.UP);
		boolean d = input.key(Input.Keys.DOWN);
		
		// Cancel out opposite presses.
		if (l && r) { l = false; r = false; }
		if (u && d) { u = false; d = false; }
		
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
		
		updated();
	}
	
	public void draw(SpriteBatch batch) {
		TextureRegion region = current.current();
		batch.draw(region,x,y);
	}

	public void renderCollision(ShapeRenderer shapes) {
		shapes.setColor(0.0f, 1.0f, 0.0f, 0.5f);
		shapes.rect(collision.x,collision.y, collision.width, collision.height);
	}
}
