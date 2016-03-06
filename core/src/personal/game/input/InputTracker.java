package personal.game.input;

import java.util.ArrayList;
import java.util.List;

public class InputTracker {
	private static final int BUFFER_SIZE = 60 * 5;
	public static InputTracker singleton = new InputTracker();
	
	private List<InputFrame> previousFrame;
	public List<InputFrame> history;
	
	private InputTracker() {
		history = new ArrayList<>(BUFFER_SIZE);
	}
	
	public void addFrame(InputFrame frame) {
		history.add(frame);
		if (history.size() > BUFFER_SIZE) {
			previousFrame = history;
			history = new ArrayList<>(BUFFER_SIZE);
			drain(previousFrame);
		}
	}
	
	private void drain(List<InputFrame> frame) {
		// Throw it away for now.
	}
}
