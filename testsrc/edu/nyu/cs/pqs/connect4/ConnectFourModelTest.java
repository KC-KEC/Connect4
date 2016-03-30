package edu.nyu.cs.pqs.connect4;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * This class creates unit test for ConnectFourModel class.
 * 
 * @author Ke Chen
 *
 */
public class ConnectFourModelTest {

  private Player player1;
  private Player player2;
  private ConnectFourModel model;
  private ConnectFourView view1;
  private ConnectFourView view2;

  /**
   * Set up a two player mode for model.
   */
  @Before
  public void setup() {
    player1 = new HumanPlayer.Builder(PlayerType.PLAYER1).name("Player1")
        .build();
    player2 = new HumanPlayer.Builder(PlayerType.PLAYER2).name("Player2")
        .build();
    model = new ConnectFourModel(GameMode.TWOPLAYER, player1, player2);
    view1 = new ConnectFourView(model, player1);
    view2 = new ConnectFourView(model, player2);
  }

  /**
   * Test addListener methods that add two listeners into the list.
   */
  @Test
  public void testAddListener() {
    assertEquals(model.getListeners().size(), 2);
    assertTrue(model.getListeners().get(0) instanceof ConnectFourView);
    ConnectFourListener newListener = model.getListeners().get(0);
    assertEquals(newListener, view1);
  }

  /**
   * Test if a listener can be removed from the list.
   */
  @Test
  public void testRemoveListener() {
    model.removeListener(view1);
    assertEquals(model.getListeners().size(), 1);
    assertEquals(model.getListeners().get(0), view2);
  }

  /**
   * Test if the startGame() method would change the game state.
   */
  @Test
  public void testStartGame() {
    assertEquals(view1.getGameState(), GameState.STOP);
    assertEquals(view2.getGameState(), GameState.STOP);
    model.startGame();
    assertEquals(view1.getGameState(), GameState.RUNNING);
    assertEquals(view2.getGameState(), GameState.RUNNING);
  }
}
