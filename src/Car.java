import java.util.ArrayList;

import megamu.shapetween.Shaper;
import megamu.shapetween.Tween;
import processing.core.PApplet;

public class Car implements Drawable {
	
	float x;
	float y;
	float width;
	float height;
	float depth;
	int people;
	boolean goingUp;
	Button[] floorButtons;
	private PApplet p;
	int shaft;
	int floor;
	int destinationFloor;
	Point2d[] path;
	Tween ani;
	ArrayList floorQueue = new ArrayList();
	private boolean done;
	
	public Car(PApplet p, float width, float height, float depth, int shaft) {
		this.p = p;
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.shaft = shaft;
	}
	
	
	public void draw() {
//		System.out.println("in car draw");
		//p.rectMode(p.CENTER);
		p.fill(25,45, 255);
		//p.rect(x, y, width, height);
		p.pushMatrix();
		p.translate(x, y);
		p.stroke(120);
		p.beginShape(p.QUADS);
			
			// left side
			p.fill(20);
			p.vertex(-width/2, -height/2, -1);
			p.fill(0);
			p.vertex(-width/2, -height/2, -depth/2);
			p.vertex(-width/2, height/2, -depth/2);
			p.fill(20);
			p.vertex(-width/2, height/2, -1);
			
			// back side
			p.fill(0);
			p.vertex(-width/2, -height/2, -depth/2);
			p.vertex(width/2, -height/2, -depth/2);
			p.vertex(width/2, height/2, -depth/2);
			p.vertex(-width/2, height/2, -depth/2);
			
			// right side
			p.fill(0);
			p.vertex(width/2, -height/2, -depth/2);
			p.fill(20);
			p.vertex(width/2, -height/2, -1);
			p.vertex(width/2, height/2, -1);
			p.fill(0);
			p.vertex(width/2, height/2, -depth/2);

			// bottom
			p.fill(20, 0, 0);		// red carpet
			p.vertex(-width/2, height/2, -depth/2);
			p.vertex(width/2, height/2, -depth/2);
			p.fill(220, 40, 20);		// red carpet
			p.vertex(width/2, height/2, -1);
			p.vertex(-width/2, height/2, -1);

			// top
			p.fill(255, 255, 255, 50);		// yellow lights?
			p.vertex(-width/2, -height/2, -depth/2);
			p.vertex(width/2, -height/2, -depth/2);
			p.vertex(width/2, -height/2, -1);
			p.vertex(-width/2, -height/2, -1);

		p.endShape();
		p.popMatrix();
	}
	
	public void setDestinationFloor(int destinationFloor) {
		this.destinationFloor = destinationFloor;
		System.out.println(floorQueue);
		if (! floorQueue.contains(destinationFloor)) {
			floorQueue.add(destinationFloor);
		}
		operate();
	}
		
	public boolean isFull() {
		return true;
	}
	
	public boolean isGoingUp() {
		return true;
	}
	
	public void addPerson(int id) {
		
	}
	
	public void removePerson(int id) {
		
	}

	// is the car done moving?
	public boolean isDone() {
		return false;
	}

	public void update(float elapsed) {
		
		if (ani != null) {
			//System.out.println(ani.time());
		}
		
		if (ani != null && ani.isTweening()) {
			int destinationFloor = ((Integer)floorQueue.get(0)).intValue();
			y = p.lerp(y, path[destinationFloor].y, ani.position());
		} 
		if (ani != null && ani.time() == 1) {
			done = true;
			System.out.println("DONE!" + floorQueue.indexOf(destinationFloor));
			//floorQueue.remove(0);
		}
		
	}


	public void createPath(Point2d[][] shaftPoints) {
		path = new Point2d[shaftPoints.length];
		
		for (int floor=0; floor < shaftPoints.length; floor++) {
			for (int shaft=0; shaft < shaftPoints[floor].length; shaft++) {
				if (this.shaft == shaft) {
					path[floor] = shaftPoints[floor][shaft];
				}		
			}
		}
		
		// set initial x,y to lowest point in path, should be bottom floor.
		//this.x = path[shaftPoints.length - 1].x;
		//this.y = path[shaftPoints.length - 1].y;
		this.x = path[0].x;
		this.y = path[0].y;

	}


	public void operate() {
		done = false;
		ani = new Tween(p, 1, Tween.SECONDS, Shaper.COSINE);
	}

}
