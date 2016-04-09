package editor.tileset;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import editor.Constants;

public class TileList extends JPanel {
	List<Tile> tiles;
	
	JButton newTile;
	
	public TileList() {
		setPreferredSize(new Dimension(Constants.WIDTH / 2, Constants.HEIGHT / 2));
		setBackground(Color.RED);
		
		newTile = new JButton("+");
		add(newTile);
	}
}
