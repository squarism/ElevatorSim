import processing.core.PApplet;
import processing.core.PImage;


public class Man extends Person implements Drawable {

	PApplet p;
	PImage img;
	int id;
	
	public Man(PApplet p, float x, float y, float width, float height, int id) {
		super(p, x, y, width, height);
		this.p = p;
		this.id = id;
		this.img = p.loadImage("man-hat.gif");
	}

	public void draw() {
		p.image(img, x, y - height/2);
	}

	public void update(float elapsed) {
		// TODO Auto-generated method stub
		
	}
	
	

}
