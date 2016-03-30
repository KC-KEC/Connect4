package edu.nyu.cs.pqs.connect4;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * This class creates unit test for HumanPlayer class.
 * 
 * @author Ke Chen
 *
 */
public class HumanPlayerTest {

  private HumanPlayer player1;
  private HumanPlayer player2;

  /**
   * Create two HumanPlayer objects for each test.
   */
  @Before
  public void setup() {
    player1 = new HumanPlayer.Builder(PlayerType.PLAYER1).name("Player1")
        .build();
    player2 = new HumanPlayer.Builder(PlayerType.PLAYER2).name("Player2")
        .build();
  }

  /**
   * Test if equals method would return true on two different HumanPlayer
   * objects that have the same type and name.
   */
  @Test
  public void testEquals_returnTrue() {
    HumanPlayer anotherPlayer1 = new HumanPlayer.Builder(PlayerType.PLAYER1)
        .name("Player1").build();
    assertTrue(player1.equals(anotherPlayer1));
  }

  /**
   * Test if equals method would return false on two different HumanPlayer
   * objects that have the different type and name.
   */
  @Test
  public void testEquals_returnFalse() {
    assertFalse(player1.equals(player2));
  }

  /**
   * Test if the hashCode is the same for two different HumanPlayer objects that
   * have the same type and name.
   */
  @Test
  public void testHashCode_equals() {
    HumanPlayer anotherPlayer1 = new HumanPlayer.Builder(PlayerType.PLAYER1)
        .name("Player1").build();
    assertEquals(player1.hashCode(), anotherPlayer1.hashCode());
  }

  /**
   * Test if the hashCode is different for two different HumanPlayer objects
   * that have the different type and name.
   */
  @Test
  public void testHashCode_notEquals() {
    assertNotEquals(player1.hashCode(), player2.hashCode());
  }

  /**
   * Test if player could successfully make a move.
   */
  @Test
  public void testMakeMove_success() {
    int[][] board = GameBoard.getInstance().getBoard();
    assertEquals(0, board[5][0]);
    Coordinate pos = player1.makeMove(0);
    assertEquals(5, pos.getX());
    assertEquals(0, pos.getY());
    board = GameBoard.getInstance().getBoard();
    assertEquals(1, board[5][0]);
  }

  /**
   * Test if exception would be thrown when player set the board in a
   * non-existing position.
   */
  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void testMakeMove_fail() {
    player1.makeMove(-1);
  }
}
