package world;

import static personal.game.Constants.TILE_SIZE;

import java.util.HashMap;
import java.util.Map;

import loaders.CharacterLoader;
import loaders.MapLoader;
import personal.game.input.InputFrame;
import character.Character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class World {
	static final int VIEW_WIDTH = TILE_SIZE * 10; 
	static final int VIEW_HEIGHT = TILE_SIZE * 8;
	
	Map<String, PrototypeMap> maps;
	
	TrackingCamera camera;
	ActiveMap currentMap;
	Character character;
	
	public World() {
		this.maps = new HashMap<>();
	}
	
	private PrototypeMap getMap(String name) {
		if (maps.containsKey(name)) {
			return maps.get(name);
		}
		PrototypeMap map = MapLoader.load(Gdx.files.internal(name + ".map.json"));
		maps.put(name, map);
		return map;
	}
	
	public void initialize(String character, String initialMap) {
		PrototypeMap proto = getMap(initialMap);
		currentMap = proto.newMap();
		
		this.character = CharacterLoader.load(Gdx.files.internal(character + ".character.json"));
		this.character.changeAnimation("stand");
		this.character.x = currentMap.getWidth() / 2;
		this.character.y = currentMap.getHeight() / 2;
		
		this.camera = new TrackingCamera(VIEW_WIDTH, VIEW_HEIGHT);
		camera.setArea(currentMap.getArea());
	}
	
	// Moves the world one frame forward.
	public void advance(InputFrame input) {
		character.update(input);
		camera.setCenter(character.x + TILE_SIZE / 2, character.y + TILE_SIZE / 2);
	}
	
	public void prerender(SpriteBatch batch) {
		camera.update();
		camera.position(batch);
	}
	
	public void render(SpriteBatch batch) {
		currentMap.render(batch);
		character.draw(batch);
	}
}
