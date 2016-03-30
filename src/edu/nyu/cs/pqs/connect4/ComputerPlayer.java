package edu.nyu.cs.pqs.connect4;

import java.util.Random;

/**
 * ComputerPlayer.class is an Immutable class that implements Player Interface
 * to provide Computer Player's behavior in the ConnectFour Game. The computer
 * style makeMove() method in this class is implemented by looking one step
 * ahead and makes that move if it results a win. Or, pick a random available
 * column in the game board to drop the piece. The availability of each column
 * means the column is not full.
 * 
 * @author Ke Chen
 * @see Player
 *
 */
public class ComputerPlayer implements Player {

  private final String name;
  private final PlayerType type;

  /**
   * The constructor of ComputerPlayer class takes two parameters to represent
   * the name and type of this Computer Player.
   * 
   * @param name
   *          A String variable represents Player's name.
   * @param type
   *          A PlayerType enum variable represents Player's type.
   */
  public ComputerPlayer(String name, PlayerType type) {
    this.name = name;
    this.type = type;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public PlayerType getType() {
    return this.type;
  }

  @Override
  public Coordinate makeMove(int col) {
    GameBoard gameBoard = GameBoard.getInstance();
    int[] rowTracker = gameBoard.getRowTracker();

    for (int j = 0; j < GameBoard.COLNUM; j++) {
      if (rowTracker[j] != 0) {
        int x = gameBoard.setBoard(j, this);
        Coordinate pos = new Coordinate(x, j);
        if (gameBoard.isWin(pos, this)) {
          return pos;
        }
        gameBoard.unsetBoard(j);
      }
    }

    Random rand = new Random();
    int j = rand.nextInt(GameBoard.COLNUM);
    int y = j;
    while (y < GameBoard.COLNUM && rowTracker[y] == 0) {
      y++;
    }
    if (y == GameBoard.COLNUM) {
      y = j;
      while (y >= 0 && rowTracker[y] == 0) {
        y--;
      }
    }

    int x = gameBoard.setBoard(y, this);
    return new Coordinate(x, y);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof ComputerPlayer)) {
      return false;
    }
    ComputerPlayer cp = (ComputerPlayer) obj;
    if (cp.name == null || cp.type == null) {
      return false;
    }
    if (!cp.name.equals(this.name) || cp.type != this.type) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = 17;
    if (this.name == null) {
      result = 31 * result + 0;
    }
    result = 31 * result + (this.name == null ? 0 : this.name.hashCode());
    result = 31 * result + (this.type == null ? 0 : this.type.hashCode());
    return result;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("ComputerPlayer [");
    sb.append("name = ");
    sb.append(this.name);
    sb.append(", type = ");
    sb.append(this.type);
    sb.append("]");
    return sb.toString();
  }
}
