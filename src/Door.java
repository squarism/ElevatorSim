import processing.core.PApplet;

public class Door implements Drawable {

	PApplet p;
	String state = new String("null");
	float x;
	float y;
	float height;
	float width;
	DoorAnimation doorAnimation;
	int floor;
	int shaft;
	
	final static String CLOSING = "CLOSING";
	final static String OPENING = "OPENING";
	final static String CLOSED = "CLOSED";
	final static String OPENED = "OPENED";
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public Door(PApplet p, float x, float y, float width, float height, int floor, int shaft) {
		this.p = p;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.floor = floor;
		this.shaft = shaft;
		state = CLOSED;
		
		// this is a dummy thing because initial state is closed
		doorAnimation = new DoorAnimation(p, (int) x, (int) y, (int) width,
				(int) height, 1);
		
		// initially, there is no animation so set to done as a hack
		doorAnimation.done = true;
	}

	public void draw() {
		p.rectMode(p.CORNER);
		p.noFill();
		p.stroke(0);
		//p.rect(x - width / 2, y - height / 2, width, height);
		p.pushMatrix();
		p.translate(x, y);
		p.beginShape(p.QUADS);
			p.vertex(-width/2, -height/2, 0);
			p.vertex(width/2, -height/2, 0);
			p.vertex(width/2, height/2, 0);
			p.vertex(-width/2, height/2, 0);
		p.endShape();
		p.popMatrix();

		Util.nesText(p, x, y - 20, state + " floor:" + floor);

		// only draw static door if door is closed and animation is done 
		if (!state.equals(OPENED) && doorAnimation.isDone()) {
			p.fill(255);
			p.stroke(0);
			// left door
			p.rect(x - width / 2, y - height / 2, width / 2, height);
			// right door
			p.rect(x, y - height / 2, width / 2, height);
			p.stroke(120, 0, 0);
			p.line(x, y - height / 2, x, y + height / 2);
		}
	}

	public void operate() {
		if (state.equals(OPENED)) {
			doorAnimation = new DoorAnimation(p, (int) x, (int) y, (int) width,
					(int) height, 1);
		} else {
			doorAnimation = new DoorAnimation(p, (int) x, (int) y, (int) width,
					(int) height, 0);
		}
		doorAnimation.start();
		DrawingManager.getInstance().addAnimation(doorAnimation);
	}

	public boolean isDone() {
		return false;
	}

	public void update(float elapsed) {
		if (state.equals(CLOSED) && doorAnimation.isStarted() && !doorAnimation.isDone()) {
			state = this.OPENING;
		} else if (state.equals(OPENED) && doorAnimation.isStarted() && !doorAnimation.isDone()) {
			state = this.CLOSING;
		} else if (doorAnimation.isStarted() == false && doorAnimation.isDone()
				&& doorAnimation.direction == 1) {
			state = this.CLOSED;
		} else if (doorAnimation.isStarted() == false && doorAnimation.isDone()
				&& doorAnimation.direction == 0) {
			state = this.OPENED;
		}

	}

}
