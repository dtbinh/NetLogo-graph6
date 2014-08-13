import org.nlogo.api.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Graph6Command extends DefaultCommand {
  // take one number as input, report a list
  public Syntax getSyntax() {
    return Syntax.commandSyntax(
        new int[]{Syntax.TurtlesetType(), Syntax.LinksetType(), Syntax.StringType()}
    );
  }

  public void perform(Argument args[], Context context)
      throws ExtensionException {
    AgentSet turtles, links;
    String fileName;
    try {
      turtles = args[0].getAgentSet();
      links = args[1].getAgentSet();
      fileName = args[2].getString();
    } catch (LogoException e) {
      throw new ExtensionException(e.getMessage());
    }
    
    String adjMatrix = createAdjMatrix(turtles,links);
    String encodedGraph = Graph6.encodeGraph(turtles.count(),adjMatrix);

    WriteFile.write(fileName, encodedGraph);
  }
  
  /**
   * This method creates a String representing the adjacency adjMatrix
   * for the given links
  */
  public String createAdjMatrix (AgentSet turtles, AgentSet links){
    String result="";
    double[][] adjMatrix = new double[turtles.count()][turtles.count()];
    HashMap <Long,Integer> map = new HashMap <Long,Integer>();
    
    int nodeId=0;
    for (Agent turtle : turtles.agents()){
      map.put(new Long(turtle.id()), new Integer(nodeId));
      nodeId++;
    }
    
    for (Agent link : links.agents()){
      int end1 = map.get(new Long(((Link) link).end1().id()));
      int end2 = map.get(new Long(((Link) link).end2().id()));
      adjMatrix[end1][end2] = 1.0;
      adjMatrix[end2][end1] = 1.0;
    }
    
    // Write the upper triangle of the adjacency matrix
    // by columns
    for (int i = 1, k = 1; k < adjMatrix.length; i++, k++) {
      for (int j = 0; j < i; j++) {
	if (adjMatrix[j][i] != 0) result += "1";
	else result += "0";
      }
    }
    
    return result;
  }

  static class WriteFile{
    public static void write (String fileName, String content)
        throws ExtensionException{
      try{
        File file = new File(fileName);

        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);
        bw.close();
      } catch (IOException e){
        throw new ExtensionException(e.getMessage());
      }
    }
  }
}
