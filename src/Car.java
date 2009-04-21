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
	Point2d[] path;
	
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
	
	public void update() {
		
	}
	
	public void moveToFloor(int floor) {
		
	}
	
	public void openDoor() {
		
	}
	
	public void closeDoor() {
		
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

	public boolean isDone() {
		// TODO Auto-generated method stub
		return false;
	}

	public void update(float elapsed) {
		// TODO Auto-generated method stub
		
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
		this.x = path[shaftPoints.length - 1].x;
		this.y = path[shaftPoints.length - 1].y;
	}


	public void operate() {
		// TODO Auto-generated method stub
		
	}

}
