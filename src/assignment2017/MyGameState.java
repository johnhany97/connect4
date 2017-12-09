/**
 * MyGameState Class
 * 
 * Class representing a game state for Connect4 game
 */
package assignment2017;

import assignment2017.codeprovided.ColumnFullException;
import assignment2017.codeprovided.Connect4GameState;
import assignment2017.codeprovided.IllegalColumnException;
import assignment2017.codeprovided.IllegalRowException;

public class MyGameState extends Connect4GameState {
  // Instance variables
  private int[][] board;
  private int currentTurn;

  /**
   * Constructor Defines a new instance of the class with an empty board and an empty player
   */
  public MyGameState() {
    this.board = new int[NUM_ROWS][NUM_COLS];
    this.currentTurn = EMPTY;
  }

  /**
   * Constructor Defines a new instance of the class with given board and desired player
   * 
   * @param board int[][] represents 2D array representing board
   * @param currentTurn int represents current player turn (Red (0) or white (1))
   */
  public MyGameState(int[][] board, int currentTurn) {
    this.board = board;
    this.currentTurn = currentTurn;
  }

  /**
   * Starts the game. Initializes every grid position in the board to EMPTY and sets the current
   * turn to RED
   */
  @Override
  public void startGame() {
    // Empty all board elements
    for (int i = 0; i < NUM_ROWS; i++) {
      for (int j = 0; j < NUM_COLS; j++) {
        board[i][j] = EMPTY;
      }
    }
    // Initially, player is red
    currentTurn = RED;
  }

  /**
   * Drops a counter into a slot on the board. The colour of the counter depends on whose turn it is
   * 
   * @param col the column in which to drop the counter, in the range 0-6
   * @throws ColumnFullException if the column denoted by col is full (i.e. the move cannot be
   *         played)
   * @throws IllegalColumnException if col is not in the range 0-6 (i.e. an invalid column)
   */
  @Override
  public void move(int col) throws ColumnFullException, IllegalColumnException {
    // Handle Exceptions
    if (isColumnFull(col)) {
      throw new ColumnFullException(col);
    }
    if (col < 0 || col > NUM_COLS) {
      throw new IllegalColumnException(col);
    }
    // Memorize row index of highest empty block
    int index = 0;
    for (int i = 0; i < NUM_ROWS; i++) {
      if (board[i][col] == EMPTY) {
        index = i;
      }
    }
    // Fill it with the current player's colour and swap turns
    if (currentTurn == RED) {
      board[index][col] = RED;
      currentTurn = YELLOW;
    } else {
      board[index][col] = YELLOW;
      currentTurn = RED;
    }
  }

  /**
   * Returns whose turn is it
   * 
   * @return the constant RED if it is red's turn, else YELLOW
   */
  @Override
  public int whoseTurn() {
    return currentTurn;
  }

  /**
   * Returns a constant denoting the status of the slot at the position denoted by the col and row
   * parameters
   * 
   * @param col the column of the position being queried (in the range 0-6)
   * @param row the row of the position being queried (in the range 0-5)
   * @return the EMPTY constant if the slot is empty, the RED constant if the slot is filled by a
   *         red counter, the YELLOW constant if is yellow
   * @throws IllegalColumnException if col is not in the range 0-6 (i.e. an invalid column)
   * @throws IllegalRowException if col is not in the range 0-5 (i.e. an invalid row)
   */
  @Override
  public int getCounterAt(int col, int row) throws IllegalColumnException, IllegalRowException {
    // Handle exceptions
    if (col < 0 || col >= NUM_COLS) {
      throw new IllegalColumnException(col);
    }
    if (row < 0 || row >= NUM_ROWS) {
      throw new IllegalRowException(row);
    }
    return board[NUM_ROWS - 1 - row][col];
  }

  /**
   * Returns whether the board is full and the game has ended in a tie
   * 
   * @return true if the board is full, else false
   */
  @Override
  public boolean isBoardFull() {
    boolean flag = true; // Assume it's true
    for (int i = 0; flag && i < NUM_ROWS; i++) {
      for (int j = 0; flag && j < NUM_COLS; j++) {
        if (board[i][j] == EMPTY) {
          flag = false; // If we're here, assumption failed
        }
      }
    }
    return flag; // Return our flag representing state of the board
  }

