package edu.nyu.cs.pqs.connect4;

/**
 * Player interface defines the behavior of each player should have.
 * 
 * @author Ke Chen
 * @see HumanPlayer
 * @see ComputerPlayer
 */
interface Player {

  /**
   * Get the name of the player
   * 
   * @return A String variable that represents the name of the player.
   */
  public String getName();

  /**
   * Get the type of the player.
   * 
   * @return A PlayerType enum variable that represents the type of the player.
   */
  public PlayerType getType();

  /**
   * Defines how to make next move for the player.
   * 
   * @param col
   *          represents the column in which the player is going to make the
   *          move.
   * @return A Coordinate object that represents the position of the move the
   *         player made.
   * @see Coordinate
   */
  public Coordinate makeMove(int col);
}
