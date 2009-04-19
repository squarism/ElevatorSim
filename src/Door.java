import processing.core.PApplet;


public class Door implements Drawable {
	
	PApplet p;
	boolean open;
	float x;
	float y;
	float height;
	float width;

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

	
	public Door(PApplet p) {
		this.p = p;
		open = false;
	}


	public void draw() {
		if (!open) {
			p.fill(255);
			p.stroke(0);
			// left door
			p.rect(x-width/2, y-height/2, width/2, height);
			// right door
			p.rect(x, y-height/2, width/2, height);
			p.stroke(120,0,0);
			p.line(x, y-height/2, x, y+height/2);

			Util.nesText(p, 60, 180, "This is cool NES shit.");
		}
	}
	
	public boolean isOpen() {
		return open;
	}


	public void operateDoors() {
		open = !open;
	}


	public boolean isDone() {
		return false;
	}


	public void update(float elapsed) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