  /**
   * Returns whether the column denoted by the col parameter is full or not
   * 
   * @param col the column being queried (in the range 0-6)
   * @return true if the column denoted by col is full of counters, else false
   * @throws IllegalColumnException if col is not in the range 0-6 (i.e. an invalid column)
   */
  @Override
  public boolean isColumnFull(int col) throws IllegalColumnException {
    // Handle exceptions
    if (col < 0 || col >= NUM_COLS) {
      throw new IllegalColumnException(col);
    }
    boolean flag = true; // Assume it's true
    for (int i = 0; flag && i < NUM_ROWS; i++) {
      if (board[i][col] == EMPTY) {
        flag = false; // If we're here, assumption failed
      }
    }
    return flag;
  }

  /**
   * Indicates whether the game has been won, and by whom
   * 
   * @return the constant EMPTY if there is no winner yet, else the constant RED if the red player
   *         has four in a row, or the YELLOW constant if it is yellow that has won
   */
  @Override
  public int getWinner() {
    // Loop through all board elements and test from these indices possible winnings
    for (int i = 0; i < NUM_ROWS; i++) {
      for (int j = 0; j < NUM_COLS; j++) {
        if (board[i][j] != EMPTY) { // To avoid finding an empty winner
          // TOP & BOTTOM
          if (check(board[i][j], i, j, -1, 0)
                  + check(board[i][j], i + 1, j, 1, 0) >= NUM_IN_A_ROW_TO_WIN) {
            return board[i][j];
          }
          // LEFT & RIGHT
          if (check(board[i][j], i, j, 0, -1)
                  + check(board[i][j], i, j + 1, 0, 1) >= NUM_IN_A_ROW_TO_WIN) {
            return board[i][j];
          }
          // Diagonal
          if (check(board[i][j], i, j, -1, -1)
                  + check(board[i][j], i + 1, j + 1, 1, 1) >= NUM_IN_A_ROW_TO_WIN) {
            return board[i][j];
          }
          // Reverse Diagonal
          if (check(board[i][j], i, j, -1, 1)
                  + check(board[i][j], i + 1, j - 1, 1, -1) >= NUM_IN_A_ROW_TO_WIN) {
            return board[i][j];
          }
        }
      }
    }
    return EMPTY; // No one won
  }

  /**
   * Function that returns the number of consecutive counters a player has in a certain direction
   * 
   * @param player int represents the player being checked
   * @param py int represents initially the location being tested (row)
   * @param px int represents initially the location being tested (col)
   * @param dy int represents change to be applied to the row value
   * @param dx int represents change to be applied to the col value
   * @return int representing the amount of consecutive counters this player has in that direction
   */
  private int check(int player, int py, int px, int dy, int dx) {
    // If exceeded the board limits
    if (py < 0 || py >= NUM_ROWS || px < 0 || px >= NUM_COLS) {
      return 0;
    }
    // If board index is not of the same player colour as we're looking at
    if (board[py][px] != player) {
      return 0;
    }
    // Increment one and check in the same direction
    return 1 + check(player, py + dy, px + dx, dy, dx);
  }

  /**
   * Indicates whether the current game has finished
   * 
   * @return true if there is a winner or the board is full
   */
  @Override
  public boolean gameOver() {
    return isBoardFull() || getWinner() != EMPTY;
  }

  /**
   * Copies the current Connect4GameState instance into another object
   * 
   * @return the new Connect4GameState instance
   */
  @Override
  public Connect4GameState copy() {
    // Create a copy of the board array
    int[][] temp = new int[NUM_ROWS][NUM_COLS];
    for (int i = 0; i < NUM_ROWS; i++) {
      for (int j = 0; j < NUM_COLS; j++) {
        temp[i][j] = this.board[i][j];
      }
    }
    // Return a new instance of the class using the copy
    return new MyGameState(temp, this.currentTurn);
  }

  /**
   * Indicates if board is empty or not
   * 
   * @return boolean representing if board is empty or not
   */
  public boolean isBoardEmpty() {
    boolean flag = true;
    for (int i = 0; i < this.board.length; i++) {
      for (int j = 0; j < this.board[i].length; j++) {
        if (this.board[i][j] != EMPTY) {
          flag = false;
        }
      }
    }
    return flag;
  }
}
