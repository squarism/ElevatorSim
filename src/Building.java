import processing.core.PApplet;

public class Building {

	int floors;
	int shafts;
	PApplet p;
	
	public Point2d shaftPoints[];
	public Door doors[];
	public Car car;
	
	float doorHeight = 25.0f;
	float doorWidth = 25.0f;
	
	
	public Building(PApplet p, int floors, int shafts) {
		this.p = p;
		
		this.floors = floors;
		this.shafts = shafts;
		car = new Car();
		
		// where doors go
		shaftPoints = new Point2d[floors * shafts];
		doors = new Door[floors * shafts];
		
		float xSpacing = p.width / (shafts + 1);
		float ySpacing = p.height / (floors + 1);
		
		int i=0;
		for (int floor=0; floor < floors; floor++) {
			for (int shaft=0; shaft < shafts; shaft++) {
				shaftPoints[i] = new Point2d((shaft + 1) * xSpacing, (floor + 1) * ySpacing);
				
				Door door = new Door(p, shaftPoints[i].x, shaftPoints[i].y, doorHeight, doorWidth);
//				door.setX();
//				door.setY();
//				door.setHeight();
//				door.setWidth();
				
				doors[i] = door;

				i++;
			}
		}
	}
	
//	door = new Door(this);
//	door.setX(65.0f);
//	door.setY(75.0f);
//	door.setHeight(50.0f);
//	door.setWidth(50.0f);
//
//	drawables.add(door);


}
