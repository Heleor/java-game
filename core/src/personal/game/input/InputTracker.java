package personal.game.input;

import java.util.LinkedList;
import java.util.List;

public class InputTracker {
	public static InputTracker singleton = new InputTracker();
	
	public List<InputFrame> history;
	
	private InputTracker() {
		history = new LinkedList<>();
	}
	
	public void addFrame(InputFrame frame) {
		history.add(frame);
	}
}
