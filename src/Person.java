import megamu.shapetween.Shaper;
import megamu.shapetween.Tween;
import processing.core.PApplet;
import processing.core.PImage;

public class Person implements Drawable {
	
	int id;
	public int sourceFloor;
	public int destinationFloor;
	public int shaft;
//	public int floor;
	public int pathPosition;
	
	float x;
	float y;
	float z;
	float width;
	float height;
	int personSpeed = 2;
	// no depth, sprite
	// float depth;

	boolean moving;
	boolean arrived;
	String state = new String("null");

	private PApplet p;
	PImage img;

	Point3d[] path;
	Tween ani;

	final static String SPAWNED = "SPAWNED";
	final static String WAITING = "WAITING";
	final static String RIDING = "RIDING";
	final static String LEAVING = "LEAVING";

	
	
	public Person(PApplet p, float x, float y, float width, float height, int id) {
		
		this.p = p;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.state = SPAWNED;
		this.img = p.loadImage("man-hat.gif");
		this.id  = id;
	}
	
	
	public void createPath(Point3d[] sourceFloorPath, Point3d[] destinationFloorPath) {
		// person has fixed set of path points on two floors
		path = new Point3d[6];

		// loop through 2 sets of 3 points, combine them
		int pathIterator = 0;
//		for (int floor = 0; floor < sourceFloorPath.length; floor++) {
//			for (int shaft = 0; shaft < sourceFloorPath[floor].length; shaft++) {
				for (int pathPoint = 0; pathPoint < sourceFloorPath.length; pathPoint++) {
					path[pathIterator++] = sourceFloorPath[pathPoint];					
				}
//			}
//		}

//		for (int floor = 0; floor < destinationFloorPath.length; floor++) {
//			for (int shaft = 0; shaft < destinationFloorPath[floor].length; shaft++) {
				for (int pathPoint = destinationFloorPath.length - 1; pathPoint >= 0 ; pathPoint--) {
					path[pathIterator++] = destinationFloorPath[pathPoint];					
				}
//			}
//		}

		System.out.println(path);
		
		// set initial x,y to lowest point in path, should be bottom floor.
		// this.x = path[shaftPoints.length - 1].x;
		// this.y = path[shaftPoints.length - 1].y;
		this.x = path[0].x;
		this.y = path[0].y;
		this.z = path[0].z;

	}


	public void startMoving() {
		moving = true;
		pathPosition = 0;
		
		float distance = p.abs(path[0].x - path[1].x);
		
		
		ani = new Tween(p, (float)(Math.random() * personSpeed), Tween.SECONDS, Shaper.COSINE);
		
	}

	public void draw() {
		p.image(img, x, y - height/2);
	}
	
	public void update(float elapsed) {
		
		/*		
		if (ani != null) {
			System.out.println(ani.time());
		}
 		*/		
		
		if (moving == true && ani.isTweening()) {
			//int destinationFloor = ((Integer) floorQueue.get(0)).intValue();
			
			Point3d currentPoint = path[pathPosition];
			Point3d nextPoint = path[pathPosition+1];
			
			float cpx = currentPoint.x;
			float cpy = currentPoint.y;
			float cpz = currentPoint.z;
			float npx = nextPoint.x;
			float npy = nextPoint.y;
			float npz = nextPoint.z;
			
			// y = p.lerp(y, path[this.destinationFloor].y, ani.position());
			x = cpx + (ani.position() * (npx - cpx));
			y = cpy + (ani.position() * (npy - cpy));
			z = cpz + (ani.position() * (npz - cpz));
			//y = previousPoint().y + ( ani.position() * (points[currentPoint].y - previousPoint().y));
		}
		
		if (ani != null && ani.time() == 1) {
			//System.out.println("Person:" + id + " thinks they are arrived!");
			ani = null;
			moving = false;
			arrived = true;

			// System.out.println("DONE!" + floorQueue.indexOf(destinationFloor));
			// floorQueue.remove(0);
		}

		
	}


	
	
}
