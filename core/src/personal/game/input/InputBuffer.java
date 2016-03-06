package personal.game.input;

public class InputBuffer {
	InputFrame current;
	
	public InputBuffer() {
		current = new InputFrame(null);
	}
	
	public void poll() {
		current.poll();
	}
	
	public InputFrame update() {
		InputTracker.singleton.addFrame(current);
		current = new InputFrame(current);
		
		return current;
	}
}
