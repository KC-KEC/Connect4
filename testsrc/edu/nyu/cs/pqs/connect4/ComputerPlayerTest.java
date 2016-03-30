package edu.nyu.cs.pqs.connect4;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * This class creates unit test for ComputerPlayer class.
 * 
 * @author Ke Chen
 *
 */
public class ComputerPlayerTest {

  private GameBoard gameBoard;
  private ComputerPlayer computer1;
  private ComputerPlayer computer2;

  /**
   * Get the singleton instance of the GameBoard and create two ComputerPlayer
   * objects for each test.
   */
  @Before
  public void setup() {
    gameBoard = GameBoard.getInstance();
    computer1 = new ComputerPlayer("Computer1", PlayerType.COMPUTER);
    computer2 = new ComputerPlayer("Computer2", PlayerType.COMPUTER);
  }

  /**
   * Create a Computer player that has a different PlayerType than COMPUTER for
   * equals() testing.
   * 
   * @return A ComputerPlayer reference.
   */
  private ComputerPlayer createOtherTypeComputerPlayer() {
    return new ComputerPlayer("Computer1", PlayerType.PLAYER1);
  }

  /**
   * Set game board to one step away from the winning of computer player.
   */
  private void setBoardToMakeComputerWin() {
    ComputerPlayer computer = new ComputerPlayer("Computer1",
        PlayerType.COMPUTER);
    for (int i = 0; i < 3; i++) {
      gameBoard.setBoard(0, computer);
    }
  }

  /**
   * Test equals() method on the true condition.
   */
  @Test
  public void testEquals_returnTrue() {
    ComputerPlayer anotherComputer1 = new ComputerPlayer("Computer1",
        PlayerType.COMPUTER);
    assertTrue(computer1.equals(computer1));
    assertTrue(computer1.equals(anotherComputer1));
  }

  /**
   * Test equals() method on the false condition.
   */
  @Test
  public void testEquals_returnFalse() {
    ComputerPlayer otherComputer = createOtherTypeComputerPlayer();
    assertFalse(computer1.equals(computer2));
    assertFalse(computer1.equals(otherComputer));
  }

  /**
   * Test if two different ComputerPlayer objects with the same type and name
   * have the same hashCode.
   */
  @Test
  public void testHashCode_equals() {
    ComputerPlayer anotherComputer1 = new ComputerPlayer("Computer1",
        PlayerType.COMPUTER);
    assertEquals(computer1.hashCode(), anotherComputer1.hashCode());
  }

  /**
   * Test if two different ComputerPlayer objects with different type and name
   * have the different hashCode.
   */
  @Test
  public void testHashCode_notEquals() {
    assertNotEquals(computer1.hashCode(), computer2.hashCode());
  }

  /**
   * Test if the ComputerPlayer would automatically choose the move that results
   * a win.
   */
  @Test
  public void testMakeMove_makeComputerWin() {
    setBoardToMakeComputerWin();
    int[][] board = gameBoard.getBoard();
    assertEquals(0, board[2][0]);
    Coordinate pos = computer1.makeMove(-1);
    assertEquals(2, pos.getX());
    assertEquals(0, pos.getY());
    board = gameBoard.getBoard();
    assertEquals(2, board[2][0]);
    assertTrue(gameBoard.isWin(pos, computer1));
  }
}
