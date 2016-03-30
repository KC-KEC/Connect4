package edu.nyu.cs.pqs.connect4;

/**
 * HumanPlayer class is an Immutable class that implements Player Interface to
 * provide Human Player's behavior in the Connect Four game.
 * 
 * @author Ke Chen
 * @see Player
 *
 */
public class HumanPlayer implements Player {

  private final String name;
  private final PlayerType type;

  /**
   * Builder class is to generate instances of HumanPlayer class. Player type is
   * the required parameter, while the name is optional and if not set, give the
   * default value "player".
   * 
   * @author Ke Chen
   *
   */
  public static class Builder {
    private PlayerType type;
    private String name = "Player";

    public Builder(PlayerType type) {
      this.type = type;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public HumanPlayer build() {
      return new HumanPlayer(this);
    }
  }

  private HumanPlayer(Builder builder) {
    this.name = builder.name;
    this.type = builder.type;
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
    if (col < 0 || col >= GameBoard.COLNUM) {
      throw new ArrayIndexOutOfBoundsException();
    }
    GameBoard gameBoard = GameBoard.getInstance();
    int row = gameBoard.setBoard(col, this);
    return new Coordinate(row, col);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof HumanPlayer)) {
      return false;
    }
    HumanPlayer hp = (HumanPlayer) obj;
    if (!hp.name.equals(this.name) || hp.type != this.type) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + (this.name == null ? 0 : this.name.hashCode());
    result = 31 * result + (this.type == null ? 0 : this.type.hashCode());
    return result;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("HumanPlayer [");
    sb.append("name = ");
    sb.append(this.name);
    sb.append(", type = ");
    sb.append(this.type);
    sb.append("]");
    return sb.toString();
  }
}
