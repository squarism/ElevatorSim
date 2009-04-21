import megamu.shapetween.Shaper;
import megamu.shapetween.Tween;
import processing.core.PApplet;

// Thread Class // 

public class DoorAnimation extends Animation implements Runnable {
	Thread thread;

	// int pauseTime; // Time to wait between loops
	float level;
	float levelBase;

	float x;
	float y;
	float w;
	float h;
	float size;

	int direction;

	
	float drawX;
	float drawY;
	float drawH;
	float drawW;
	
	Point2d[] leftDoor = new Point2d[4];
	Point2d[] rightDoor = new Point2d[4];
	
	float elapsed;

	PApplet p;
	
	Tween ani;

	public DoorAnimation(PApplet p, int x, int y, int w, int h, int direction) {
		this.p = p;
		p.registerDispose(this);

		this.level = w;
		this.levelBase = this.level;
		this.done = false;
		this.direction = direction;

		this.h = h;
		this.w = w;
		// this.size = 32; // this is the size
		this.x = x;
		this.y = y;
		
		leftDoor[0] = new Point2d(x-w/2, y-h/2);	// top left
		leftDoor[1] = new Point2d(x, y-h/2);		// top right - moves
		leftDoor[2] = new Point2d(x, y+h/2);		// bottom right - moves
		leftDoor[3] = new Point2d(x-w/2, y+h/2);	// bottom left
		
		rightDoor[0] = new Point2d(x, y-h/2);		// top left - moves
		rightDoor[1] = new Point2d(x+w/2, y-h/2);	// top right
		rightDoor[2] = new Point2d(x+w/2, y+h/2);	// bottom right
		rightDoor[3] = new Point2d(x, y+h/2);		// bottom left - moves
		
		
		
	}

	// draw does not contain the elapsed because it's not the update really
	// store it for run()
	public void draw() {
		//System.out.println("row animation draw()");

		//p.fill(255, 255, 255, (level / levelBase) * 255);

		/*
		if (direction == 0) {
			drawX = x - ((h / 2) * ((level - levelBase) / levelBase));
		} else {
			drawX = x + ((h / 2) * ((level - levelBase) / levelBase));
		}*/

		
		// not absolute, used for both doors, offset of sorts
		
		// TODO: MOVE TO UPDATE FOO!
		float leftDoorEdge;
		float rightDoorEdge;
		if(direction == 0) {
			leftDoorEdge = p.lerp(leftDoor[1].x, leftDoor[0].x, ani.position());
			rightDoorEdge = p.lerp(rightDoor[0].x, rightDoor[1].x, ani.position());			
		} else {
			leftDoorEdge = p.lerp(leftDoor[0].x, leftDoor[1].x, ani.position());
			rightDoorEdge = p.lerp(rightDoor[1].x, rightDoor[0].x, ani.position());
		}
		
		p.fill(255);
		p.stroke(0);
		p.quad(leftDoor[0].x, leftDoor[0].y, 
				leftDoorEdge, leftDoor[1].y, 
				leftDoorEdge, leftDoor[2].y, 
				leftDoor[3].x, leftDoor[3].y);
		
		
		p.quad(rightDoorEdge, rightDoor[0].y, 
				rightDoor[1].x, rightDoor[1].y, 
				rightDoor[2].x, rightDoor[2].y, 
				rightDoorEdge, rightDoor[3].y);
		
		
		
		
		
		/*
		if (direction == 0) {
			doorEdge = p.lerp(x, x - w/2, ani.position());

		} else {
			doorEdge = p.lerp(x - w/2, x, ani.position());
			System.out.println(doorEdge);
		}

		if (direction == 0) {
			drawX = x - w/2;
		} else {
			drawX = w - doorEdge;
		}
		drawY = y - h/2;
		
		
		p.rectMode(p.CORNER);
		
		if (direction == 0) {
			p.rect(drawX, drawY, x-doorEdge, h);
		} else {
			p.rect(drawX-doorEdge, drawY, doorEdge, h);
		}*/
		
		p.fill(255,125,0, ani.position());

		//p.text(level, drawX, drawY + drawH + h / 2);
	}

	public void update(float elapsed) {
		// System.out.println("update() elapsed:" + elapsed + " level:" + level);
		this.elapsed = elapsed;
	}

	public void start() {
		super.done = false;
		ani = new Tween(p, 1, Tween.SECONDS, Shaper.COSINE);
		thread = new Thread(this);
		// boomCreated();
		thread.start();
		super.started = true;
	}

	public void run() {
		// do something threaded here

		/*
		//System.out.println("in run()");
		while (this.level > 1) {
			// level-- plus a little extra for lost time between draws
			this.level = this.level - 1 - this.elapsed;
			try {
				// add random waiting to make each boom different
				Thread.sleep((int) p.random(1, 10));
			} catch (InterruptedException e) {
				// do nothing, to the horror of good coding
			}
		}*/
		while (ani.isTweening()) {
			
		}

		// while loop finished, level is 0
		super.done = true;
		super.started = false;
		// boomDestroyed(id);
	}

	public void stop() {
		thread = null;
	}

	// this will magically be called by the parent once the user hits stop
	// this functionality hasn't been tested heavily so if it doesn't work, file
	// a bug
	public void dispose() {
		stop();
	}
}
