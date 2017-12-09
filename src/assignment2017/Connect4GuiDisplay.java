/**
 * Connect4GuiDisplay Class
 * 
 * Class representing Graphical user interface frame for Connect4 game
 */
package assignment2017;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;

import assignment2017.codeprovided.Connect4Displayable;
import assignment2017.codeprovided.Connect4GameState;
import assignment2017.codeprovided.Connect4Player;

@SuppressWarnings("serial")
public class Connect4GuiDisplay extends JFrame implements Connect4Displayable {

  /** Defines how many cells to divide width of screen by **/
  final public static int WIDTH_STEP = 14;

  /** Defines how many cells to divide the height of the screen by **/
  final public static int HEIGHT_STEP = 7;

  /** Defines normal font size **/
  final public static int FONT_SIZE = 40;

  // Instance variables
  private Connect4GameState gameState;
  private int screenHeight;
  private int screenWidth;
  private int screenWidthStep;
  private int screenHeightStep;
  private int scaleFactor;
  private MainMenu mainMenu;
  private GamePanel gamePanel;

  /**
   * Constructor Defines an instance of Connect4ConsoleDisplay to print out a game on the console
   * screen
   * 
   * @param gameState Connect4GameState represents the current game state
   */
  public Connect4GuiDisplay(Connect4GameState gameState) {
    this.gameState = gameState;
    initializeFrame();
    this.mainMenu = new MainMenu(this);
    this.gamePanel = new GamePanel(this, this.gameState);
  }

  /**
   * Function to set GameState
   * 
   * @param gs Connnect4GameState representing the game state
   */
  public void setGameState(Connect4GameState gs) {
    this.gameState = gs;
  }

  /**
   * Function responsible for initializing JFrame properties
   */
  public void initializeFrame() {
    // Title
    setTitle("Connect 4");
    // Size and location
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension screenDimensions = toolkit.getScreenSize();
    this.screenWidthStep = screenDimensions.width / WIDTH_STEP;
    this.screenHeightStep = screenDimensions.height / HEIGHT_STEP;
    this.screenWidth = this.screenWidthStep * Connect4GameState.NUM_COLS;
    this.screenHeight = this.screenHeightStep * Connect4GameState.NUM_ROWS;
    setSize(this.screenWidth, this.screenHeight);
    int screenLocationX = (screenDimensions.width - this.screenWidth) / 2;
    int screenLocationY = (screenDimensions.height - this.screenHeight) / 2;
    setLocation(new Point(screenLocationX, screenLocationY));

    // Set Font scaling
    this.scaleFactor = screenDimensions.width / 1920;
    // Default close operation
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // Prevent resizing
    setResizable(false);
  }

  /**
   * Function responsible for showing main menu
   */
  public void displayMainMenu() {
    Container contentPane = this.getContentPane();
    contentPane.removeAll();
    contentPane.add(this.mainMenu);
    repaint();
    setVisible(true);
  }

  /**
   * Function responsible for setting board players
   * 
   * @param red Connect4Player representing first player
   * @param yellow Connect4Player representing second player
   */
  public void setPlayers(Connect4Player red, Connect4Player yellow) {
    this.gamePanel.setRedPlayer(red);
    this.gamePanel.setYellowPlayer(yellow);
  }

  /**
   * Function responsible for printing out the board
   */
  @Override
  public void displayBoard() {
    Container contentPane = this.getContentPane();
    contentPane.removeAll();
    contentPane.add(this.gamePanel);
    repaint();
    setVisible(true);
    this.gamePanel.play();
  }

  /**
   * Function to return font scale
   * 
   * @return int representing the font scale number
   */
  public int getFontScale() {
    return this.scaleFactor;
  }

  /**
   * Function to get the game panel
   * 
   * @return GamePanel representing the game panel
   */
  public GamePanel getGamePanel() {
    return this.gamePanel;
  }
}
