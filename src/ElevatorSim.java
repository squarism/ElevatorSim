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
	
	String message = new String("DEBUG");

	public void setup() {
		size(400, 200, P3D);
		noSmooth();
		pixelFont = loadFont("Monaco-9.vlw");
		textFont(pixelFont,12);
		
		ortho(-width/2, width/2, -height/2, height/2, -100, 100);

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
		// building.doDoors();
	}

	public void draw() {
		background(20, 50, 150); // nice blue bg
		lights();
		
		//translate(-width/2, height/4, 0);

		//translate(0, -height/4, 0);
		//rotateX(-PI/6); 
		//rotateY(PI/6);

		

		//translate(width/2, 0, 0);
		  // Orange point light on the right
		  pointLight(150, 100, 0, // Color
		             200, -150, 0); // Position

		  // Blue directional light from the left
		  directionalLight(0, 102, 255, // Color
		                   width/2, height/2, 0); // The x-, y-, z-axis direction

		  // Yellow spotlight from the front
		  spotLight(255.0f, 255.0f, 109.0f, // Color
		            0.0f, 40.0f, 200.0f, // Position
		            0.0f, -0.5f, -0.5f, // Direction
		            PI / 2, 2); // Angle, concentration
		
		// draw floors

		float dh = building.doorHeight / 2;
		float dw = building.doorWidth / 2;

		Point2d[] bottomFloorPoints = building.shaftPoints[0];
		Point2d[] topFloorPoints = building.shaftPoints[building.shaftPoints.length - 1];
		float bottomFloorY = bottomFloorPoints[0].y + building.doorHeight / 2;
		float topFloorY = topFloorPoints[0].y - building.doorHeight / 2;
		
		
		for (int floors=0; floors < building.floors; floors++) {
			fill(120 * floors,255,255, 45);
			beginShape(QUADS);
			Point2d[] floorPoints = building.shaftPoints[floors];
			float floorY = floorPoints[0].y;
			
			
			vertex(0, floorY + building.doorHeight/2, -100);
			vertex(width, floorY + building.doorHeight/2, -100);
			vertex(width, floorY + building.doorHeight/2, 100);
			vertex(0, floorY + building.doorHeight/2, 100);

			endShape();
		}
		
		for (int shafts=0; shafts < building.shafts; shafts++) {
			
			// left wall
			beginShape(QUADS);
			Point2d[] shaftPoints = building.shaftPoints[shafts];
			float shaftX = shaftPoints[shafts].x;				
			vertex(shaftX - dw, topFloorY, -dw * 2);
			vertex(shaftX - dw, topFloorY, 0);
			vertex(shaftX - dw, bottomFloorY, 0);
			vertex(shaftX - dw, bottomFloorY, -dw * 2);
			endShape();

			// right wall
			beginShape(QUADS);
			vertex(shaftX + dw, topFloorY, -dw * 2);
			vertex(shaftX + dw, topFloorY, 0);
			vertex(shaftX + dw, bottomFloorY, 0);
			vertex(shaftX + dw, bottomFloorY, -dw * 2);
			endShape();
			
			// back wall
			beginShape(QUADS);
			vertex(shaftX - dw, topFloorY, -dw * 2);
			vertex(shaftX + dw, topFloorY, -dw * 2);
			vertex(shaftX + dw, bottomFloorY, -dw * 2);
			vertex(shaftX - dw, bottomFloorY, -dw * 2);
			endShape();
			
			
			
		}
		
		
		
		//text("DEBUG: " + message, 0,11);


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
			for (int i = 0; i < building.doors.length; i++) {

				for (int j = 0; j < building.doors[i].length; j++) {
					Door door = building.doors[i][j];
					door.operate();
				}
				
			}
		}
		
		if (key == '1') building.callCar(0,0);
		if (key == '2') building.callCar(0,1);
		if (key == '3') building.callCar(0,2);
		if (key == '4')	building.callCar(1,0);
		if (key == '5') building.callCar(1,1);
		if (key == '6') building.callCar(1,2);
		
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

					// door.operate();

					//building.callCar(0, door.floor);
				}

			}

		}
	}

	public void mouseDragged() {
		int threshold = (int) map(mouseX, 0, width, 0, 255);
		
		if (mousePressed && mouseButton == LEFT) {
//			rotateX(mouseX);
//			rotateY(mouseY);
			//camera(-100, -100, 0, mouseX, mouseY, 0, 0, 0, 0);

//			beginCamera();
//			camera();
//			rotateY(mouseY/100);
//			endCamera();
			
//			camera(width/2.0f, height/2.0f, (height/2.0f) / tan(PI*60.0f / 360.0f), width/2.0f, height/2.0f, 0, 0, 1, 0);
			camera(mouseX, mouseY, (height/2.0f) / tan(PI*60.0f / 360.0f), width/2.0f, height/2.0f, 0, 0, 1, 0);			
		}
	}

	public static void main(String args[]) {
		PApplet.main(new String[] { "ElevatorSim" });
	}

}