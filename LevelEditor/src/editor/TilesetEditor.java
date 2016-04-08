package editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

public class TilesetEditor extends JFrame {
	private static final String DEFAULT_DIR = "C:\\programming\\game2\\java-game\\android\\assets";
	private static final String TILESET_EXT = ".tileset.json";
	
	private static final int TILE_SIZE = 16;
	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;
	
	class Tile {
		JLabel label;
	}
	
	private List<Tile> tiles;
	private JPanel tileList;
	private JPanel tileInfo;
	
	private JPanel spritesheet;
	
	private Tile current = null;
	
	private void generateTileList() {
		tileList = new JPanel();
		tileList.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT / 2));
		tileList.setBackground(Color.RED);
	}
	
	private void generateTileInfo() {
		tileInfo = new JPanel();
		tileInfo.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT / 2));
		tileInfo.setBackground(Color.BLUE);
	}
	
	private JPanel generateLeftArea() {
		generateTileList();
		generateTileInfo();
		
		JPanel leftArea = new JPanel();
		leftArea.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT));
		leftArea.add(tileList, BorderLayout.PAGE_START);
		leftArea.add(tileInfo, BorderLayout.PAGE_END);
		return leftArea;
	}
	
	private JPanel generateRightArea() {
		spritesheet = new JPanel();
		spritesheet.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT));
		spritesheet.setBackground(Color.GREEN);
		return spritesheet;
	}
	
	boolean unsaved = false;
	
	File tileset = null;
	JFileChooser fc;
	
	private void newTileset() {
		tileset = null;
	}
	
	private void open() {
		int r = fc.showOpenDialog(this);
		
		if (r == JFileChooser.APPROVE_OPTION) {
			tileset = fc.getSelectedFile();
			System.out.println("Opened " + tileset);
		}
	}
	
	private void saveAs() {
		int r = fc.showSaveDialog(this);
		
		if (r == JFileChooser.APPROVE_OPTION) {
			tileset = fc.getSelectedFile();
		} else {
			return;
		}
		performSave();
	}
	
	private void save() {
		if (tileset == null) {
			int r = fc.showSaveDialog(this);
			
			if (r == JFileChooser.APPROVE_OPTION) {
				tileset = fc.getSelectedFile();
			} else {
				return;
			}
		}
		performSave();
	}
	
	private void performSave() {
		System.out.println("Saved " + tileset);
		// save file to tileset
	}
	
	private JMenuBar generateMenu() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		
		JMenuItem n = new JMenuItem("New");
		n.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
		n.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				newTileset();
			}
		});
		fileMenu.add(n);
		
		JMenuItem open = new JMenuItem("Open...");
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
		open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				open();
			}
		});
		fileMenu.add(open);
		
		JMenuItem save = new JMenuItem("Save...");
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		fileMenu.add(save);

		JMenuItem saveAs = new JMenuItem("Save As...");
		saveAs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveAs();
			}
		});
		fileMenu.add(saveAs);

		
		menuBar.add(fileMenu);
		return menuBar;
	}
	
	public TilesetEditor() {
		setTitle("Tileset editor");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JPanel content = new JPanel(new BorderLayout());
		setContentPane(content);
		
		setJMenuBar(generateMenu());
		
		add(generateLeftArea(), BorderLayout.LINE_START);
		add(generateRightArea(), BorderLayout.LINE_END);
		
		fc = new JFileChooser();
		fc.setCurrentDirectory(new File(DEFAULT_DIR));
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setFileFilter(new FileFilter() {
			@Override
			public String getDescription() {
				return "Tilesets";
			}
			
			@Override
			public boolean accept(File f) {
				return f.getName().endsWith(TILESET_EXT);
			}
		});
	}
}
