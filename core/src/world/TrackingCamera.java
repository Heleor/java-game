package world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;

public class TrackingCamera {
	float viewWidth; float viewHeight;
	float x; float y;
	
	OrthographicCamera camera;
	
	Rectangle bounds;
	
	public TrackingCamera(float viewWidth, float viewHeight) {
		this.viewWidth = viewWidth;
		this.viewHeight = viewHeight;
		this.x = viewWidth / 2;
		this.y = viewHeight / 2;
		
		camera = new OrthographicCamera(viewWidth, viewHeight);
		camera.position.set(x, y, 0);
		camera.update();
		
		this.bounds = new Rectangle(0,0,viewWidth, viewHeight);
	}
	
	// Bounds are the locations the camera is allowed to be.
	public void setArea(Rectangle area) {
		float left = area.x + viewWidth / 2;
		float right = area.x + area.width - viewWidth / 2;
		float bottom = area.y + viewHeight / 2;
		float top = area.y + area.height - viewHeight / 2;
		
		this.bounds = new Rectangle(left, bottom, right - left, top - bottom);
	}
	
	public void setCenter(float x, float y) {
		float newX = x;
		float newY = y;
		
		if (newX < bounds.x) {
			newX = bounds.x;
		} else if (newX > bounds.x + bounds.width) {
			newX = bounds.x + bounds.width;
		}
		
		if (newY < bounds.y) {
			newY = bounds.y;
		} else if (newY > bounds.y + bounds.height) {
			newY = bounds.y + bounds.height;
		}
		
		this.x = newX;
		this.y = newY;
		
		camera.position.set(newX,newY,0);
	}
	
	public void setRawPosition(float x, float y) {
		this.x = x;
		this.y = y;
		
		camera.position.set(x,y,0);
	}
	
	public void update() {
		camera.update();
	}
	
	public Matrix4 matrix() {
		return camera.combined;
	}
}
