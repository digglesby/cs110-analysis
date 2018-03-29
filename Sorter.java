import java.util.Date;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.IOException;

class Sorter{
  public ArrayList<Post> basePosts;
  public Schema schema;

  Sorter(
    ArrayList<Post> posts,
    Schema newSchema
  ) {
    basePosts = posts;
    schema = newSchema;
  }


  private void printUsage(){
    System.out.println("filter [f] | export [e] | reset | exit ");
  }

  private void printFilterUsage(){

    StringBuilder schemaOptions = new StringBuilder();

    for ( int i = 0; i < schema.names.length; i++ ){

      schemaOptions.append( schema.names[i] );
      schemaOptions.append(" | ");
    }

    schemaOptions.append("back");

    System.out.println(schemaOptions.toString());
  }

  private void printFilterStringUsage(){
    System.out.println("contains [c] | regex [r] | back ");
  }

  private void printFilterIntUsage(){
    System.out.println("gtr_than [g] | lss_than [g] | equals [e] | back ");
  }

  private void export( String filename, Context context ){
    try{
      PrintWriter fileWriter = new PrintWriter(filename.concat(".csv") , "UTF-8");

      for ( int i = 0; i < schema.names.length; i++ ){
        fileWriter.print(schema.names[i]);
        fileWriter.print(",");
      }

      fileWriter.print("\n");

      for (Post thisPost: context.posts){

        for ( int i = 0; i < schema.names.length; i++ ){

          switch (schema.names[i]){

            case "score":
              fileWriter.print(thisPost.score);
              break;

            case "num_comments":
              fileWriter.print(thisPost.numComments);
              break;

            case "selftext":
              fileWriter.print(thisPost.selfText);
              break;

            case "thumbnail":
              fileWriter.print(thisPost.thumbnail);
              break;

            case "guilded":
              fileWriter.print(thisPost.guilded);
              break;

            case "author":
              fileWriter.print(thisPost.author);
              break;

            case "title":
              fileWriter.print(thisPost.title);
              break;

            case "created_utc":
              fileWriter.print(thisPost.timeCreated.getTime());
              break;

            case "subreddit":
              fileWriter.print(thisPost.subreddit);
              break;

            case "url":
              fileWriter.print(thisPost.url);
              break;

          }

          fileWriter.print( "," );
        }

        for (String thisComment: thisPost.comments){
          fileWriter.print( thisComment );
          fileWriter.print( "," );
        }

        fileWriter.print("\n");

      }

      fileWriter.close();
      System.out.printf("Successfully exported file %s!\n", filename.concat(".csv") );
    } catch(IOException e) {
      System.out.println(e);
    }
  }

  public void shellInterface(){
    Boolean running = true;
    Context context = new Context(basePosts);
    Scanner inputScanner = null;
    ArrayList<String> input = new ArrayList<String>();

    int schemaTarget = -1;


    inputScanner = new Scanner(System.in);
    context.printPreview();

    while (running){
      printUsage();

      input.add( inputScanner.nextLine() );

      switch (input.get(0)){
        case "reset":
          input.clear();
          context = new Context(basePosts);
          context.printPreview();
          break;

        case "filter":
        case "f":

          while (input.size() > 0){

            printFilterUsage();

            /*
            * Input loop for filter
            */
            input.add( inputScanner.nextLine() );

            switch (input.get(1)){

              case "back":
                input.clear();
                schemaTarget = -1;
                break;

              default:

                if ( schema.hasKey( input.get(1) ) ) {

                  while (input.size() > 1){

                    switch ( schema.typeOf( input.get(1) ) ){
                      case "String":
                        printFilterStringUsage();
                        break;
                      case "int":
                        printFilterIntUsage();
                        break;
                    }

                    /*
                    * Input loop for schema filter
                    */

                    input.add( inputScanner.nextLine() );

                    switch ( schema.typeOf( input.get(1) ) ){
                      case "String":

                        switch (input.get(2)){
                          case "contains":
                          case "c":

                            while (input.size() > 2){

                              System.out.println("Text:");

                              input.add( inputScanner.nextLine() );

                              if ( input.get(3) == "back" ){
                                input.remove( 3 );
                                input.remove( 2 );
                              }else{
                                context.filterString( input.get(1), "contains", input.get(3) );
                                input.clear();
                                context.printPreview();
                              }

                            }

                            break;
                          case "regex":
                          case "r":

                            while (input.size() > 2){

                              System.out.println("Valid regex:");

                              input.add( inputScanner.nextLine() );

                              if ( input.get(3) == "back" ){
                                input.remove( 3 );
                                input.remove( 2 );
                              }else{
                                context.filterString( input.get(1), "regex", input.get(3) );
                                input.clear();
                                context.printPreview();
                              }

                            }

                            break;
                        }

                        break;
                      case "int":

                        switch (input.get(2)){
                          case "gtr_than":
                          case "g":

                            while (input.size() > 2){

                              System.out.println("Number:");

                              input.add( inputScanner.nextLine() );

                              if ( input.get(3) == "back" ){
                                input.remove( 3 );
                                input.remove( 2 );
                              }else{
                                context.filterInt( input.get(1), "gtr_than", Integer.parseInt( input.get(3) ) );
                                input.clear();
                                context.printPreview();
                              }

                            }

                            break;
                          case "lss_than":
                          case "l":

                            while (input.size() > 2){

                              System.out.println("Number:");

                              input.add( inputScanner.nextLine() );

                              if ( input.get(3) == "back" ){
                                input.remove( 3 );
                                input.remove( 2 );
                              }else{
                                context.filterInt( input.get(1), "lss_than", Integer.parseInt( input.get(3) ) );
                                input.clear();
                                context.printPreview();
                              }

                            }

                            break;
                          case "equals":
                          case "e":

                            while (input.size() > 2){

                              System.out.println("Number:");

                              input.add( inputScanner.nextLine() );

                              if ( input.get(3) == "back" ){
                                input.remove( 3 );
                                input.remove( 2 );
                              }else{
                                context.filterInt( input.get(1), "equals", Integer.parseInt( input.get(3) ) );
                                input.clear();
                                context.printPreview();
                              }

                            }

                            break;
                        }

                        break;
                      default:

                        System.out.println("This is currently unsupported! ):");
                        input.remove( 1 );
                    }


                    if (input.size() > 0){
                      if ( input.get(2) == "back" ){
                        input.remove( 2 );
                        input.remove( 1 );
                      }
                    }
                  }

                } else {
                  System.out.println("Invalid input!");
                  input.remove( 1 );
                }
                break;
            }
          }

          break;
        case "export":
        case "e":

          while (input.size() > 0){
            System.out.println("Give an export filename:");
            input.add( inputScanner.nextLine() );
            export( input.get(1), context);
            input.clear();

          }

          break;
        case "exit":
          running = false;
          break;
        default:
          System.out.println("Invalid input!");
          break;
      }
    }

  }
}
