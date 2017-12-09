/**
 * KeyboardPlayer Class
 * 
 * Class representing a console keyboard player
 */
package assignment2017;

import assignment2017.codeprovided.ColumnFullException;
import assignment2017.codeprovided.Connect4GameState;
import assignment2017.codeprovided.Connect4Player;
import assignment2017.codeprovided.IllegalColumnException;
import sheffield.EasyReader;

public class KeyboardPlayer extends Connect4Player {
  // Instance variables
  EasyReader consoleIp;

  /**
   * Constructor Initializes the reader used to take an input from the user
   */
  public KeyboardPlayer() {
    consoleIp = new EasyReader();
  }

  /**
   * Function that is called to allow the keyboard player to make a move
   * 
   * @param gameState Connect4 Game State
   */
  @Override
  public void makeMove(Connect4GameState gameState) {
    boolean madeMove = false; // Used for repetition in case of invalid input
    while (!madeMove) {
      try {
        System.out.println("Please enter a column number, 0 to 6 followed by return.");
        String col = consoleIp.readString(); // Taken as a string to deal with all expected input
        gameState.move(Integer.parseInt(col));
        madeMove = true; // Stop the repetition
      } catch (NumberFormatException | IllegalColumnException e) { // Out of range or not a number
        System.out.println("This is not a valid a column, try another one");
      } catch (ColumnFullException e) {
        System.out.println("This column is full, try another column.");
      }
    }
  }
}
