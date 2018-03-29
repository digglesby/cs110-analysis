import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Context{
  public ArrayList<Post> posts = new ArrayList<Post>();
  public Map<String, Integer> minValues = new HashMap<String, Integer>();
  public Map<String, Integer> maxValues = new HashMap<String, Integer>();
  public Map<String, Integer> totalValues = new HashMap<String, Integer>();

  private String fixURL(String url){
    return url;
  }

  public void printPreview(){
    System.out.printf("%s Posts with %s Comments\n",
      posts.size(),
      totalValues.get("numComments")
    );

  }

  public void minMaxStat( String key, int value ){

    if (minValues.containsKey( key )){
      if ( value < minValues.get(key) ){
        minValues.put( key, value );
      }
    }else{
      minValues.put( key, value );
    }

    if (maxValues.containsKey( key )){
      if ( value < maxValues.get(key) ){
        maxValues.put( key, value );
      }
    }else{
      maxValues.put( key, value );
    }

    if (totalValues.containsKey( key ) ){
      totalValues.put( key, totalValues.get(key) + value );
    }else{
      totalValues.put( key, value );
    }
  }

  public void calculateStats(){
    minValues = new HashMap<String, Integer>();
    maxValues = new HashMap<String, Integer>();
    totalValues = new HashMap<String, Integer>();

    for (Post thisPost: posts){

      minMaxStat( "numComments", thisPost.numComments );
      minMaxStat( "score", thisPost.score );
      minMaxStat( "guilded", thisPost.guilded );
      minMaxStat( "titleLength", thisPost.title.length() );
    }
  }

  public void filterString( String key, String mode, String value ){
    ArrayList<Post> newPosts = new ArrayList<Post>();
    String sValue = null;
    Pattern regex = null;
    Matcher matcher = null;

    if (mode.equals("regex")){
      regex = Pattern.compile( value );
    }


    for (Post thisPost: posts){
      switch (key){

        case "selftext":
          sValue = thisPost.selfText;
          break;

        case "thumbnail":
          sValue = thisPost.thumbnail;
          break;

        case "author":
          sValue = thisPost.author;
          break;

        case "title":
          sValue = thisPost.title;
          break;

        case "subreddit":
          sValue = thisPost.subreddit;
          break;

        case "url":
          sValue = thisPost.url;
          break;
      }

      if (sValue != null){
        switch (mode){
          case "contains":
            if (sValue.contains( value )){

              newPosts.add( thisPost );
            }

            break;
          case "regex":

            matcher = regex.matcher( sValue );

            if ( matcher.find() ){
              newPosts.add( thisPost );
            }

            break;
        }
      }

    }

    posts = newPosts;
    calculateStats();
  }

  public void filterInt( String key, String mode, int value ){
    ArrayList<Post> newPosts = new ArrayList<Post>();
    int iValue = -1;


    for (Post thisPost: posts){
      switch (key){

        case "score":
          iValue = thisPost.score;
          break;

        case "num_comments":
          iValue = thisPost.numComments;
          break;

        case "guilded":
          iValue = thisPost.guilded;
          break;

        case "created_utc":
          iValue = (int) thisPost.timeCreated.getTime();
          break;

      }

      switch (mode){
        case "gtr_than":
          if (iValue > value){
            newPosts.add( thisPost );
          }
          break;
        case "lss_than":
          if (iValue < value){
            newPosts.add( thisPost );
          }
          break;
        case "equals":
          if (iValue == value){
            newPosts.add( thisPost );
          }
          break;
      }
    }

    posts = newPosts;
    calculateStats();
  }

  Context(
    ArrayList<Post> new_posts
  ) {
    posts = new_posts;
    calculateStats();
  }
}
