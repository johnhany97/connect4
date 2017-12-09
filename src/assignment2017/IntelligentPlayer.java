/**
 * IntelligentPlayer Class
 * 
 * Class representing a smart AI player
 */
package assignment2017;

import assignment2017.codeprovided.ColumnFullException;
import assignment2017.codeprovided.Connect4GameState;
import assignment2017.codeprovided.Connect4Player;
import assignment2017.codeprovided.IllegalColumnException;

public class IntelligentPlayer extends Connect4Player {

  private GamePanel gamePanel;

  /**
   * Constructor Initializes the instance of the class
   * 
   * @param gamePanel representing panel containing game
   */
  public IntelligentPlayer(GamePanel gamePanel) {
    this.gamePanel = gamePanel;
  }

  /**
   * Function that is called to allow the graphical player to move
   * 
   * @param gameState connect4 game state
   */
  @Override
  public void makeMove(Connect4GameState gameState) {
    int col = -1;
    // Copy the gameState Board to play with it
    int[][] arrCopy = new int[Connect4GameState.NUM_ROWS][Connect4GameState.NUM_COLS];
    for (int i = Connect4GameState.NUM_ROWS - 1; i >= 0; i--) {
      for (int j = 0; j < Connect4GameState.NUM_COLS; j++) {
        arrCopy[i][j] = gameState.getCounterAt(j, i);
      }
    }
    // Have a list of all possible places to play
    int[] possibleMoves = new int[Connect4GameState.NUM_COLS];
    for (int i = 0; i < possibleMoves.length; i++) {
      int empty = Connect4GameState.NUM_ROWS;
      while (empty >= 1 && arrCopy[empty - 1][i] == Connect4GameState.EMPTY) {
        empty--;
      }
      if (empty != Connect4GameState.NUM_ROWS) {
        possibleMoves[i] = empty;
      } else {
        possibleMoves[i] = -1;
      }
    }
    if (((MyGameState) gameState).isBoardEmpty()) {
      col = Connect4GameState.NUM_COLS / 2; // Middle Column
    } else {
      // Make a 4
      col = check4(gameState, gameState.whoseTurn());
      if (col == -1) { // We can't make a 4
        // Block other player's potential 4
        if (gameState.whoseTurn() == Connect4GameState.RED) {
          col = check4(gameState, Connect4GameState.YELLOW);
        } else {
          col = check4(gameState, Connect4GameState.RED);
        }
        if (col == -1) { // Nothing to block
          // Make a three that doesn't help him
          // Considering we have two of our pieces in same column with empty above space
          // This checks for two moves ahead
          for (int i = 0; i < Connect4GameState.NUM_ROWS - 3; i++) {
            for (int j = 0; j < Connect4GameState.NUM_COLS; j++) {
              if (gameState.getCounterAt(j, Connect4GameState.NUM_ROWS - 1 - i) == gameState
                      .whoseTurn()
                      && gameState.getCounterAt(j, Connect4GameState.NUM_ROWS - 2 - i) == gameState
                              .whoseTurn()
                      && gameState.getCounterAt(j,
                              Connect4GameState.NUM_ROWS - 3 - i) == Connect4GameState.EMPTY) {
                if (safe(gameState, gameState.whoseTurn(), i - 2, j)) {
                  col = j;
                }
              }
            }
          }
          // Check if in a row (right)
          for (int i = 0; i < Connect4GameState.NUM_ROWS; i++) {
            for (int j = 0; j < Connect4GameState.NUM_COLS - 2; j++) {
              if (gameState.getCounterAt(j, Connect4GameState.NUM_ROWS - 1 - i) == gameState
                      .whoseTurn()
                      && gameState.getCounterAt(j + 1,
                              Connect4GameState.NUM_ROWS - 1 - i) == gameState.whoseTurn()
                      && gameState.getCounterAt(j + 2,
                              Connect4GameState.NUM_ROWS - 1 - i) == Connect4GameState.EMPTY) {
                if (i == 0 || gameState.getCounterAt(j + 2, i) != Connect4GameState.EMPTY) {
                  if (safe(gameState, gameState.whoseTurn(), i, j + 2)) {
                    col = j + 2;
                  }
                }
              }
            }
          }
          // Left
          for (int i = 0; i < Connect4GameState.NUM_ROWS; i++) {
            for (int j = Connect4GameState.NUM_COLS - 1; j >= 2; j--) {
              if (gameState.getCounterAt(j, Connect4GameState.NUM_ROWS - 1 - i) == gameState
                      .whoseTurn()
                      && gameState.getCounterAt(j - 1,
                              Connect4GameState.NUM_ROWS - 1 - i) == gameState.whoseTurn()
                      && gameState.getCounterAt(j - 2,
                              Connect4GameState.NUM_ROWS - 1 - i) == Connect4GameState.EMPTY) {
                if (i == 0 || gameState.getCounterAt(j - 2, i - 1) != Connect4GameState.EMPTY) {
                  if (safe(gameState, gameState.whoseTurn(), i, j - 2)) {
                    col = j - 2;
                  }
                }
              }
            }
          }
          if (col == -1) { // Nothing that we can do is going to stop him from winning
            // Also it might be that we still don't have threes
            // So just attempt to create a two
            int otherPlayer = Connect4GameState.RED;
            if (gameState.whoseTurn() == Connect4GameState.RED) {
              otherPlayer = Connect4GameState.YELLOW;
            }
            // Block from Right
            for (int i = 0; i < Connect4GameState.NUM_ROWS; i++) {
              for (int j = 0; j < Connect4GameState.NUM_COLS - 2; j++) {
                if (getBoardElement(gameState, j, i) == otherPlayer
                        && getBoardElement(gameState, j + 1, i) == otherPlayer
                        && getBoardElement(gameState, j + 2, i) == Connect4GameState.EMPTY) {
                  if (i == 0
                          || getBoardElement(gameState, j + 2, i - 1) != Connect4GameState.EMPTY) {
                    col = j + 2;
                  }
                }
              }
            }
            // Block from Left
            for (int i = 0; i < Connect4GameState.NUM_ROWS; i++) {
              for (int j = Connect4GameState.NUM_COLS - 1; j > 1; j--) {
                if (getBoardElement(gameState, j, i) == otherPlayer
                        && getBoardElement(gameState, j - 1, i) == otherPlayer
                        && getBoardElement(gameState, j - 2, i) == Connect4GameState.EMPTY) {
                  if (i == 0
                          || getBoardElement(gameState, j - 2, i - 1) != Connect4GameState.EMPTY) {
                    col = j - 2;
                  }
                }
              }
            }
            if (col == -1) {
              col = (int) (Math.random() * 7); // Number from 0 to 6
              while (gameState.isColumnFull(col)) { // make sure that this column isn't full
                col = (int) (Math.random() * 7);
              }
            }
          }
        }
      }
    }
    try {
      gameState.move(col);
    } catch (IllegalColumnException e) {
      System.out.println("The computer attempted a column that's invalid.");
    } catch (ColumnFullException e) {
      System.out.println("The computer attempted a full column. Let's try this again.");
    }
    this.gamePanel.play();
  }

