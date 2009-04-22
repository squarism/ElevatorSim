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
			cars[shaft] = new Car(p, 25.0f, 25.0f, 50.0f, shaft);
			cars[shaft].createPath(shaftPoints);
		}
		
		System.out.println("[floor][shaft]:[" + doors.length + "][" + doors[0].length + "]");
		
	}
	// door = new Door(this);
	// door.setX(65.0f);
	// door.setY(75.0f);
	// door.setHeight(50.0f);
	// door.setWidth(50.0f);
	//
	// drawables.add(door);

	public void operateDoor(int destinationFloor, int shaft) {
		for (int i = 0; i < doors.length; i++) {

			for (int j = 0; j < doors[i].length; j++) {

				Door door = doors[i][j];
				if (door.shaft == shaft && door.floor == destinationFloor) {
					door.operate();
				}
				/*
				float dl = door.x - door.width / 2;
				float dr = door.x + door.width / 2;
				float du = door.y - door.height / 2;
				float db = door.y + door.height / 2;

				if (dl < mouseX && mouseX < dr && du < mouseY && mouseY < db) {
					//door.operate();
					building.cars[0].setDestinationFloor(door.floor);
				}
				*/

			}

		}

		
	}

	/*
	public void doDoors() {
		for (int i = 0; i < cars.length; i++) {
			if (cars[i].destinationFloor == cars[i].floor)
		}
		//building.operateDoor(destinationFloor, shaft);
		
	}
	*/

	public void callCar(int shaft, int floor) {
		
		// if we're on that floor, we don't need to do anything
		// TODO: maybe open doors.
		if (cars[shaft].floor != floor) {
		
			cars[shaft].setDestinationFloor(floor);
			System.out.println("SET DEST" + floor);
			//cars[shaft].operate();
			
			int closeOnFloor = cars[shaft].floor;
			
			// close previous floor's door
			if (doors[closeOnFloor][shaft].state == Door.OPEN) {
				doors[closeOnFloor][shaft].operate();
			}
	
			//building.operateDoor(destinationFloor, shaft);
		
		}
		
	}

	public void update() {
		for (int shaft = 0; shaft < cars.length; shaft++) {
			
			// we need to go somewhere
			if (cars[shaft].destinationFloor != cars[shaft].floor && !cars[shaft].isMoving()) {
				
				// System.out.println("WE NEED TO GO SOMEWHERE ON SHAFT" + shaft);
				
				int floor = cars[shaft].floor;
				
				if (doors[floor][shaft].isReady()) {
					System.out.println("so operate:" + shaft);
					cars[shaft].operate();
				}
				
			}
			
			
			
			if (cars[shaft].arrived) {
				System.out.println("WE'RE HERE!");
				if (cars[shaft].destinationFloor == cars[shaft].floor) {
					int currentFloor = cars[shaft].floor;
					
					// open door, we're arrived
					if (doors[currentFloor][shaft].state == Door.CLOSED) {
						doors[currentFloor][shaft].operate();
					}

					
					//operateDoor(currentFloor, shaft);
					cars[shaft].setArrived(false);
				}
				
			} else {
				
			}
		}
	}

}
