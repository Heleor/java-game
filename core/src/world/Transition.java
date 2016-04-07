package world;

import character.Character;


public class Transition {
	float cameraStartX; float cameraStartY;
	float cameraEndX; float cameraEndY;
	
	float playerStartX; float playerStartY;
	float playerEndX; float playerEndY;
	
	int currentFrame;
	int endFrame;

	float mapOffsetX;
	float mapOffsetY;
	ActiveMap nextMap;
	
	private float interpolate(float a, float b) {
		float percent = (float) currentFrame / (float) endFrame;
		return (1.0f - percent) * a + (percent) * b;
	}
	
	public void updateCamera(TrackingCamera camera) {
		float x = interpolate(cameraStartX, cameraEndX);
		float y = interpolate(cameraStartY, cameraEndY);
		if (currentFrame == endFrame) {
			x = cameraEndX - mapOffsetX;
			y = cameraEndY - mapOffsetY;
		}
		
		camera.setRawPosition(x, y);
		camera.update();
	}

	public void updateCharacter(Character character) {
		float x = interpolate(playerStartX, playerEndX);
		float y = interpolate(playerStartY, playerEndY);
		character.x = x;
		character.y = y;
		
		if (currentFrame == endFrame) {
			character.x = playerEndX - mapOffsetX;
			character.y = playerEndY - mapOffsetY;
		}
		
		character.updated();
	}
}
