package character;

import java.util.List;
import java.util.Map;

import personal.game.graphics.Animation;
import personal.game.input.InputFrame;
import world.CollisionArea;
import world.World;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Character {
	final World world;
	final Map<String, Animation> animations;
	
	enum Direction { UP, DOWN, LEFT, RIGHT, NONE };
	
	Animation current;
	Direction facing;
	
	public float x;
	public float y;
	
	Rectangle collision;
	
	public Character(World world, Map<String, Animation> animations) {
		this.world = world;
		this.animations = animations;
		this.current = animations.entrySet().iterator().next().getValue();
		
		x = 0;
		y = 0;
		facing = Direction.NONE;
		collision = new Rectangle(x+2,y,12,8);
		
		updated();
	}
	
	private void updated() {
		current.update();
		
		collision.x = x + 2;
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
		
		boolean collides = false;

		// Update positions based on current frame.
		// ... This isn't even Java.
		if (u) { if (collides(x, y+1)) { collides = true; } else { y++; }}
		if (d) { if (collides(x, y-1)) { collides = true; } else { y--; }}
		if (r) { if (collides(x+1, y)) { collides = true; } else { x++; }}
		if (l) { if (collides(x-1, y)) { collides = true; } else { x--; }}
		
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
			
			if (collides) {
				if (!sameDir) {
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
			} else {
				if (!sameDir) {
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
			}

			current.start();
		}
		
		updated();
	}
	
	private boolean collides(float x, float y) {
		Rectangle test = new Rectangle(collision);
		test.x = x + 2; test.y = y;
		List<CollisionArea> matches = world.collisions(test);
		for (CollisionArea c : matches) {
			if (!c.passable) {
				return true;
			}
		}
		return false;
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
