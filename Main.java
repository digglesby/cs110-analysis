import java.io.*;
import java.util.ArrayList;

class Main {
  public static void main(String[] args){
    Parser parser = null;
    Sorter sorter = null;
    File dataFile = null;
    FileInputStream fileStream = null;

    Schema schema = null;
    ArrayList<Post> basePosts = null;

    try { 
      dataFile = new File("allRedditContent.csv");
      fileStream = new FileInputStream(dataFile);
      parser = new Parser(fileStream);

      schema = parser.parseSchema();
      basePosts = parser.parse( schema );

      sorter = new Sorter( basePosts, schema );
      sorter.shellInterface();

    } catch (IOException e){

    }

  }
}
