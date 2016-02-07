package personal.game.graphics;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * libgdx has an Animation as well, but having flip support and repeat 
 * support is nice. Maybe implement correctly in the future.
 */
public class Animation {
	final int phase;
	final boolean repeat;
	final List<TextureRegion> frames;
	
	final int max;
	
	boolean started = false;
	int frame = 0;
	
	public Animation(List<TextureRegion> frames, int phase, boolean repeat, boolean flipX, boolean flipY) {
		this.phase = phase;
		this.repeat = repeat;
		
		this.frames = new ArrayList<>();
		for (TextureRegion r : frames) {
			TextureRegion _r = new TextureRegion(r);
			_r.flip(flipX, flipY);
			this.frames.add(_r);
		}
		
		this.max = phase * frames.size() - 1;
	}
	
	public void start() {
		frame = 0;
		started = true;
	}
	
	public void stop() {
		started = false;
	}
	
	/**
	 * Increments the animation frame by one. 
	 * If repeat is set, will wrap around. If not set,
	 * will stop at final frame.
	 */
	public void update() {
		if (started) {
			if (frame < max || repeat) {
				frame++;
				if (frame == max && repeat) {
					frame = 0;
				}
			}
		}
	}
	
	public TextureRegion current() {
		return frames.get(frame / phase);
	}
}
