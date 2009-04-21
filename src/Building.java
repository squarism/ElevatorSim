import processing.core.PApplet;

public class Building {

	int floors;
	int shafts;
	PApplet p;

	public Point2d shaftPoints[][];
	public Door doors[][];
	public Car cars[];

	float doorHeight = 25.0f;
	float doorWidth = 25.0f;

	public Building(PApplet p, int floors, int shafts) {
		this.p = p;
		
		this.floors = floors;
		this.shafts = shafts;

		
		// where doors go
		shaftPoints = new Point2d[floors][shafts];
		doors = new Door[floors][shafts];
		cars = new Car[shafts];
		
		float xSpacing = p.width / (shafts + 1);
		float ySpacing = p.height / (floors + 1);
		
		
		for (int floor=0; floor < floors; floor++) {
			for (int shaft=0; shaft < shafts; shaft++) {
				shaftPoints[floor][shaft] = new Point2d((shaft + 1) * xSpacing, (floors - floor) * ySpacing);
				Door door = new Door(p, shaftPoints[floor][shaft].x, shaftPoints[floor][shaft].y, doorHeight, doorWidth, floor, shaft);
				doors[floor][shaft] = door;
			}
		}
		
		for (int shaft=0; shaft < shafts; shaft++) {
			System.out.println(shaft);
			cars[shaft] = new Car(p, 25.0f, 25.0f, 50.0f, shaft);
			cars[shaft].createPath(shaftPoints);
		}
		
	}
	// door = new Door(this);
	// door.setX(65.0f);
	// door.setY(75.0f);
	// door.setHeight(50.0f);
	// door.setWidth(50.0f);
	//
	// drawables.add(door);

}
