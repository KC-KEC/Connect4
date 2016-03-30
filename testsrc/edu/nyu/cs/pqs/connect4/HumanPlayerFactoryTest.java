package edu.nyu.cs.pqs.connect4;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * This class creates unit test for HumanPlayerFactory class.
 * 
 * @author Ke Chen
 *
 */
public class HumanPlayerFactoryTest {

  /**
   * Test if the factory could successfully produce HumanPlayer objects.
   */
  @Test
  public void testCreatePlayer() {
    HumanPlayerFactory humanFactory = new HumanPlayerFactory();
    Player player = humanFactory.createPlayer("Player1", PlayerType.PLAYER1);
    assertTrue(player instanceof HumanPlayer);
    assertEquals(player.getName(), "Player1");
    assertEquals(player.getType(), PlayerType.PLAYER1);
  }
}
