import processing.core.PApplet;

public class Door implements Drawable {

	PApplet p;
	boolean open;
	boolean opening;
	boolean closing;
	String state = new String("null");
	float x;
	float y;
	float height;
	float width;
	DoorAnimation doorAnimation;

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

	public Door(PApplet p, float x, float y, float width, float height) {
		this.p = p;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		open = false;
		doorAnimation = new DoorAnimation(p, (int) x, (int) y, (int) width, (int) height, 1);
	}

	public void draw() {
		p.rectMode(p.CORNER);
		p.noFill();
		p.stroke(0);
		p.rect(x - width / 2, y - height / 2, width, height);

		Util.nesText(p, x, y-20, state);
		
		if (open) {
			

		} else {
			
			
			
			if (doorAnimation.isDone()) {
				p.fill(255);
				p.stroke(0);
				// left door
				p.rect(x - width / 2, y - height / 2, width / 2, height);
				// right door
				p.rect(x, y - height / 2, width / 2, height);
				p.stroke(120, 0, 0);
				p.line(x, y - height / 2, x, y + height / 2);

				//Util.nesText(p, x, y, "closed");
			}
		}
	}

	public boolean isOpen() {
		return open;
	}

	public void operateDoors() {
		//open = !open;
		if (open) {
			doorAnimation = new DoorAnimation(p, (int) x, (int) y, (int) width, (int) height, 1);
		} else {
			doorAnimation = new DoorAnimation(p, (int) x, (int) y, (int) width, (int) height, 0);
		}
		doorAnimation.start();
		AnimationManager.getInstance().add(doorAnimation);
	}

	public boolean isDone() {
		return false;
	}

	public void update(float elapsed) {
		if (!open && doorAnimation.isStarted() && !doorAnimation.isDone()) {
			opening = true;
			state = "OPENING!";
		} else if (open && doorAnimation.isStarted() && !doorAnimation.isDone()) {
			closing = true;
			state = "CLOSING!";
		} else if (doorAnimation.isStarted() == false && doorAnimation.isDone() && doorAnimation.direction == 1) {
			open = false;
			state = "CLOSED!";
		} else if (doorAnimation.isStarted() == false && doorAnimation.isDone() && doorAnimation.direction == 0) {
			open = true;
			state = "OPEN!";
		}

	}

}
