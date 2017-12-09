/**
 * GuiPlayer Class
 * 
 * Class representing a human player for GUI interfaces
 */
package assignment2017;

import javax.swing.JOptionPane;

import assignment2017.codeprovided.ColumnFullException;
import assignment2017.codeprovided.Connect4GameState;
import assignment2017.codeprovided.Connect4Player;
import assignment2017.codeprovided.IllegalColumnException;

public class GuiPlayer extends Connect4Player {
  public boolean moveMade;
  private GamePanel gamePanel;

  /**
   * Constructor Initializes the instance of the class
   */
  public GuiPlayer(GamePanel gamePanel) {
    this.gamePanel = gamePanel;
  }

  /**
   * Function to get current state of instance on whether it made a move
   * 
   * @return boolean representing that state
   */
  public boolean getMoveMade() {
    return this.moveMade;
  }

  /**
   * Function that is called to allow the graphical player to move
   * 
   * @param gameState Connect4GameState representing the current game state
   * @param col integer representing the column number in which to make a move
   */
  public void makeMove(Connect4GameState gameState, int col) {
    try {
      gameState.move(col);
      moveMade = true;
    } catch (IllegalColumnException | ColumnFullException e) {
      JOptionPane.showMessageDialog(null, "Error");
    }
    this.gamePanel.play();
  }

  @Override
  public void makeMove(Connect4GameState gameState) {}
}
