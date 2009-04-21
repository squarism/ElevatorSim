import java.util.ArrayList;

public class DrawingManager {
	
	ArrayList gameObjects = new ArrayList();
	
   private static DrawingManager instance = null;
   protected DrawingManager() {
      // Exists only to defeat instantiation.
   }
   public static DrawingManager getInstance() {
      if(instance == null) {
         instance = new DrawingManager();
      }
      return instance;
   }
   
   public void add(Object o) {
	   gameObjects.add(o);
   }
   
}