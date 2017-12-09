/**
 * Connect4ConsoleDisplay class
 * 
 * Class responsible for printing out game on a console display
 */
package assignment2017;

import assignment2017.codeprovided.Connect4Displayable;
import assignment2017.codeprovided.Connect4GameState;

public class Connect4ConsoleDisplay implements Connect4Displayable {

  // Instance variables
  Connect4GameState gameState;

  /**
   * Constructor Defines an instance of Connect4ConsoleDisplay to print out a game on the console
   * screen
   * 
   * @param gameState Connect4GameState represents the current game state
   */
  public Connect4ConsoleDisplay(Connect4GameState gameState) {
    this.gameState = gameState;
  }

  /**
   * Function responsible for printing out the board
   */
  @Override
  public void displayBoard() {
    // Print out contents of board with surrounding borders and footer
    for (int i = Connect4GameState.NUM_ROWS - 1; i >= 0; i--) {
      for (int j = 0; j < Connect4GameState.NUM_COLS; j++) {
        // Edges of the board
        if (j == 0) {
          System.out.print("|");
        }
        // Contents of the board itself
        switch (this.gameState.getCounterAt(j, i)) {
          case Connect4GameState.RED: // Red
            System.out.print(" R ");
            break;
          case Connect4GameState.YELLOW: // Yellow
            System.out.print(" Y ");
            break;
          default: // Empty
            System.out.print("   ");
            break;
        }
        // Edges of the board
        if (j == Connect4GameState.NUM_COLS - 1) {
          System.out.println("|");
        }
      }
    }
    // Footer
    System.out.println(" --------------------- ");
    System.out.println("  0  1  2  3  4  5  6  ");

    // Output if the game is over
    if (gameState.gameOver()) {
      // Recognize winner
      // Either RED, YELLOW or EMPTY
      int winner = gameState.getWinner();
      switch (winner) {
        case Connect4GameState.RED: // Red
          System.out.println("R Wins");
          break;
        case Connect4GameState.YELLOW: // Yellow
          System.out.println("Y Wins");
          break;
        default: // Board is full with no winners
          System.out.println("It's a tie :)");
      }
    }
  }
}
