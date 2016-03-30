package edu.nyu.cs.pqs.connect4;

/**
 * ConnectFourListener interface provides the required behaviors that each
 * listener should implement so that when registered into Model, the model
 * object could properly interact with or broadcast information to all
 * listeners.
 * 
 * @author Ke Chen
 * @see ConnectFourView
 *
 */
public interface ConnectFourListener {

  /**
   * Start a new game by setting up the graphic user interface and change the
   * GameState from <code>STOP</code> to <code>RUNNING</code>.
   */
  public void gameStart();

  /**
   * Adding an icon above the specified column: <code>col</code> in the game
   * board to show that the player's mouse is in which column of the game board.
   * When the parameter <code>whichPlayer</code> is not the player for current
   * View, this method just returned without doing anything.
   * 
   * @param whichPlayer
   *          A Player reference.
   * @param col
   *          An integer represents which column on the game board.
   */
  public void mouseInEachColume(Player whichPlayer, int col);

  /**
   * Removing the icon above the specified column: <code>col</code> in the game
   * board to show that the player's mouse is out of the given column of the
   * game board. When the parameter <code>whichPlayer</code> is not the player
   * for current View, this method just returned without doing anything.
   * 
   * @param whichPlayer
   *          A Player reference.
   * @param col
   *          An integer represents the column on the game board.
   */
  public void mouseOutEachColume(Player whichPlayer, int col);

  /**
   * Filling the empty cell in the specified position: <code>pos</code> in the
   * game board to show the moving behavior of the given player. When the
   * parameter <code>whichPlayer</code> is not the player for current View, this
   * method just returned without doing anything.
   * 
   * @param whichPlayer
   *          A Player reference.
   * @param pos
   *          A Coordinate reference represents the position on the game board.
   */
  public void mouseClickEachColume(Player whichPlayer, Coordinate pos);

  /**
   * Switch turn to specified <code>player</code>. Before getting its own turn,
   * any behavior of the <code>player</code> has no effect to the game board.
   * 
   * @param whichPlayer
   *          A Player reference.
   */
  public void switchTurnTo(Player whichPlayer);

  /**
   * Set the game state to stop the game and show the win or lose message to
   * GUI. If the specified <code>player</code> is the player for current View,
   * winning message would be shown. Otherwise show the lose message. When the
   * game state is set to <code>STOP</code>, any further behavior of current
   * View has no effect.
   * 
   * @param whichPlayer
   *          A Player reference.
   */
  public void gameOverWithWinner(Player whichPlayer);

  /**
   * Set the game state to stop the game and show the draw message to GUI. When
   * the game state is set to <code>STOP</code>, any further behavior of current
   * View has no effect.
   */
  public void gameTie();

  /**
   * Show the message on GUI to indicate that the chosen column by the
   * <code>player</code> is already full. When the parameter
   * <code>whichPlayer</code> is not the player for current View, this method
   * just returned without doing anything.
   * 
   * @param whichPlayer
   *          A Player reference.
   */
  public void columeFull(Player whichPlayer);

  /**
   * Set the JFrame of current View to not visible.
   */
  public void closeWindow();
}
