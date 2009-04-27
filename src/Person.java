import megamu.shapetween.Tween;
import processing.core.PApplet;
import processing.core.PImage;

public class Person {
	
	int id;
	int sourceFloor;
	int destinationFloor;
	int shaft;
	int floor;
	
	float x;
	float y;
	float width;
	float height;
	// no depth, sprite
	// float depth;
	

	boolean moving;
	boolean arrived;

	private PApplet p;

	Point2d[] path;
	Tween ani;
	
	public Person(PApplet p, float x, float y, float width, float height) {
		
		this.p = p;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
	}
	
}
