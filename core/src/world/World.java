package world;

import static personal.game.Constants.TILE_SIZE;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loaders.CharacterLoader;
import loaders.MapLoader;
import personal.game.Direction;
import personal.game.input.InputFrame;
import character.Character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class World {
	static final int VIEW_WIDTH = TILE_SIZE * 10; 
	static final int VIEW_HEIGHT = TILE_SIZE * 8;
	
	Map<String, PrototypeMap> maps;
	
	SpriteBatch batch;
	ShapeRenderer shapes;
	
	TrackingCamera camera;
	ActiveMap currentMap;
	Character character;
	
	Transition transitioning;
	
	public World() {
		this.maps = new HashMap<>();
		this.batch = new SpriteBatch();
		this.shapes = new ShapeRenderer();
	}
	
	private void preloadMap(String name) {
		if (!maps.containsKey(name)) {
			PrototypeMap map = MapLoader.load(Gdx.files.internal(name + ".map.json"));
			maps.put(name, map);
		}
	}
	
	private PrototypeMap getMap(String name) {
		preloadMap(name);
		return maps.get(name);
	}
	
	public void initialize(String character, String initialMap) {
		PrototypeMap proto = getMap(initialMap);
		currentMap = proto.newMap();
		
		this.character = CharacterLoader.load(Gdx.files.internal(character + ".character.json"), this);
		this.character.changeAnimation("stand");
		this.character.x = currentMap.getWidth() / 2;
		this.character.y = TILE_SIZE * 2;
		
		this.camera = new TrackingCamera(VIEW_WIDTH, VIEW_HEIGHT);
		camera.setArea(currentMap.getArea());
	}
	
	public void transitionTo(String newMap, Direction scrollDirection) {
		Transition transition = new Transition();
		transition.nextMap = getMap(newMap).newMap();
		
		transition.playerStartX = character.x;
		transition.playerStartY = character.y;
		transition.playerEndX = character.x;
		transition.playerEndY = character.y;
		
		transition.cameraStartX = camera.x;
		transition.cameraStartY = camera.y;
		transition.cameraEndX = camera.x;
		transition.cameraEndY = camera.y;
		
		if (scrollDirection == Direction.RIGHT) {
			transition.playerEndX += TILE_SIZE;
			transition.cameraEndX += currentMap.getWidth();
			
			transition.endFrame = (int) (transition.cameraEndX - transition.cameraStartX) / 4;
			transition.mapOffsetX = currentMap.getWidth();
			transition.mapOffsetY = 0;
		} else if (scrollDirection == Direction.LEFT) {
			transition.playerEndX -= TILE_SIZE;
			transition.cameraEndX -= currentMap.getWidth();
			
			transition.endFrame = (int) (transition.cameraStartX - transition.cameraEndX) / 4;
			transition.mapOffsetX = -currentMap.getWidth();
			transition.mapOffsetY = 0;
		} else if (scrollDirection == Direction.UP) {
			transition.playerEndY += TILE_SIZE;
			transition.cameraEndY += currentMap.getHeight();
			
			transition.endFrame = (int) (transition.cameraEndY - transition.cameraStartY) / 4;
			transition.mapOffsetX = 0;
			transition.mapOffsetY = currentMap.getHeight();
		} else if (scrollDirection == Direction.DOWN) {
			transition.playerEndY -= TILE_SIZE;
			transition.cameraEndY -= currentMap.getHeight();
			
			transition.endFrame = (int) (transition.cameraStartY - transition.cameraEndY) / 4;
			
			transition.mapOffsetX = 0;
			transition.mapOffsetY = -currentMap.getHeight();
		}
		
		transition.currentFrame = 0;
		
		transitioning = transition;
	}
	
	// Moves the world one frame forward.
	public void advance(InputFrame input) {
		if (transitioning != null) {
			transitioning.currentFrame++;
			
			transitioning.updateCamera(camera);
			transitioning.updateCharacter(character);
			
			if (transitioning.currentFrame == transitioning.endFrame) {
				currentMap = transitioning.nextMap;
				transitioning = null;
			}
		} else {
			character.update(input);
			
			camera.setCenter(character.x + TILE_SIZE / 2, character.y + TILE_SIZE / 2);
			camera.update();
		}
	}
	
	public void render() {
		batch.setProjectionMatrix(camera.matrix());		
		
		batch.begin();
		currentMap.render(batch);
		if (transitioning != null) {
			transitioning.nextMap.render(batch, transitioning.mapOffsetX, transitioning.mapOffsetY);
		}
		character.draw(batch);
		batch.end();
	}
	
	public void renderCollisions() {
		shapes.setProjectionMatrix(camera.matrix());
		
		Gdx.gl.glEnable(GL20.GL_BLEND);
		shapes.begin(ShapeType.Filled);
		character.renderCollision(shapes);
		
		currentMap.renderCollision(shapes);
			
		shapes.end();
	}
	
	public List<CollisionArea> collisions(Rectangle collision) {
		return currentMap.collisions(collision);
	}
}
