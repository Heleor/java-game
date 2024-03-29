package loaders;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import loaders.TilesetLoader.RawTileset.RawTile;
import personal.game.graphics.Spritesheet;
import personal.game.graphics.Tileset;
import personal.game.graphics.Spritesheet.Tile;
import world.WorldTile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.Json;

public class TilesetLoader {
	static class RawTileset {
		static class SpritesheetRef {
			String image;
		}
		
		SpritesheetRef spritesheet;
		
		static class RawTile {
			int c, r;
			boolean passable = true;
			int slowing = 0;
		}
		
		HashMap<String, RawTile> tiles;
	}
	
	public static Tileset load(FileHandle file) {
		Json json = new Json();
		RawTileset raw = json.fromJson(RawTileset.class, file);
		
		// This should be handled by a manager.
		Pixmap image = new Pixmap(Gdx.files.internal(raw.spritesheet.image));
		Spritesheet sheet = new Spritesheet(image);
		
		Map<String, WorldTile> tiles = new HashMap<>();
		for (Entry<String, RawTile> t : raw.tiles.entrySet()) {
			Tile _t = sheet.get(t.getValue().c, t.getValue().r);
			WorldTile wt = new WorldTile(_t);
			wt.passable = t.getValue().passable;
			wt.slowing = t.getValue().slowing;
			tiles.put(t.getKey(), wt);
		}
		
		return new Tileset(tiles);
	}
}
