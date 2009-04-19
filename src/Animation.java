public class Animation implements Drawable {
	
	boolean started;
	boolean done;
	
	public void draw() {
		System.out.println("animation draw()");
	}
	public void update(float elapsed) {
		
	}
	
	public boolean isStarted() {
		return started;
	}
	
	public boolean isDone() {
		return done;
	}
	
	public void start() {
		
	}
	
	public void stop() {
		
	}
}