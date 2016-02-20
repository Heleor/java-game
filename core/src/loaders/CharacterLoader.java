package loaders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import loaders.CharacterLoader.RawCharacter.RawAnimation;
import loaders.CharacterLoader.RawCharacter.RawFrame;
import personal.game.graphics.Animation;
import personal.game.graphics.Spritesheet;
import personal.game.graphics.Spritesheet.Tile;
import character.Character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Json;

public class CharacterLoader {
	static class RawCharacter {
		static class SpritesheetRef {
			String image;
		}
		
		SpritesheetRef spritesheet;
		
		static class RawFrame {
			int c, r;
			boolean flipX = false;
			boolean flipY = false;
		}
		
		HashMap<String, RawFrame> frames;
		
		static class RawAnimation {
			int phase = 1;
			boolean repeat = false;
			ArrayList<String> frames;
		}
		
		HashMap<String, RawAnimation> animations;
	}
	
	public static Character load(FileHandle file) {
		Json json = new Json();
		RawCharacter raw = json.fromJson(RawCharacter.class, file);
		
		// This should be handled by a manager.
		Pixmap image = new Pixmap(Gdx.files.internal(raw.spritesheet.image));
		Spritesheet sheet = new Spritesheet(image);
		
		Map<String, Animation> animations = new HashMap<>();
		for (Entry<String, RawAnimation> a : raw.animations.entrySet()) {
			ArrayList<TextureRegion> frames = new ArrayList<>();
			for (String f : a.getValue().frames) {
				RawFrame _f = raw.frames.get(f);
				
				Tile tile = sheet.get(_f.c, _f.r);
				
				TextureRegion region = new TextureRegion(tile.getRegion());
				region.flip(_f.flipX, _f.flipY);
				
				frames.add(region);
			}
			animations.put(
					a.getKey(), 
					new Animation(frames, a.getValue().phase,  a.getValue().repeat));
		}
		
		return new Character(animations);
	}
}
