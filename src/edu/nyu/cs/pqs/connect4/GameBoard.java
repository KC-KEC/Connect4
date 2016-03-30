package edu.nyu.cs.pqs.connect4;

import java.util.Arrays;

/**
 * GameBoard class is a singleton class that stores the game board information.
 * Since this is a two-player game, three values would be used to represent
 * occupied cells by each player and the empty one. To clarify, '1' represents
 * player1, '2' represents player2 and '0' means empty. These values are stored
 * in a two-dimensional array. And an one-dimensional array
 * <code>rowTracker[]</code> is maintained to store the available row number of
 * each column on the game board, which is initialized to the value of total row
 * number.
 * 
 * @author Ke Chen
 *
 */
public class GameBoard {
  private static GameBoard instance = null;
  private int[][] board;
  public final static int ROWNUM = 6;
  public final static int COLNUM = 7;
  private int[] rowTracker;

  private GameBoard() {
    board = new int[ROWNUM][COLNUM];
    rowTracker = new int[COLNUM];
    Arrays.fill(rowTracker, ROWNUM);
  }

  /**
   * Use this static method to generate the only instance of GameBoard class.
   * The instance would be instantiate only if it's equal to null.
   * 
   * @return The singleton instance of GameBoard class.
   */
  public static GameBoard getInstance() {
    if (instance == null) {
      instance = new GameBoard();
    }
    return instance;
  }

  /**
   * Get a copy of the game board array.
   * 
   * @return A two-dimensional array represents the game board.
   */
  public int[][] getBoard() {
    int[][] copyBoard = new int[ROWNUM][COLNUM];
    for (int i = 0; i < ROWNUM; i++) {
      for (int j = 0; j < COLNUM; j++) {
        copyBoard[i][j] = board[i][j];
      }
    }
    return copyBoard;
  }

  /**
   * Get a copy of the rowTracker array that stores the available row number of
   * each column on the game board.
   * 
   * @return A one-dimensional array.
   */
  public int[] getRowTracker() {
    return Arrays.copyOf(rowTracker, rowTracker.length);
  }

  /**
   * Set the game board to represent the behavior of placing a piece on column
   * <code>j</code> by the given <code>player</code>. If the <code>player</code>
   * is of type PLAYER1, store '1' in the board array, otherwise store '2'.
   * 
   * @param j
   *          represents the column in which the player place the piece.
   * @param player
   *          represents which player is placing the piece.
   * @return A integer that is the row number of the placed piece.
   */
  public int setBoard(int j, Player player) {
    if (j < 0 || j >= COLNUM) {
      return -1;
    }
    if (rowTracker[j] == 0) {
      return -1;
    }
    rowTracker[j]--;
    int i = rowTracker[j];
    board[i][j] = player.getType() == PlayerType.PLAYER1 ? 1 : 2;
    return i;
  }

  /**
   * Unset the piece that is just placed. This method is called by computer
   * moving to figure out whether there is a move that results a win.
   * 
   * @param j
   *          represents the column to unset.
   */
  public void unsetBoard(int j) {
    if (j < 0 || j >= COLNUM) {
      return;
    }
    if (rowTracker[j] == GameBoard.ROWNUM) {
      return;
    }
    int i = rowTracker[j];
    board[i][j] = 0;
    rowTracker[j]++;
  }

  /**
   * Reset the board array to 0.
   */
  public void resetBoard() {
    for (int i = 0; i < ROWNUM; i++) {
      for (int j = 0; j < COLNUM; j++) {
        board[i][j] = 0;
      }
    }

    for (int j = 0; j < COLNUM; j++) {
      rowTracker[j] = ROWNUM;
    }
  }

  /**
   * Check if place the piece by <code>player</code> in the cell
   * <code>pos</code> would result a win.
   * 
   * @param pos
   *          A Coordinate reference to present the position in the game board.
   * @param player
   *          A Player reference.
   * @return If it results a win, return true. Otherwise return false.
   */
  public boolean isWin(Coordinate pos, Player player) {
    int val = player.getType() == PlayerType.PLAYER1 ? 1 : 2;
    boolean result = false;
    result |= checkHorizontal(pos, val);
    result |= checkVertical(pos, val);
    result |= checkDiagnose(pos, val);
    return result;
  }

  private boolean checkHorizontal(Coordinate pos, int val) {
    int l = pos.getY();
    int r = pos.getY();
    int x = pos.getX();
    while (l - 1 >= 0 && board[x][l - 1] == val) {
      l--;
    }
    while (r + 1 < COLNUM && board[x][r + 1] == val) {
      r++;
    }

    if (r - l + 1 >= 4) {
      return true;
    }
    return false;
  }

  private boolean checkVertical(Coordinate pos, int val) {
    int l = pos.getX();
    int r = pos.getX();
    int y = pos.getY();
    while (l - 1 >= 0 && board[l - 1][y] == val) {
      l--;
    }
    while (r + 1 < ROWNUM && board[r + 1][y] == val) {
      r++;
    }

    if (r - l + 1 >= 4) {
      return true;
    }
    return false;
  }

  private boolean checkDiagnose(Coordinate pos, int val) {
    int posX = pos.getX();
    int posY = pos.getY();

    int x = posX - 1;
    int y = posY + 1;
    int count = 1;
    while (x >= 0 && y < ROWNUM && board[x][y] == val) {
      count++;
      x--;
      y++;
    }
    x = posX + 1;
    y = posY - 1;
    while (x < ROWNUM && y >= 0 && board[x][y] == val) {
      count++;
      x++;
      y--;
    }
    if (count >= 4) {
      return true;
    }

    count = 1;
    x = posX - 1;
    y = posY - 1;
    while (x >= 0 && y >= 0 && board[x][y] == val) {
      count++;
      x--;
      y--;
    }
    x = posX + 1;
    y = posY + 1;
    while (x < ROWNUM && y < COLNUM && board[x][y] == val) {
      count++;
      x++;
      y++;
    }
    if (count >= 4) {
      return true;
    }

    return false;
  }

  /**
   * Check if the current game board makes a draw to both player.
   * 
   * @return If it's a draw, return true. Otherwise return false.
   */
  public boolean isTie() {
    for (int j = 0; j < COLNUM; j++) {
      if (rowTracker[j] != 0) {
        return false;
      }
    }
    return true;
  }
}
