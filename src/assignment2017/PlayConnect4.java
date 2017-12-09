/**
 * PlayConnect4 Class
 * 
 * Class that defines instances of all other classes and links them together in one game
 */
package assignment2017;

import assignment2017.codeprovided.Connect4GameState;
import assignment2017.codeprovided.Connect4Player;

public class PlayConnect4 {
  // Where all the magic takes place
  public static void main(String[] args) {
   /* if (args.length != 0) { // In case no args were given
      switch (args[0]) {
        case "-nogui":
          // Game State
          Connect4GameState gameState = new MyGameState();

          // Players
          Connect4Player red = new RandomPlayer();
          Connect4Player yellow = new KeyboardPlayer();

          // Console Display interface
          Connect4ConsoleDisplay display = new Connect4ConsoleDisplay(gameState);

          // Game
          Connect4 game = new Connect4(gameState, red, yellow, display, false);

          // Let the fun begin
          game.play();
          break;
        case "-gui":*/
          // Game State
          Connect4GameState gameState2 = new MyGameState();

          // GUI Display interface
          Connect4GuiDisplay display2 = new Connect4GuiDisplay(gameState2);

          // Game
          Connect4 game2 = new Connect4(gameState2, display2, true);

          // Let the fun begin
          game2.play();
       /*   break;
        default:
          System.out.println("Unsupported argument: " + args[0]);
          showUsageInfo();
      }
    } else {
      System.out.println("No arguments given");
      showUsageInfo();
    }*/
  }

  /**
   * Function to show how to use program
   */
  public static void showUsageInfo() {
    System.out.println("Connect4 Usage: (One of the following arguments must be supplied)");
    System.out.println("\t-gui\tRun Game in graphical display mode");
    System.out.println("\t-nogui\tRun Game in console display mode");
    System.out.println("Sample Usage:\n\tjava PlayConnect4 -nogui");
  }
}
