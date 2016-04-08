package character;

import static personal.game.Constants.TILE_SIZE;

import java.util.Map;

import personal.game.Direction;
import personal.game.graphics.Animation;
import personal.game.input.InputFrame;
import world.World;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Character {
	
	final World world;
	final Map<String, Animation> animations;
	
	Animation current;
	Direction facing;
	
	public float x;
	public float y;
	
	public int moveFrame;
	
	public Rectangle collision;
	public Rectangle fullBounds;
	
	public Character(World world, Map<String, Animation> animations) {
		this.world = world;
		this.animations = animations;
		this.current = animations.entrySet().iterator().next().getValue();
		
		x = 0;
		y = 0;
		moveFrame = 0;
		facing = Direction.NONE;
		collision = new Rectangle(x+3,y,10,8);
		fullBounds = new Rectangle(x,y,TILE_SIZE, TILE_SIZE);
		
		updated();
	}
	
	public void updated() {
		current.update();
		
		collision.x = x + 2;
		collision.y = y;
		fullBounds.x = x;
		fullBounds.y = y;
	}
	
	public void changeAnimation(String animation) {
		Animation next = animations.get(animation);
		if (next == null) {
			return;
		}
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
		if (u) { if (!world.tryMoving(x, y+1)) { collides = true; } else { y++; }}
		if (d) { if (!world.tryMoving(x, y-1)) { collides = true; } else { y--; }}
		if (r) { if (!world.tryMoving(x+1, y)) { collides = true; } else { x++; }}
		if (l) { if (!world.tryMoving(x-1, y)) { collides = true; } else { x--; }}
		
		boolean moving = u || d || r || l;
		
		// Animation changes if the previous direction is no longer
		// being traveled *and* we're no longer moving.
		boolean sameDir = false;
		sameDir |= facing == Direction.UP && u;
		sameDir |= facing == Direction.DOWN && d;
		sameDir |= facing == Direction.LEFT && l;
		sameDir |= facing == Direction.RIGHT && r;
		
		// If we were previously moving in a direction and we still are,
		// we want to preserve it.
		String direction = facing.letter;
		if (!sameDir) {
			if (u) { direction = "u"; facing = Direction.UP; } 
			else if (d) { direction = "d"; facing = Direction.DOWN; }
			else if (l) { direction = "l"; facing = Direction.LEFT; }
			else if (r) { direction = "r"; facing = Direction.RIGHT; }
		}
		
		String animation = null;
		if (collides && !world.frozen() && moving) {
			animation = "push";
		} else {
			animation = "walk";
		}
		
		changeAnimation(animation + "_" + direction);
		if (moving) {
			moveFrame++;
			current.start();
		} else {
			moveFrame = 0;
			current.stop();
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
