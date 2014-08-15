import org.nlogo.api.DefaultClassManager;
import org.nlogo.api.PrimitiveManager;

public class Graph6Extension extends DefaultClassManager {
  public void load(PrimitiveManager primitiveManager) {
    primitiveManager.addPrimitive("save-graph6", new Graph6Command());
  }
}
