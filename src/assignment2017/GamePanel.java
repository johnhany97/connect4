/**
 * GamePanel Class
 * 
 * Class representing 2D Connect4 playing panel
 */
package assignment2017;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import assignment2017.codeprovided.Connect4GameState;
import assignment2017.codeprovided.Connect4Player;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {

  // Instance variables
  private Connect4GameState gameState;
  private Connect4GuiDisplay frame;
  private Connect4Player red;
  private Connect4Player yellow;
  private Board board;
  private int boardX;
  private int boardY;
  private JLabel players;
  private JPanel buttonsPanel;
  private JButton homeButton;
  private JButton exitButton;

  /**
   * Constructor to initialize instance of class
   * 
   * @param frame Connect4GuiDisplay which represents the main frame
   * @param gs Connect4GameState which represents current game state
   */
  public GamePanel(Connect4GuiDisplay frame, Connect4GameState gs) {
    super();
    // Instantiate instance variables
    this.frame = frame;
    this.gameState = gs;
    // Start game
    gs.startGame();
    setLayout(new BorderLayout());
    // Draw board
    int frameWidth = this.frame.getWidth();
    int frameHeight = this.frame.getHeight();
    int frameWidthStep = frameWidth / (Connect4GameState.NUM_COLS + 2);
    int frameHeightStep = frameHeight / (Connect4GameState.NUM_ROWS + 2);
    this.boardX = frameWidthStep;
    this.boardY = frameHeightStep - (Connect4GuiDisplay.FONT_SIZE * this.frame.getFontScale());
    this.board = new Board(this.gameState, frameWidthStep * Connect4GameState.NUM_COLS,
            frameHeightStep * Connect4GameState.NUM_ROWS, frameWidthStep,
            frameHeightStep - (Connect4GuiDisplay.FONT_SIZE * this.frame.getFontScale()));
    add(this.board, BorderLayout.CENTER);
    // Buttons Panel
    this.buttonsPanel = new JPanel(new GridLayout(1, 0));
    // Go to Home Button
    this.homeButton = new JButton("Go to Home");
    this.homeButton.setFont(new Font("Sans Serif", Font.PLAIN,
            Connect4GuiDisplay.FONT_SIZE * this.frame.getFontScale()));
    this.homeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        gameState = new MyGameState();
        frame.setGameState(gameState);
        board.setGameState(gameState);
        MouseListener[] arr = board.getMouseListeners();
        for (int i = 0; i < arr.length; i++) {
          board.removeMouseListener(arr[i]);
        }
        players.setText("Connect 4");
        gameState.startGame();
        frame.displayMainMenu();
      }
    });
    this.buttonsPanel.add(this.homeButton);
    // Exit Button
    this.exitButton = new JButton("Exit");
    this.exitButton.setFont(new Font("Sans Serif", Font.PLAIN,
            Connect4GuiDisplay.FONT_SIZE * this.frame.getFontScale()));
    this.exitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        System.exit(0);
      }
    });
    this.buttonsPanel.add(this.exitButton);
    add(this.buttonsPanel, BorderLayout.SOUTH);
    // Top Notice
    this.players = new JLabel("Connect 4");
    this.players.setFont(new Font("Sans Serif", Font.PLAIN,
            Connect4GuiDisplay.FONT_SIZE * this.frame.getFontScale()));
    this.players.setHorizontalAlignment(SwingConstants.CENTER);
    add(players, BorderLayout.NORTH);
  }

  /**
   * Function to play and update board
   */
  public void play() {
    if (!gameState.gameOver()) {
      // Switching between players
      this.frame.repaint();
      if (gameState.whoseTurn() == Connect4GameState.RED) {
        if (red.getClass().getName().equals("assignment2017.GuiPlayer")) {
          this.board.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
              int mouseX = e.getX();
              int mouseY = e.getY();
              boolean onBoard = true;
              if (mouseX < boardX || mouseX > boardX * (Connect4GameState.NUM_COLS + 1)) {
                onBoard = false;
              }
              if (mouseY < boardY || mouseY > boardY + board.getHeight()) {
                onBoard = false;
              }
              if (onBoard) {
                ((GuiPlayer) red).makeMove(gameState, (mouseX
                        / ((boardX * (Connect4GameState.NUM_COLS)) / Connect4GameState.NUM_COLS))
                        - 1);
                board.removeMouseListener(this);
              }
            }
          });
        } else {
          red.makeMove(gameState);
        }
      } else {
        if (yellow.getClass().getName().equals("assignment2017.GuiPlayer")) {
          this.board.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
              int mouseX = e.getX();
              int mouseY = e.getY();
              boolean onBoard = true;
              if (mouseX < boardX || mouseX > boardX * (Connect4GameState.NUM_COLS + 1)) {
                onBoard = false;
              }
              if (mouseY < boardY || mouseY > boardY + board.getHeight()) {
                onBoard = false;
              }
              if (onBoard) {
                ((GuiPlayer) yellow).makeMove(gameState, (mouseX
                        / ((boardX * (Connect4GameState.NUM_COLS)) / Connect4GameState.NUM_COLS))
                        - 1);
                board.removeMouseListener(this);
              }
            }
          });
        } else {
          yellow.makeMove(gameState);
        }
      }
    }
    if (gameState.gameOver()) {
      // Recognize winner
      // Either RED, YELLOW or EMPTY
      int winner = gameState.getWinner();
      board.repaint();
      switch (winner) {
        case Connect4GameState.RED: // Red
          this.players.setText("Red Wins");
          break;
        case Connect4GameState.YELLOW: // Yellow
          this.players.setText("Yellow Wins");
          break;
        default: // Board is full with no winners
          this.players.setText("It's a tie :)");
          break;
      }
    }
  }

  /**
   * Function to set red player
   * 
   * @param red Connect4Player class representing first player
   */
  public void setRedPlayer(Connect4Player red) {
    this.red = red;
  }

  /**
   * Function to get red player
   * 
   * @return Connect4Player representing red player
   */
  public Connect4Player getRedPlayer() {
    return this.red;
  }

  /**
   * Function to set yellow player
   * 
   * @param yellow Connect4Player class representing second player
   */
  public void setYellowPlayer(Connect4Player yellow) {
    this.yellow = yellow;
  }

  /**
   * Function to get yellow player
   * 
   * @return Connect4Player representing yellow player
   */
  public Connect4Player getYellowPlayer() {
    return this.yellow;
  }

  /**
   * Function to return board
   * 
   * @return Board representing the board
   */
  public Board getBoard() {
    return this.board;
  }
}
