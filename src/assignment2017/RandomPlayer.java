/**
 * RandomPlayer class
 * 
 * Class representing a random computer player for connect4
 */
package assignment2017;

import assignment2017.codeprovided.ColumnFullException;
import assignment2017.codeprovided.Connect4GameState;
import assignment2017.codeprovided.Connect4Player;
import assignment2017.codeprovided.IllegalColumnException;

public class RandomPlayer extends Connect4Player {

  /**
   * Function that allows the computer to make a move
   * 
   * @param gameState Connect4GameState represents the game state which the computer will make it's
   *        move within
   */
  @Override
  public void makeMove(Connect4GameState gameState) {
    boolean madeMove = false; // Used for repetition in case something goes wrong
    while (!madeMove) {
      try {
        int col = (int) (Math.random() * 7); // Number from 0 to 6
        while (gameState.isColumnFull(col)) { // make sure that this column isn't full
          col = (int) (Math.random() * 7);
        }
        gameState.move(col); // Make the move
        madeMove = true;
        System.out.println("Computer dropped counter in column " + col);
      } catch (IllegalColumnException e) {
        System.out.println("The computer attempted a column that's invalid.");
      } catch (ColumnFullException e) {
        System.out.println("The computer attempted a full column. Let's try this again.");
      }
    }
  }
}
