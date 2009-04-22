import java.util.ArrayList;
import java.util.Iterator;

import processing.core.PApplet;
import processing.core.PFont;

public class ElevatorSim extends PApplet {

	PFont pixelFont;
	// PApplet p = super.;
	Building building;

	int now;
	int then;
	float fps;

	public void setup() {
		size(400, 200, P3D);
		noSmooth();
		pixelFont = loadFont("Monaco-9.vlw");
		textFont(pixelFont);

		building = new Building(this, 3, 2);

		// add elevator doors on every floor across every shaft to drawlist
		for (int i = 0; i < building.doors.length; i++) {

			for (int j = 0; j < building.doors[i].length; j++) {

				DrawingManager.getInstance()
						.addGameObject(building.doors[i][j]);

			}

		}

		// add cars to drawlist
		for (int i = 0; i < building.cars.length; i++) {
			DrawingManager.getInstance().addGameObject(building.cars[i]);
		}

	}
	
	public void update() {
		building.update();
		//building.doDoors();
	}

	public void draw() {
		background(20, 50, 150); // nice blue bg

		// note how much time has passed since our last loop
		then = now;
		float elapsed = 0;

		// wait for at least 1/100th of a second to pass
		while (elapsed < 1.0 / 100.0) {
			now = millis();

			// compute elapsed time in seconds
			elapsed = (now - then) / 1000.0f;
		}

		// compute frames per second by averaging over the past + current
		fps = (float) (.95 * fps + .05 / elapsed);

		// slow down if the computer can't keep up a reasonable framerate
		// by limiting the elapsed time to 1/12th of a second (12fps)
		if (elapsed > 1.0 / 12.0) {
			elapsed = (float) (1.0 / 12.0);
		}

		// this update
		update();
		
		ArrayList toRemove = new ArrayList();
		ArrayList anim = DrawingManager.getInstance().animationObjects;
		for (Iterator iterator = anim.iterator(); iterator.hasNext();) {

			Animation animation = (Animation) iterator.next();

			// System.out.println(animation.isDone());
			if (animation.isDone()) {
				// drawables.remove(d);
				toRemove.add(animation);
			} else {
				animation.update(elapsed);
				animation.draw();
			}

		}

		for (Iterator iterator = toRemove.iterator(); iterator.hasNext();) {
			Animation removeMe = (Animation) iterator.next();
			anim.remove(removeMe);
		}

		ArrayList gameObjects = DrawingManager.getInstance().gameObjects;
		for (Iterator iterator = gameObjects.iterator(); iterator.hasNext();) {
			
			Drawable drawable = (Drawable) iterator.next();
			drawable.update(elapsed);
			drawable.draw();
		
		}
	}

	public void keyPressed() {
		if (key == ' ') {
			for (int i = 0; i < building.cars.length; i++) {
				Car car = building.cars[0];
				car.operate();
			}
		}
	}

	public void mousePressed() {
		for (int i = 0; i < building.doors.length; i++) {

			for (int j = 0; j < building.doors[i].length; j++) {

				Door door = building.doors[i][j];
				float dl = door.x - door.width / 2;
				float dr = door.x + door.width / 2;
				float du = door.y - door.height / 2;
				float db = door.y + door.height / 2;

				if (dl < mouseX && mouseX < dr && du < mouseY && mouseY < db) {
					System.out.println("SETTING SFDOIHJSFO");
					
					//door.operate();
					building.callCar(0, door.floor);
					//building.cars[0].setDestinationFloor(door.floor);
					//building.cars[0].operate();
				}

			}

		}
	}

	public void mouseDragged() {
		int threshold = (int) map(mouseX, 0, width, 0, 255);
	}

	public static void main(String args[]) {
		PApplet.main(new String[] { "ElevatorSim" });
	}


}