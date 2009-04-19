import java.util.ArrayList;
import java.util.Iterator;

import processing.core.PApplet;
import processing.core.PFont;

public class ElevatorSim extends PApplet {

	PFont pixelFont;
	// PApplet p = super.;
	Door door;

	ArrayList drawables = new ArrayList();
	int now;
	int then;
	float fps;

	public void setup() {
		size(700, 300);
		pixelFont = loadFont("Monaco-9.vlw");
		textFont(pixelFont);

		door = new Door(this);
		door.setX(65.0f);
		door.setY(75.0f);
		door.setHeight(50.0f);
		door.setWidth(50.0f);

		drawables.add(door);
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
		for (Iterator iterator = drawables.iterator(); iterator.hasNext();) {
			Drawable d = (Drawable) iterator.next();
			//System.out.println(d.isDone());
			if (d.isDone()) {
				//drawables.remove(d);
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
			door.operateDoors();
			DoorAnimation doorAnimation = new DoorAnimation(this, (int) door
					.getX(), (int) door.getY(), (int) door.getWidth(),
					(int) door.getHeight(), 0);
			doorAnimation.start();
			drawables.add(doorAnimation);
		}
	}

	public void mouseDragged() {
		int threshold = (int) map(mouseX, 0, width, 0, 255);
	}

	public static void main(String args[]) {
		PApplet.main(new String[] { "ElevatorSim" });
	}

}