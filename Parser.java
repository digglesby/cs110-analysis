import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.util.Date;

class Parser{
  private FileInputStream inputStream;
  private Scanner newLineScanner;

  Parser( FileInputStream newInputStream ){
    inputStream = newInputStream;

    newLineScanner = new Scanner(inputStream);
    newLineScanner.useDelimiter("\\n");

  }

  private Schema getHeaderSchema ( String line ){
    int i = 0;
    Scanner commaScanner = new Scanner(line);
    String entry = null;
    String type = null;
    Schema schema = new Schema(10);

    commaScanner.useDelimiter(",");

    while (commaScanner.hasNext()){

      entry = commaScanner.next();

      if (i < 10){

        schema.setField( i , entry );
      }

      i++;
    }

    return schema;
  }

  private Post parseLine( String line, Schema schema ){
    Scanner commaScanner = new Scanner(line);
    Post post = null;
    String entry;
    int i = 0;

    int score = 0;
    int numComments = 0;
    int guilded = 0;
    String selfText = null;
    String thumbnail = null;
    String author = null;
    String title = null;
    String subreddit = null;
    String url = null;
    Date timeCreated = null;
    ArrayList<String> comments = new ArrayList<String>();

    commaScanner.useDelimiter(",");

    while (commaScanner.hasNext()){

      entry = commaScanner.next();

      if (entry.length() > 0){

        if (i < 10){

          switch (schema.get(i)){

            case "score":
              score = Integer.parseInt(entry);
              break;

            case "num_comments":
              numComments = Integer.parseInt(entry);
              break;

            case "selftext":
              selfText = entry;
              break;

            case "thumbnail":
              thumbnail = entry;
              break;

            case "guilded":
              guilded = Integer.parseInt(entry);
              break;

            case "author":
              author = entry;
              break;

            case "title":
              title = entry;
              break;

            case "created_utc":
              timeCreated = new Date(Long.parseLong(entry));
              break;

            case "subreddit":
              subreddit = entry;
              break;

            case "url":
              url = entry;
              break;

          }
        }else{

          comments.add(entry);
        }

      }

      i++;
    }

    post = new Post(
      score,
      numComments,
      selfText,
      thumbnail,
      guilded,
      author,
      title,
      timeCreated,
      subreddit,
      url,
      comments
    );

    return post;
  }

  public Schema parseSchema() {
    Schema schema = null;

    schema = getHeaderSchema(newLineScanner.next());

    return schema;
  }

  public ArrayList<Post> parse( Schema schema ){
    ArrayList<Post> posts = new ArrayList<Post>();

    while (newLineScanner.hasNext()){
      posts.add( parseLine( newLineScanner.next(), schema ) );
    }

    return posts;
  }
}
