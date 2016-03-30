package edu.nyu.cs.pqs.connect4;

/**
 * ConnectFourApp class is the launcher of this ConnectFour game. The only
 * main() method of this project sits in this class to launch the game and show
 * the start menu.
 * 
 * @author Ke Chen
 *
 */
public class ConnectFourApp {

  private void startGame() {
    ConnectFourStartMenu.getInstance();// singleton
  }

  public static void main(String[] args) {
    new ConnectFourApp().startGame();
  }
}
