/**
 * RandomGuiPlayer Class
 * 
 * Class representing a random player for GUI version of game
 */
package assignment2017;

import assignment2017.codeprovided.ColumnFullException;
import assignment2017.codeprovided.Connect4GameState;
import assignment2017.codeprovided.Connect4Player;
import assignment2017.codeprovided.IllegalColumnException;

public class RandomGuiPlayer extends Connect4Player {

  private GamePanel gamePanel;

  /**
   * Constructor Initializes the instance of the class
   * 
   * @param gamePanel representing panel containing game
   */
  public RandomGuiPlayer(GamePanel gamePanel) {
    this.gamePanel = gamePanel;
  }

  /**
   * Function that is called to allow the graphical player to move
   * 
   * @param gameState representing connect4gamestate
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
      } catch (IllegalColumnException e) {
        System.out.println("The computer attempted a column that's invalid.");
      } catch (ColumnFullException e) {
        System.out.println("The computer attempted a full column. Let's try this again.");
      }
    }
    this.gamePanel.play();
  }
}
