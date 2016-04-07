package personal.game;

public enum Direction {
	UP("u"), DOWN("d"), LEFT("l"), RIGHT("r"), NONE("?");
	
	public final String letter;
	
	Direction(String letter) {
		this.letter = letter;
	}
}
