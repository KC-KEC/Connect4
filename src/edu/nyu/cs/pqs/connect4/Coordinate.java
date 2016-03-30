package edu.nyu.cs.pqs.connect4;

/**
 * Coordinate class is an immutable class that represents each cell in the game
 * board.
 * 
 * @author Ke Chen
 *
 */
public class Coordinate {
  private final int x;
  private final int y;

  /**
   * Constructor takes the two integers to represent the x-coordinate and
   * y-coordinate.
   * 
   * @param x
   *          An integer to represent x-coordinate
   * @param y
   *          An integer to represent y-coordinate
   */
  public Coordinate(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Get x-coordinate
   * 
   * @return int varaible that stores the x-coordinate
   */
  public int getX() {
    return x;
  }

  /**
   * Get y-coordinate
   * 
   * @return int variable that stores the y-coordinate
   */
  public int getY() {
    return y;
  }
}