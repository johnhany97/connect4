/**
 * Board Class
 * 
 * Class representing a Connect4 2D Board drawn as a JComponent
 */
package assignment2017;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

import assignment2017.codeprovided.Connect4GameState;

@SuppressWarnings("serial")
public class Board extends JComponent {

  // Instance variables
  private Connect4GameState gameState;
  private int width;
  private int height;
  private int x;
  private int y;

  /**
   * Constructor that initializes an instance of the class
   * 
   * @param gs game state representing the current game state
   * @param width intended width of the board
   * @param height intended width of the board
   * @param x origin coordinate of the board on the frame
   * @param y origin coordinate of the board on the frame
   */
  public Board(Connect4GameState gs, int width, int height, int x, int y) {
    super();
    this.gameState = gs;
    this.width = width;
    this.height = height;
    this.x = x;
    this.y = y;
  }

  /**
   * Function to set gameState
   * 
   * @param gameState Connect4GameState representing the current game state
   */
  public void setGameState(Connect4GameState gameState) {
    this.gameState = gameState;
  }

  /**
   * Function to paint the component
   * 
   * @param g Graphics
   */
  @Override
  public void paint(Graphics g) {
    // Background
    g.setColor(Color.BLUE);
    g.fillRect(this.x, this.y, this.width, this.height);
    final int stepWidth = this.width / Connect4GameState.NUM_COLS;
    final int stepHeight = this.height / Connect4GameState.NUM_ROWS;
    int row = 0;
    int col = 0;
    // Board elements
    for (int i = Connect4GameState.NUM_ROWS - 1; i >= 0; i--) {
      col = 0;
      for (int j = 0; j < Connect4GameState.NUM_COLS; j++) {
        switch (this.gameState.getCounterAt(j, i)) {
          case Connect4GameState.RED:
            g.setColor(Color.RED);
            break;
          case Connect4GameState.YELLOW:
            g.setColor(Color.YELLOW);
            break;
          default:
            g.setColor(Color.WHITE);
            break;
        }
        g.fillOval(col * stepWidth + this.x, row * stepHeight + this.y, stepWidth, stepHeight);
        col++;
      }
      row++;
    }
  }
}
