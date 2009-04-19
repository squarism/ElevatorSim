import processing.core.PApplet;


public class Util {
	
	public static void nesText(PApplet p, float x, float y, String text) {
		p.fill(0);
		p.text(text, x, y);
		p.fill(255);
		p.text(text, x-1, y-1);
	}

}