  /**
   * Function used to check if there are three in a row followed by empty slot
   * 
   * Can be used to eather make a 4 or block a 4
   * 
   * @param gameState current game state
   * @param playerColor integer representing color of player checking
   * @return integer representing column that a player should do
   */
  public int check4(Connect4GameState gameState, int playerColor) {
    int col = -1;
    // Make a 4 (In a column)
    // E
    // X
    // X
    // X
    for (int i = 0; i <= 2; i++) {
      for (int j = 0; j < Connect4GameState.NUM_COLS; j++) {
        if (gameState.getCounterAt(j, i) == playerColor
                && gameState.getCounterAt(j, i + 1) == playerColor
                && gameState.getCounterAt(j, i + 2) == playerColor
                && gameState.getCounterAt(j, i + 3) == Connect4GameState.EMPTY) {
          col = j;
        }
      }
    }
    // Make a 4 (In a row)
    // X|X|X|E
    for (int i = Connect4GameState.NUM_ROWS - 1; i >= 0; i--) {
      for (int j = 0; j < 4; j++) {
        if (gameState.getCounterAt(j, i) == playerColor
                && gameState.getCounterAt(j + 1, i) == playerColor
                && gameState.getCounterAt(j + 2, i) == playerColor
                && gameState.getCounterAt(j + 3, i) == Connect4GameState.EMPTY) {
          if (i == 0) {
            col = j + 3;
          } else if (gameState.getCounterAt(j + 3, i - 1) != Connect4GameState.EMPTY) {
            col = j + 3;
          }
        }
      }
    }
    // E|X|X|X
    for (int i = Connect4GameState.NUM_ROWS - 1; i >= 0; i--) {
      for (int j = Connect4GameState.NUM_COLS - 1; j >= 3; j--) {
        if (gameState.getCounterAt(j, i) == playerColor
                && gameState.getCounterAt(j - 1, i) == playerColor
                && gameState.getCounterAt(j - 2, i) == playerColor
                && gameState.getCounterAt(j - 3, i) == Connect4GameState.EMPTY) {
          if (i == 0) {
            col = j - 3;
          } else if (gameState.getCounterAt(j - 3, i - 1) != Connect4GameState.EMPTY) {
            col = j - 3;
          }
        }
      }
    }
    // Broken Row
    // X|X|E|X
    for (int i = Connect4GameState.NUM_ROWS - 1; i >= 0; i--) {
      for (int j = 0; j < 4; j++) {
        if (gameState.getCounterAt(j, i) == playerColor
                && gameState.getCounterAt(j + 1, i) == playerColor
                && gameState.getCounterAt(j + 2, i) == Connect4GameState.EMPTY
                && gameState.getCounterAt(j + 3, i) == playerColor) {
          if (i == 0) {
            col = j + 2;
          } else if (gameState.getCounterAt(j + 2, i - 1) != Connect4GameState.EMPTY) {
            col = j + 2;
          }
        }
      }
    }
    // X|E|X|X
    for (int i = Connect4GameState.NUM_ROWS - 1; i >= 0; i--) {
      for (int j = Connect4GameState.NUM_COLS - 1; j >= 3; j--) {
        if (gameState.getCounterAt(j, i) == playerColor
                && gameState.getCounterAt(j - 1, i) == playerColor
                && gameState.getCounterAt(j - 2, i) == Connect4GameState.EMPTY
                && gameState.getCounterAt(j - 3, i) == playerColor) {
          if (i == 0) {
            col = j - 2;
          } else if (gameState.getCounterAt(j - 2, i - 1) != Connect4GameState.EMPTY) {
            col = j - 2;
          }
        }
      }
    }
    // Make a 4 (In reverse Diagonal)
    for (int i = 0; i <= 2; i++) {
      for (int j = Connect4GameState.NUM_COLS - 1; j >= 3; j--) {
        if (gameState.getCounterAt(j, i) == playerColor
                && gameState.getCounterAt(j - 1, i + 1) == playerColor
                && gameState.getCounterAt(j - 2, i + 2) == playerColor
                && gameState.getCounterAt(j - 3, i + 3) == Connect4GameState.EMPTY
                && gameState.getCounterAt(j - 3, i + 2) != Connect4GameState.EMPTY) {
          col = j - 3;
        }
      }
    }
    for (int i = 0; i <= 2; i++) {
      for (int j = Connect4GameState.NUM_COLS - 1; j >= 3; j--) {
        if (gameState.getCounterAt(j, i) == Connect4GameState.EMPTY
                && gameState.getCounterAt(j - 1, i + 1) == playerColor
                && gameState.getCounterAt(j - 2, i + 2) == playerColor
                && gameState.getCounterAt(j - 3, i + 3) == playerColor) {
          if (i == 0) {
            col = j;
          } else if (gameState.getCounterAt(j, i - 1) != Connect4GameState.EMPTY) {
            col = j;
          }
        }
      }
    }
    for (int i = 0; i <= 2; i++) {
      for (int j = Connect4GameState.NUM_COLS - 1; j >= 3; j--) {
        if (gameState.getCounterAt(j, i) == playerColor
                && gameState.getCounterAt(j - 1, i + 1) == Connect4GameState.EMPTY
                && gameState.getCounterAt(j - 2, i + 2) == playerColor
                && gameState.getCounterAt(j - 3, i + 3) == playerColor
                && gameState.getCounterAt(j - 1, i) != Connect4GameState.EMPTY) {
          col = j - 1;
        }
      }
    }
    for (int i = 0; i <= 2; i++) {
      for (int j = Connect4GameState.NUM_COLS - 1; j >= 3; j--) {
        if (gameState.getCounterAt(j, i) == playerColor
                && gameState.getCounterAt(j - 1, i + 1) == playerColor
                && gameState.getCounterAt(j - 2, i + 2) == Connect4GameState.EMPTY
                && gameState.getCounterAt(j - 3, i + 3) == playerColor
                && gameState.getCounterAt(j - 2, i + 1) != Connect4GameState.EMPTY) {
          col = j - 2;
        }
      }
    }
    // Make a 4 (In Diagonal)
    for (int i = 0; i <= 2; i++) {
      for (int j = 0; j <= 3; j++) {
        if (gameState.getCounterAt(j, i) == playerColor
                && gameState.getCounterAt(j + 1, i + 1) == playerColor
                && gameState.getCounterAt(j + 2, i + 2) == playerColor
                && gameState.getCounterAt(j + 3, i + 3) == Connect4GameState.EMPTY
                && gameState.getCounterAt(j + 3, i + 2) != Connect4GameState.EMPTY) {
          col = j + 3;
        }
      }
    }
    for (int i = 0; i <= 2; i++) {
      for (int j = 0; j <= 3; j++) {
        if (gameState.getCounterAt(j, i) == playerColor
                && gameState.getCounterAt(j + 1, i + 1) == Connect4GameState.EMPTY
                && gameState.getCounterAt(j + 2, i + 2) == playerColor
                && gameState.getCounterAt(j + 3, i + 3) == playerColor
                && gameState.getCounterAt(j + 1, i) != Connect4GameState.EMPTY) {
          col = j + 1;
        }
      }
    }
    for (int i = 0; i <= 2; i++) {
      for (int j = 0; j <= 3; j++) {
        if (gameState.getCounterAt(j, i) == playerColor
                && gameState.getCounterAt(j + 1, i + 1) == playerColor
                && gameState.getCounterAt(j + 2, i + 2) == Connect4GameState.EMPTY
                && gameState.getCounterAt(j + 3, i + 3) == playerColor
                && gameState.getCounterAt(j + 2, i + 1) != Connect4GameState.EMPTY) {
          col = j + 2;
        }
      }
    }
    for (int i = 0; i <= 2; i++) {
      for (int j = 0; j <= 3; j++) {
        if (gameState.getCounterAt(j, i) == Connect4GameState.EMPTY
                && gameState.getCounterAt(j + 1, i + 1) == playerColor
                && gameState.getCounterAt(j + 2, i + 2) == playerColor
                && gameState.getCounterAt(j + 3, i + 3) == playerColor) {
          if (i == 0) {
            col = j;
          } else if (gameState.getCounterAt(j, i - 1) == playerColor) {
            col = j;
          }
        }
      }
    }
    return col;
  }

