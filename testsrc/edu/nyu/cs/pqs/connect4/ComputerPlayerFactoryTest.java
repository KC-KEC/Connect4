package edu.nyu.cs.pqs.connect4;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * This class creates unit test for ComputerPlayerFactory class.
 * 
 * @author Ke Chen
 *
 */
public class ComputerPlayerFactoryTest {

  private ComputerPlayerFactory computerFactory;

  /**
   * Create a new singleton factory of ComputerFactory for each test.
   */
  @Before
  public void setup() {
    computerFactory = new ComputerPlayerFactory();
  }

  /**
   * Test if the computerFactory could produce a normal ComputerPlayer instance.
   */
  @Test
  public void testCreatePlayer() {
    Player player = computerFactory.createPlayer("Computer",
        PlayerType.COMPUTER);
    assertTrue(player instanceof ComputerPlayer);
    assertEquals(player.getName(), "Computer");
    assertEquals(player.getType(), PlayerType.COMPUTER);
  }

  /**
   * Test if the coumputerFactory would produce more than one ComputerPlayer
   * instance.
   */
  @Test
  public void testCreatePlayer_anotherComputer() {
    computerFactory.createPlayer("Computer", PlayerType.COMPUTER);
    Player player = computerFactory.createPlayer("AnotherComputer",
        PlayerType.COMPUTER);
    assertTrue(player instanceof ComputerPlayer);
    assertEquals(player.getName(), "Computer");
    assertNotEquals(player.getName(), "AnotherComputer");
  }
}
