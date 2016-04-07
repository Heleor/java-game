package world;

import com.badlogic.gdx.math.Rectangle;

public class Connection {
	final Rectangle area;
	final String newMap;
	
	public Connection(Rectangle area, String newMap) {
		this.area = area;
		this.newMap = newMap;
	}
	
	public Rectangle getArea() {
		return area;
	}
}
