import java.util.Date;
import java.util.ArrayList;

class Schema{
  public String[] names;
  public String[] types;
  public int size;

  Schema(int new_size){
    size = new_size;
    names = new String[size];
    types = new String[size];
  }

  public void setField( int index, String name ){
    String type = null;

    switch (name){

      case "score":
      case "num_comments":
      case "guilded":
        type = "int";
        break;

      case "selftext":
      case "thumbnail":
      case "author":
      case "title":
      case "subreddit":
      case "url":
        type = "String";
        break;

      case "created_utc":
        type = "Date";
        break;
    }

    names[index] = name;
    types[index] = type;
  }

  public String get( int index ){
    return names[index];
  }

  public String getType( int index ){
    return types[index];
  }

  public int indexOf( String name ){

    for ( int i = 0; i < names.length; i++ ){

      if (names[i].equals(name)) {
        return i;
      }
    }

    return -1;
  }

  public Boolean hasKey( String name ){

    return (indexOf(name) != -1);
  }

  public String typeOf( String name ){
    String type = null;
    int index = indexOf(name);

    if ( index != -1 ) {
      type = types[index];
    }

    return type;
  }
}
