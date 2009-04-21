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
			for (int j=0; j < building.doors[i].length; j++) {
				DrawingManager.getInstance().add(building.doors[i][j]);	
			}
		}

		// add cars to drawlist
		for (int i = 0; i < building.cars.length; i++) {
			DrawingManager.getInstance().add(building.cars[i]);
		}

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

		// slow the game down if the computer can't keep up a reasonable frame
		// rate
		// by limiting the elapsed time to 1/12th of a second (12fps)
		if (elapsed > 1.0 / 12.0)
			elapsed = (float) (1.0 / 12.0);

		ArrayList toRemove = new ArrayList();
		ArrayList drawables = DrawingManager.getInstance().gameObjects;
		for (Iterator iterator = drawables.iterator(); iterator.hasNext();) {
			Drawable d = (Drawable) iterator.next();
			// System.out.println(d.isDone());
			if (d.isDone()) {
				// drawables.remove(d);
				toRemove.add(d);
			} else {
				d.update(elapsed);
				d.draw();
			}
		}

		for (Iterator iterator = toRemove.iterator(); iterator.hasNext();) {
			Drawable removeMe = (Drawable) iterator.next();
			drawables.remove(removeMe);
		}

	}

	public void keyPressed() {
		 if (key == ' ') {
			 for(int i=0; i < building.cars.length; i++) {
				 Car car = building.cars[i];
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
					door.operate();
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