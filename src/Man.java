//import processing.core.PApplet;
//import processing.core.PImage;
//
//
//public class Man extends Person implements Drawable {
//
//	PApplet p;
//	PImage img;
//	int id;
//	
//	public Man(PApplet p, float x, float y, float width, float height, int id) {
//		super(p, x, y, width, height);
//		this.p = p;
//		this.id = id;
//		this.img = p.loadImage("man-hat.gif");
//	}
//
//	public void draw() {
//		p.image(img, x, y - height/2);
//	}
//
//	public void update(float elapsed) {
//		if (moving == true) {
//			//int destinationFloor = ((Integer) floorQueue.get(0)).intValue();
//			
//			Point3d currentPoint = path[pathPosition];
//			Point3d nextPoint = path[pathPosition+1];
//			
//			float cpx = currentPoint.x;
//			float cpy = currentPoint.y;
//			float cpz = currentPoint.z;
//			float npx = nextPoint.x;
//			float npy = nextPoint.y;
//			float npz = nextPoint.z;
//			
//			// y = p.lerp(y, path[this.destinationFloor].y, ani.position());
//			x = x + ani.position() * (npx - cpx);
//			y = y + ani.position() * (npy - cpy);
//			z = z + ani.position() * (npz - cpz);
//			//y = previousPoint().y + ( ani.position() * (points[currentPoint].y - previousPoint().y));
//		}
//		if (ani != null && ani.time() == 1) {
//			System.out.println("Person:" + id + " thinks they are arrived!");
//			ani = null;
//			moving = false;
//			arrived = true;
//
//			// System.out.println("DONE!" + floorQueue.indexOf(destinationFloor));
//			// floorQueue.remove(0);
//		}
//
//		
//	}
//	
//	
//
//}
