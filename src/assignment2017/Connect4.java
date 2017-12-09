/**
 * Connect4 Class
 * 
 * Class responsible of connecting different game element classes together
 */
package assignment2017;

import assignment2017.codeprovided.Connect4Displayable;
import assignment2017.codeprovided.Connect4GameState;
import assignment2017.codeprovided.Connect4Player;

public class Connect4 {
  // Instance variables
  Connect4GameState gameState;
  Connect4Player player1;
  Connect4Player player2;
  Connect4Displayable display;
  boolean graphicsMode;

  /**
   * Constructor Defines an instant of a Connect4Game
   * 
   * @param gs Connect4GameState represents a connect4 game state to be played
   * @param p1 Connect4Player represents the first player in the game
   * @param p2 Connect4Player represents the second player in the game
   * @param d Connect4Display represents the methodology to interface with to print out the game
   * @param f Boolean represents whether it's in graphics mode or not
   */
  public Connect4(Connect4GameState gs, Connect4Player p1, Connect4Player p2, Connect4Displayable d,
          boolean f) {
    this.gameState = gs;
    this.player1 = p1;
    this.player2 = p2;
    this.display = d;
    this.graphicsMode = f;
  }

  /**
   * Constructor Defines an instant of a Connect4Game without instances of players Most commonly
   * used for graphics mode
   * 
   * @param gs Connect4GameState represents a connect4 game state to be played
   * @param d Connect4Display represents the methodology to interface with to print out the game
   * @param f Boolean represents whether it's in graphics mode or not
   */
  public Connect4(Connect4GameState gs, Connect4Displayable d, boolean f) {
    this.gameState = gs;
    this.display = d;
    this.graphicsMode = f;
  }

  /**
   * Function to start playing the game It initializes the state, and starts allowing players to
   * play
   */
  public void play() {
    if (graphicsMode) {
      Connect4GuiDisplay display2 = (Connect4GuiDisplay) this.display;
      display2.displayMainMenu();
    } else {
      // Let the games begin
      gameState.startGame();
      while (!gameState.gameOver()) {
        // Displaying the game board before every move
        display.displayBoard();
        // Switching between players
        if (gameState.whoseTurn() == Connect4GameState.RED) {
          player1.makeMove(gameState);
        } else {
          player2.makeMove(gameState);
        }
      }
      // Display board after every move by the player
      display.displayBoard();
    }
  }
}