  /**
   * Function used to check if a slot is safe two moves ahead or not
   * 
   * @param gameState current Game state
   * @param playerColor color of currentPlayer
   * @param i row representing location of potential play
   * @param j col representing location of potential play
   * @return boolean representing whether it's safe or not
   */
  public boolean safe(Connect4GameState gameState, int playerColor, int i, int j) {
    boolean flag = true;
    int otherPlayer = Connect4GameState.RED;
    if (playerColor == Connect4GameState.RED) {
      otherPlayer = Connect4GameState.YELLOW;
    }
    // Consider that given i and j represent location of P
    // O|O|O|E
    // P
    if (i >= 1 && i <= 5 && j >= 3 && j <= 6) {
      if (getBoardElement(gameState, j - 1, i - 1) == otherPlayer
              && getBoardElement(gameState, j - 2, i - 1) == otherPlayer
              && getBoardElement(gameState, j - 3, i - 1) == otherPlayer) {
        flag = false;
      }
    }
    // E|O|O|O
    // P
    if (i >= 1 && i <= 5 && j >= 0 && j <= 3) {
      if (getBoardElement(gameState, j + 1, i - 1) == otherPlayer
              && getBoardElement(gameState, j + 2, i - 1) == otherPlayer
              && getBoardElement(gameState, j + 3, i - 1) == otherPlayer) {
        flag = false;
      }
    }
    // O|E|O|O
    // P
    if (i >= 1 && i <= 5 && j >= 1 && j <= 4) {
      if (getBoardElement(gameState, j - 1, i - 1) == otherPlayer
              && getBoardElement(gameState, j + 1, i - 1) == otherPlayer
              && getBoardElement(gameState, j + 2, i - 1) == otherPlayer) {
        flag = false;
      }
    }
    // O|O|E|O
    // P
    if (i >= 1 && i <= 5 && j >= 2 && j <= 5) {
      if (getBoardElement(gameState, j - 1, i - 1) == otherPlayer
              && getBoardElement(gameState, j - 2, i - 1) == otherPlayer
              && getBoardElement(gameState, j + 1, i - 1) == otherPlayer) {
        flag = false;
      }
    }
    // O
    // O
    // E
    // P|O
    if (i >= 3 && i <= 5 && j >= 2 && j <= 5) {
      if (getBoardElement(gameState, j + 1, i) == otherPlayer
              && getBoardElement(gameState, j - 1, i - 2) == otherPlayer
              && getBoardElement(gameState, j - 2, i - 3) == otherPlayer) {
        flag = false;
      }
    }
    // O
    // E
    // P|O
    // | |O
    if (i >= 2 && i <= 4 && j >= 1 && j <= 4) {
      if (getBoardElement(gameState, j + 1, i) == otherPlayer
              && getBoardElement(gameState, j + 2, i + 1) == otherPlayer
              && getBoardElement(gameState, j - 1, i - 2) == otherPlayer) {
        flag = false;
      }
    }
    // E
    // P|O
    // | |O
    // | | |O
    if (i >= 1 && i <= 3 && j >= 0 && j <= 3) {
      if (getBoardElement(gameState, j + 1, i) == otherPlayer
              && getBoardElement(gameState, j + 2, i + 1) == otherPlayer
              && getBoardElement(gameState, j + 3, i + 2) == otherPlayer) {
        System.out.println("Wow there");
        flag = false;
      }
    }
    // O
    // O
    // E
    // O|P
    if (i >= 3 && i <= 5 && j >= 1 && j <= 4) {
      if (gameState.getCounterAt(j - 1, i) == otherPlayer
              && gameState.getCounterAt(j + 1, i - 2) == otherPlayer
              && gameState.getCounterAt(j + 2, i - 3) == otherPlayer) {
        flag = false;
      }
    }
    // O
    // E
    // O|P
    // O| |
    if (i >= 2 && i <= 4 && j >= 2 && j <= 5) {
      if (gameState.getCounterAt(j - 1, i) == otherPlayer
              && gameState.getCounterAt(j - 2, i + 1) == otherPlayer
              && gameState.getCounterAt(j + 1, i - 2) == otherPlayer) {
        flag = false;
      }
    }
    // E
    // O|P
    // O| |
    // O| | |
    if (i >= 1 && i <= 3 && j >= 3 && j <= 6) {
      if (gameState.getCounterAt(j - 1, i) == otherPlayer
              && gameState.getCounterAt(j - 2, i + 1) == otherPlayer
              && gameState.getCounterAt(j - 3, i + 2) == otherPlayer) {
        flag = false;
      }
    }
    return flag;
  }

  /**
   * Function to get board element dealing with it as if normal 2d array
   * 
   * @param gameState current game state
   * @param j represents col
   * @param i represents row
   * @return int representing content of board in that index
   */
  public int getBoardElement(Connect4GameState gameState, int j, int i) {
    return gameState.getCounterAt(j, Connect4GameState.NUM_ROWS - 1 - i);
  }
}
