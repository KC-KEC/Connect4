package edu.nyu.cs.pqs.connect4;

import java.util.ArrayList;
import java.util.List;

/**
 * ConnectFourModel class serves as the Observable part in this game and
 * broadcast information to all Views that is observing this model. So that all
 * Views will properly perform what Model tells them to do.
 * 
 * @author Ke Chen
 * @see ConnectFourView
 */
public class ConnectFourModel {
  private GameBoard gameBoard;
  private List<ConnectFourListener> listeners;
  private GameMode mode;
  private Player player1;
  private Player player2;

  /**
   * The constructor takes three parameters to represent the GameMode and both
   * players. The GameMode is either <code>SINGLEPLAYER</code> or
   * <code>TWOPLAYER</code>. <code>player1</code> is always HumanPlayer and
   * <code>player2</code> is either HumanPlayer (TWOPLAYER MODE) or
   * ComputerPlayer (SINGLEPLAYER MODE).
   * 
   * @param mode
   *          A GameMode enum variable to represent the mode of the game.
   * @param player1
   *          A Player reference.
   * @param player2
   *          A Player reference.
   */
  public ConnectFourModel(GameMode mode, Player player1, Player player2) {
    this.gameBoard = GameBoard.getInstance();
    this.listeners = new ArrayList<ConnectFourListener>();
    this.mode = mode;
    this.player1 = player1;
    this.player2 = player2;
  }

  /**
   * Register the given <code>listener</code> to this model.
   * 
   * @param listener
   *          A ConnectFourListener reference.
   */
  public void addListener(ConnectFourListener listener) {
    listeners.add(listener);
  }

  /**
   * Unregistered the specified <code>listener</code> from this model.
   * 
   * @param listener
   *          A ConnectFourListener reference.
   */
  public void removeListener(ConnectFourListener listener) {
    listeners.remove(listener);
  }

  /**
   * Get the current game mode.
   * 
   * @return an Enum object of GameMode
   */
  public GameMode getGameMode() {
    return this.mode;
  }

  /**
   * Get the copy of registered <code>listeners</code> list.
   * 
   * @return A copy of <code>listeners</code> list.
   */
  public List<ConnectFourListener> getListeners() {
    List<ConnectFourListener> result = new ArrayList<ConnectFourListener>(
        this.listeners);
    return result;
  }

  /**
   * Get the reference to player1.
   * 
   * @return A reference to player1 object.
   */
  public Player getPlayer1() {
    return this.player1;
  }

  /**
   * Get the reference to player2.
   * 
   * @return A reference to player2 object.
   */
  public Player getPlayer2() {
    return this.player2;
  }

  /**
   * Broadcast to all registered Views to start the game.
   */
  public void startGame() {
    fireGameStartEvent();
  }

  /**
   * This method interact with the mouseEntered listener in each View. It
   * broadcasts to all registered Views that <code>whichPlayer</code> has it's
   * mouse entered the column: <code>col</code> on the game board.
   * 
   * @param whichPlayer
   *          A Player reference.
   * @param col
   *          An integer to represents the column on the game board.
   */
  public void mouseEntered(Player whichPlayer, int col) {
    fireMouseEnterEvent(whichPlayer, col);
  }

  /**
   * This method interact with the mouseExited listener in each View. It
   * broadcasts to all registered Views that <code>whichPlayer</code> has
   * removed it's mouse from the column: <code>col</code> on the game board.
   * 
   * @param whichPlayer
   *          A Player reference.
   * @param col
   *          An integer to represents the column on the game board.
   */
  public void mouseExited(Player whichPlayer, int col) {
    fireMouseExitEvent(whichPlayer, col);
  }

  /**
   * This method interact with the mouseClicked listener in each View. It
   * broadcasts to all registered Views that <code>whichPlayer</code> has
   * clicked the column: <code>col</code> on the game board. Call makeMove()
   * method to set board and do winning or tie checking. If either condition is
   * satisfied, broadcast to all registered Views that the game is over with the
   * final status. Otherwise, switch turn to another player. If it's a
   * SINGLEPLAYER mode game, the computer player would also make next step and
   * do win and tie checking again.
   * 
   * @param whichPlayer
   *          A Player reference.
   * @param col
   *          An integer to represents the column on the game board.
   */
  public void mouseClicked(Player whichPlayer, int col) {
    Coordinate humanPos = whichPlayer.makeMove(col);

    if (humanPos.getX() >= 0 && humanPos.getX() < GameBoard.ROWNUM) {
      fireMakeMoveEvent(whichPlayer, humanPos);

      if (isWin(humanPos, whichPlayer)) {
        fireWinEvent(whichPlayer);
        return;
      }
      else if (isTie()) {
        fireTieEvent();
        return;
      }

      switchTurn(whichPlayer);

      if (mode == GameMode.SINGLEPLAYER) {
        Coordinate computerPos = player2.makeMove(-1);
        fireMakeMoveEvent(player2, computerPos);
        if (isWin(computerPos, player2)) {
          fireWinEvent(player2);
          return;
        }
        else if (isTie()) {
          fireTieEvent();
          return;
        }
        switchTurn(player2);
      }
    }
    else if (humanPos.getX() == -1) {
      fireColumeFullEvent(whichPlayer);
    }
  }

  /**
   * Close the game window and go back to start menu. Also reset the game board.
   */
  public void returnToMenu() {
    fireCloseWindowEvent();
    gameBoard.resetBoard();
    ConnectFourStartMenu.getInstance().getFrame().setVisible(true);
  }

  private boolean isWin(Coordinate pos, Player whichPlayer) {
    return gameBoard.isWin(pos, whichPlayer);
  }

  private boolean isTie() {
    return gameBoard.isTie();
  }

  private void switchTurn(Player whichPlayer) {
    if (player1.getType() == whichPlayer.getType()) {
      switchTurnTo(player2);
    }
    else {
      switchTurnTo(player1);
    }
  }

  private void switchTurnTo(Player whichPlayer) {
    fireSwitchTurnEvent(whichPlayer);
  }

  private void fireSwitchTurnEvent(Player whichPlayer) {
    for (ConnectFourListener listener : listeners) {
      listener.switchTurnTo(whichPlayer);
    }
  }

  private void fireGameStartEvent() {
    for (ConnectFourListener listener : listeners) {
      listener.gameStart();
    }
  }

  private void fireMouseEnterEvent(Player whichPlayer, int col) {
    for (ConnectFourListener listener : listeners) {
      listener.mouseInEachColume(whichPlayer, col);
    }
  }

  private void fireMouseExitEvent(Player whichPlayer, int col) {
    for (ConnectFourListener listener : listeners) {
      listener.mouseOutEachColume(whichPlayer, col);
    }
  }

  private void fireMakeMoveEvent(Player whichPlayer, Coordinate pos) {
    for (ConnectFourListener listener : listeners) {
      listener.mouseClickEachColume(whichPlayer, pos);
    }
  }

  private void fireWinEvent(Player whichPlayer) {
    for (ConnectFourListener listener : listeners) {
      listener.gameOverWithWinner(whichPlayer);
    }
  }

  private void fireTieEvent() {
    for (ConnectFourListener listener : listeners) {
      listener.gameTie();
    }
  }

  private void fireColumeFullEvent(Player whichPlayer) {
    for (ConnectFourListener listener : listeners) {
      listener.columeFull(whichPlayer);
    }
  }

  private void fireCloseWindowEvent() {
    for (ConnectFourListener listener : listeners) {
      listener.closeWindow();
    }
  }
}
