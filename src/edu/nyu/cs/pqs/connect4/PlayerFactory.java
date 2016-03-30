package edu.nyu.cs.pqs.connect4;

/**
 * PlayerFactory defines the create method that each concrete player factory
 * should implement according to their own creating style.
 * 
 * @author Ke Chen
 * @see HumanPlayerFactory
 * @see ComputerPlayerFactory
 *
 */
interface PlayerFactory {
  /**
   * Create a Player object.
   * 
   * @param name
   *          A String variable represents Player's name
   * @param type
   *          A PlayerType enum variable represents Player's type
   * @return A Player object.
   */
  public Player createPlayer(String name, PlayerType type);
}
