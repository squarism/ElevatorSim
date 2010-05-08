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
	final static String OPEN = "OPEN";
	
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

		Util.nesText(p, x - 80, y - 20, "State:" + state + " Floor:" + floor);
		Util.nesText(p, x - 80, y - 40, doorAnimation.isStarted() + " " + doorAnimation.isDone());

		// only draw static door if door is closed and animation is done 
		if (!state.equals(OPEN) && doorAnimation.isDone()) {
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
		if (state.equals(OPEN)) {
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
		}
		if (state.equals(OPEN) && doorAnimation.isStarted() && !doorAnimation.isDone()) {
			state = this.CLOSING;
		}
		if (state == this.CLOSING && doorAnimation.isDone()
				&& doorAnimation.direction == 1) {
			state = this.CLOSED;
		}
		if (state == this.OPENING && doorAnimation.isDone()
				&& doorAnimation.direction == 0) {
			state = this.OPEN;
		}

	}

	public boolean isReady() {
		if (state.equals(CLOSING) || state.equals(OPENING) || state.equals(OPEN)) {
			return false;
		} else {
			return true;
		}
	}

}
