package edu.nyu.cs.pqs.connect4;

/**
 * HumanPlayerFactory class implements the PlayerFactory Interface to produce
 * HumanPlayer objects.
 * 
 * @author Ke Chen
 * @see HumanPlayer
 *
 */
public class HumanPlayerFactory implements PlayerFactory {

  @Override
  public Player createPlayer(String name, PlayerType type) {
    return new HumanPlayer.Builder(type).name(name).build();
  }

}
