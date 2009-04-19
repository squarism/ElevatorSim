import java.util.ArrayList;

public class AnimationManager {
	
	ArrayList drawables = new ArrayList();
	
   private static AnimationManager instance = null;
   protected AnimationManager() {
      // Exists only to defeat instantiation.
   }
   public static AnimationManager getInstance() {
      if(instance == null) {
         instance = new AnimationManager();
      }
      return instance;
   }
   
   public void add(Object o) {
	   drawables.add(o);
   }
   
}