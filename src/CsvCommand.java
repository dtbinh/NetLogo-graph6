import org.nlogo.api.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class CsvCommand extends DefaultCommand {
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
    
    WriteFile.open(fileName);
    writeFile(turtles,links);
    WriteFile.close();
  }
  
  private void writeFile(AgentSet turtles,AgentSet links)
      throws ExtensionException{
    HashMap <Long,Integer> map = new HashMap <Long,Integer>();
    
    int nodeId=0;
    for (Agent turtle : turtles.agents()){
      map.put(new Long(turtle.id()), new Integer(nodeId));
      nodeId++;
    }
    
    WriteFile.write(turtles.count()+"\n");
    
    for (Agent link : links.agents()){
      int end1 = map.get(new Long(((Link) link).end1().id()));
      int end2 = map.get(new Long(((Link) link).end2().id()));
      
      WriteFile.write(end1+","+end2+"\n");
    }
  }

  static class WriteFile{
    static File file;
    static BufferedWriter bw;
    
    public static void open (String fileName)
        throws ExtensionException{
      try{
        file = new File(fileName);

        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        bw = new BufferedWriter(fw);
      } catch (IOException e){
        throw new ExtensionException(e.getMessage());
      }
    }
    
    public static void write (String content)
	throws ExtensionException{
      try{
	bw.write(content);
      } catch (IOException e){
        throw new ExtensionException(e.getMessage());
      }
    }
    
    public static void close ()
	throws ExtensionException{
      try{
	bw.close();
      } catch (IOException e){
        throw new ExtensionException(e.getMessage());
      }
    }
  }
}
