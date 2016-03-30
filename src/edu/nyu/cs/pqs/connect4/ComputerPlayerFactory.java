package edu.nyu.cs.pqs.connect4;

/**
 * ComputerPlayerFactory class is a singleton factory that implements
 * PlayerFactory interface to produce Computer Player instance. Since the
 * ConnectFour is a two player game, only one computer opponent is allowed to
 * exist, so the factory should produce no more than one instance of Computer
 * Player. While this class is a singleton factory, the ComputerPlayer itself is
 * not a singleton.
 * 
 * @author Ke Chen
 * @see PlayerFactory
 * @see ComputerPlayer
 */
public class ComputerPlayerFactory implements PlayerFactory {
  private ComputerPlayer instance = null;

  @Override
  public Player createPlayer(String name, PlayerType type) {
    if (instance == null) {
      instance = new ComputerPlayer(name, type);
    }
    return instance;
  }
}
