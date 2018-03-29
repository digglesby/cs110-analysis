import java.util.Date;
import java.util.ArrayList;

class Post{
  public int score;
  public int numComments;
  public int guilded;

  public String selfText;
  public String thumbnail;
  public String author;
  public String title;
  public String subreddit;
  public String url;

  public Date timeCreated;

  public ArrayList<String> comments;

  private String fixURL(String url){
    return url;
  }

  Post(
    int newScore,
    int newNumComments,
    String newSelfText,
    String newThumbnail,
    int newGuilded,
    String newAuthor,
    String newTitle,
    Date newTimeCreated,
    String newSubreddit,
    String newUrl,
    ArrayList<String> newComments
  ) {
    score = newScore;
    numComments = newNumComments;
    guilded = newGuilded;
    selfText = newSelfText;
    thumbnail = newThumbnail;
    author = newAuthor;
    title = newTitle;
    subreddit = newSubreddit;
    timeCreated = newTimeCreated;
    url = newUrl;
    comments = newComments;
  }
}
