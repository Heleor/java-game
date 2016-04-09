package editor.tileset;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.beans.DesignMode;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import editor.Constants;

public class TileProperties extends JPanel {
	Tile selectedTile = null;

	JTextField name = new JTextField(10);
	JLabel preview = new JLabel();
	JLabel tileLocation = new JLabel();
	JCheckBox passable = new JCheckBox();
	
	public TileProperties() {
		setPreferredSize(new Dimension(Constants.WIDTH / 2, Constants.HEIGHT / 2));
		setBackground(new Color(0.8f,0.8f,1.0f));
		
		JLabel label = new JLabel("Picture: ", JLabel.TRAILING);
		label.setLabelFor(preview);
		add(label);
		preview.setIcon(new ImageIcon());
		add(preview);
		
		label = new JLabel("Name: ", JLabel.TRAILING);
		label.setLabelFor(name);
		add(label);
		add(name);
		
		label = new JLabel("Tile: ", JLabel.TRAILING);
		label.setLabelFor(tileLocation);
		add(label);
		tileLocation.setText("unset");
		add(tileLocation);
		
		label = new JLabel("Passable: ", JLabel.TRAILING);
		label.setLabelFor(passable);
		add(label);
		add(passable);
	}
}
