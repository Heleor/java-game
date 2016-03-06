package personal.game.input;

import com.badlogic.gdx.Gdx;


public class InputFrame {
	private static int NUM_KEYS = 256;
	
	final boolean[] keys;
	final InputFrame previous;
	
	public InputFrame(InputFrame previous) {
		keys = new boolean[NUM_KEYS];
		this.previous = previous;
		
		poll();
	}
	
	public void poll() {
		for (int i = 0; i < NUM_KEYS; i++) {
			keys[i] = keys[i] || Gdx.input.isKeyPressed(i);
		}
	}
	
	public boolean key(int key) {
		return keys[key];
	}
}
