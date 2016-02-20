package world;

import java.util.HashMap;
import java.util.Map;

import loaders.CharacterLoader;
import loaders.MapLoader;
import character.Character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class World {
	Map<String, PrototypeMap> maps;
	
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
		this.character.setAnimation("walk_r");
	}
	
	// Moves the world one frame forward.
	public void advance() {
		character.update();
		character.x++;
		
		if (character.x > currentMap.getWidth()) {
			character.x = -16;
			character.y += 16;
			if (character.y >= currentMap.getHeight()) {
				character.y = 0;
			}
		}
	}
	
	public void render(SpriteBatch batch) {
		currentMap.render(batch);
		character.draw(batch);
	}
}
