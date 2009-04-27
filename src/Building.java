import processing.core.PApplet;


/* Building is brains, not just structure
 * manages objects inside itself
 * does not manage game
 * ElevatorSim main class manages game (as much as possible)
 */
public class Building {

	int floors;
	int shafts;
	PApplet p;

	public Point2d shaftPoints[][];
	public Point2d spawnPoints[];
	public Point3d peoplePaths[][][];
	
	public Door doors[][];
	public Car cars[];
	public Person people[];
	
	int buildingWidth = 400;
	int buildingHeight = 200;
	

	float doorHeight = 25.0f;
	float doorWidth = 25.0f;
	float personHeight = 20.0f;
	float personWidth = 15.0f;

	public Building(PApplet p, int floors, int shafts) {
		this.p = p;
		
		this.floors = floors;
		this.shafts = shafts;

		
		// where doors go
		shaftPoints = new Point2d[floors][shafts];
		doors = new Door[floors][shafts];
		cars = new Car[shafts];
		
		
		float xSpacing = buildingWidth / (shafts + 1);
		float ySpacing = buildingHeight / (floors + 1);
		
		/* CREATE STRUCTURE AND DOORS */
		for (int floor=0; floor < floors; floor++) {
			for (int shaft=0; shaft < shafts; shaft++) {
				shaftPoints[floor][shaft] = new Point2d((shaft + 1) * xSpacing, (floors - floor) * ySpacing);
				Door door = new Door(p, shaftPoints[floor][shaft].x, shaftPoints[floor][shaft].y, doorHeight, doorWidth, floor, shaft);
				doors[floor][shaft] = door;
			}
		}
		
		/* CREATE CARS */
		for (int shaft=0; shaft < shafts; shaft++) {
			cars[shaft] = new Car(p, 25.0f, 25.0f, 50.0f, shaft);
			cars[shaft].createPath(shaftPoints);
		}
		
		/* CREATE SPAWN POINTS */
		spawnPoints = new Point2d[floors * shafts];

		int s = 0;
		for (int floor=0; floor < floors; floor++) {
//			for (int shaft=0; shaft < shafts; shaft++) {
	
				// left/right for spawn point on each side of floor

					spawnPoints[s] = new Point2d(0.0f, (floors - floor) * ySpacing);
					s++;
					System.out.println(s);

					spawnPoints[s] = new Point2d(buildingWidth, (floors - floor) * ySpacing);
					

//			}
			
			s++;
			
			System.out.println(s);
		}
		
		/* CREATE PATHS */
		peoplePaths = new Point3d[floors][shafts][3];
		int sp = 0;
		for (int floor=0; floor < floors; floor++) {
			for (int shaft=0; shaft < shafts; shaft++) {
				
				// spawn point
				
				
				
				float x = spawnPoints[sp].x;
				float y = spawnPoints[sp].y;
				float z = 0.0f;
				peoplePaths[floor][shaft][0] = new Point3d(x, y, z);
				
//				peoplePaths[floor][shaft][0].x = spawnPoints[sp++].x;
//				peoplePaths[floor][shaft][0].y = spawnPoints[sp++].y;
//				peoplePaths[floor][shaft][0].z = 0.0f;

				// in front of elevator

				x = shaftPoints[floor][shaft].x;
				y = shaftPoints[floor][shaft].y;
				z = 0.0f;
				peoplePaths[floor][shaft][1] = new Point3d(x, y, z);
				
//				peoplePaths[floor][shaft][1].x = shaftPoints[floor][shaft].x;
//				peoplePaths[floor][shaft][1].y = shaftPoints[floor][shaft].y;
//				peoplePaths[floor][shaft][1].z = 0.0f;

				// inside elevator
				x = shaftPoints[floor][shaft].x;
				y = shaftPoints[floor][shaft].y;
				z = -doorWidth;
				peoplePaths[floor][shaft][2] = new Point3d(x, y, z);
				
//				peoplePaths[floor][shaft][2].x = shaftPoints[floor][shaft].x;
//				peoplePaths[floor][shaft][2].y = shaftPoints[floor][shaft].y;
//				peoplePaths[floor][shaft][2].z = -doorWidth;
				
				
				sp++;
				
			}
		}

		
		
		
		/* CREATE PEOPLE */
		people = new Person[2];
		people[0] = new Man(p, 15.0f, 15.0f, personWidth, personHeight, 1);
		people[1] = new Man(p, buildingWidth-15.0f, shaftPoints[0][1].y, personWidth, personHeight, 2);
		
		
		System.out.println("[floor][shaft]:[" + doors.length + "][" + doors[0].length + "]");
		
	}

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
		
		/* UPDATE CARS */
		for (int shaft = 0; shaft < cars.length; shaft++) {
			
			// car needs to go somewhere
			if (cars[shaft].destinationFloor != cars[shaft].floor && !cars[shaft].isMoving()) {
				
				// System.out.println("WE NEED TO GO SOMEWHERE ON SHAFT" + shaft);
				
				int floor = cars[shaft].floor;
				
				if (doors[floor][shaft].isReady()) {
					System.out.println("so operate:" + shaft);
					cars[shaft].operate();
				}
				
			}

			// car has arrived
			if (cars[shaft].arrived) {
				System.out.println("WE'RE HERE!");
				if (cars[shaft].destinationFloor == cars[shaft].floor) {
					int currentFloor = cars[shaft].floor;
					
					// open door, we're arrived
					if (doors[currentFloor][shaft].state == Door.CLOSED) {
						doors[currentFloor][shaft].operate();
					}

					cars[shaft].setArrived(false);
				}
				
			}
			
		}
		
		
		/* UPDATE PEOPLE */
		
		
	}

}
