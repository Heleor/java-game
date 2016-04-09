package editor.tileset;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;

import editor.Constants;

public class SpritesheetChooser extends JPanel {
	JFileChooser imageChooser;
	
	String imageName = null;
	BufferedImage spritesheetBI;
	
	JLabel imagePreview;
	JLabel imageFilename;
	
	public SpritesheetChooser() {
		setLayout(new BorderLayout());
		setMinimumSize(new Dimension(Constants.WIDTH / 2, Constants.HEIGHT));
		
		imageChooser = new JFileChooser();
		imageChooser.setCurrentDirectory(new File(Constants.DEFAULT_DIR));
		imageChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		imageChooser.setFileFilter(new FileFilter() {
			@Override
			public String getDescription() {
				return "PNG Images";
			}
			
			@Override
			public boolean accept(File f) {
				return f.getName().endsWith(".png");
			}
		});
		
		imagePreview = new JLabel(); 
		add(imagePreview, BorderLayout.CENTER);
		
		imageFilename = new JLabel(); 
		imageFilename.setHorizontalTextPosition(SwingConstants.CENTER);
		imageFilename.setBackground(Color.WHITE);
		add(imageFilename, BorderLayout.PAGE_START);
		
		JButton button = new JButton();
		button.setText("Choose...");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chooseImage();
			}
		});
		add(button, BorderLayout.PAGE_END);
	}
	
	private void chooseImage() {
		int r = imageChooser.showOpenDialog(this);
		
		if (r == JFileChooser.APPROVE_OPTION) {
			 File f = imageChooser.getSelectedFile();
			 try {
				 spritesheetBI = ImageIO.read(f);
			 } catch (IOException e) {
				 e.printStackTrace();
				 return;
			 }
			 imagePreview.setIcon(new ImageIcon(spritesheetBI));
			 imageName = f.getName();
			 imageFilename.setText(imageName);
			 imageFilename.validate();
		} else {
			return;
		}
	}
}
