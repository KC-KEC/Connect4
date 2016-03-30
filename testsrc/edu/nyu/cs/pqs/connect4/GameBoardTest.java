package edu.nyu.cs.pqs.connect4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * This class creates unit test for GameBoard class.
 * 
 * @author Ke Chen
 *
 */
public class GameBoardTest {

  private GameBoard gameBoard = GameBoard.getInstance();
  private ComputerPlayer computer;
  private HumanPlayer human;

  /**
   * Create a HumanPlayer and a ComputerPlayer for each test. And reset the game
   * board for each test.
   */
  @Before
  public void setup() {
    gameBoard.resetBoard();
    computer = new ComputerPlayer("Computer", PlayerType.COMPUTER);
    human = new HumanPlayer.Builder(PlayerType.PLAYER1).name("Player1").build();
  }

  /**
   * Test if two kinds of player can successfully set the board.
   */
  @Test
  public void testSetBoard() {
    assertEquals(-1, gameBoard.setBoard(-1, computer));
    assertEquals(-1, gameBoard.setBoard(GameBoard.COLNUM, computer));
    for (int i = GameBoard.ROWNUM - 1; i >= 0; i -= 2) {
      assertEquals(i, gameBoard.setBoard(0, computer));
      assertEquals(i - 1, gameBoard.setBoard(0, human));
    }
    assertEquals(-1, gameBoard.setBoard(0, computer));
  }

  /**
   * Test if player can successfully unset board.
   */
  @Test
  public void testUnsetBoard() {
    gameBoard.unsetBoard(-1);
    gameBoard.unsetBoard(GameBoard.COLNUM);
    gameBoard.unsetBoard(0);
    gameBoard.setBoard(0, computer);
    assertEquals(5, gameBoard.getRowTracker()[0]);
    gameBoard.unsetBoard(0);
    assertEquals(6, gameBoard.getRowTracker()[0]);
  }

  /**
   * Test if the board could be reset to 0.
   */
  @Test
  public void testResetBoard() {
    gameBoard.setBoard(0, computer);
    assertEquals(2, gameBoard.getBoard()[5][0]);
    assertEquals(5, gameBoard.getRowTracker()[0]);
    gameBoard.resetBoard();
    assertEquals(0, gameBoard.getBoard()[0][5]);
    assertEquals(6, gameBoard.getRowTracker()[0]);
  }

  /**
   * Test if 4 same pieces in a row would result a win.
   */
  @Test
  public void testIsWin_Horizontal() {
    for (int i = 0; i < 4; i++) {
      gameBoard.setBoard(i, computer);
    }
    assertTrue(gameBoard.isWin(new Coordinate(5, 2), computer));
    assertFalse(gameBoard.isWin(new Coordinate(5, 2), human));

    gameBoard.resetBoard();
    for (int i = 3; i < GameBoard.COLNUM; i++) {
      gameBoard.setBoard(i, human);
    }
    assertTrue(gameBoard.isWin(new Coordinate(5, 4), human));
  }

  /**
   * Test if 4 same pieces in a column would result a win.
   */
  @Test
  public void testIsWin_Vertical() {
    for (int i = 0; i < 4; i++) {
      gameBoard.setBoard(0, computer);
    }
    assertTrue(gameBoard.isWin(new Coordinate(2, 0), computer));
    assertFalse(gameBoard.isWin(new Coordinate(2, 0), human));

    gameBoard.resetBoard();
    for (int i = 0; i < GameBoard.ROWNUM; i++) {
      if (i < 2) {
        gameBoard.setBoard(0, human);
      }
      else {
        gameBoard.setBoard(0, computer);
      }
    }
    assertTrue(gameBoard.isWin(new Coordinate(4, 0), computer));
    assertFalse(gameBoard.isWin(new Coordinate(2, 0), human));
  }

  /**
   * Test if 4 same pieces in diagnose would result a win.
   */
  @Test
  public void testIsWin_Diagnose() {
    gameBoard.setBoard(0, computer);

    gameBoard.setBoard(1, human);
    gameBoard.setBoard(1, computer);

    gameBoard.setBoard(2, human);
    gameBoard.setBoard(2, human);
    gameBoard.setBoard(2, computer);

    gameBoard.setBoard(3, human);
    gameBoard.setBoard(3, human);
    gameBoard.setBoard(3, human);
    gameBoard.setBoard(3, computer);

    assertTrue(gameBoard.isWin(new Coordinate(2, 3), computer));
  }

  /**
   * Test if a full board would result a draw,
   */
  @Test
  public void testTie() {
    assertFalse(gameBoard.isTie());
    for (int i = 0; i < GameBoard.ROWNUM; i++) {
      for (int j = 0; j < GameBoard.COLNUM; j++) {
        gameBoard.setBoard(j, computer);
      }
    }
    assertTrue(gameBoard.isTie());
  }
}
