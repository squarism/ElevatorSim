import java.util.ArrayList;

public class DrawingManager {

	ArrayList gameObjects = new ArrayList();
	ArrayList animationObjects = new ArrayList();

	private static DrawingManager instance = null;

	protected DrawingManager() {
		// Exists only to defeat instantiation.
	}

	public static DrawingManager getInstance() {
		if (instance == null) {
			instance = new DrawingManager();
		}
		return instance;
	}

	public void addAnimation(Object o) {
		// System.out.println("ADDED ANIM:" + o);
		animationObjects.add(o);
	}

	public void addGameObject(Object o) {
		// System.out.println("ADDED GAMEOBJECT:" + o);
		gameObjects.add(o);
	}

}