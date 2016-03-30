package edu.nyu.cs.pqs.connect4;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * ConnectFourStartMenu class is a singleton class that could be instantiate
 * only once. It shows the start menu of ConnectFour game and let the user to
 * choose the mode of the game.
 * 
 * @author Ke Chen
 *
 */
public class ConnectFourStartMenu {

  private Player player1 = null;
  private Player player2 = null;
  private ConnectFourModel model;
  private static ConnectFourStartMenu instance = null;
  private GameMode mode = null;
  private JFrame frame = new JFrame();

  private ConnectFourStartMenu() {
    JPanel bottomPanel = new JPanel();
    BufferedImage mainImage;
    JLabel label = null;
    try {
      mainImage = ImageIO.read(new File("image/main.jpg"));
      label = new JLabel(new ImageIcon(mainImage));
    } catch (IOException e) {
      int result = JOptionPane.showConfirmDialog(null,
          "ERROR: Image files not found", "ERROR", JOptionPane.CLOSED_OPTION);
      if (result == JOptionPane.YES_OPTION) {
        System.exit(0);
      }
    }
    label.setLayout(new BorderLayout());
    JButton singlePlayerButton = new JButton("Single Player");
    singlePlayerButton.setPreferredSize(new Dimension(150, 50));
    JButton twoPlayerButton = new JButton("Two Player");
    twoPlayerButton.setPreferredSize(new Dimension(150, 50));

    bottomPanel.add(singlePlayerButton, BorderLayout.WEST);
    bottomPanel.add(twoPlayerButton, BorderLayout.EAST);
    label.add(bottomPanel, BorderLayout.PAGE_END);
    frame.getContentPane().add(label);
    frame.setTitle("Connect Four Game");
    frame.setSize(800, 450);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame.setVisible(true);

    singlePlayerButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        frame.setVisible(false);
        singleModeButtonPressed();
      }
    });

    twoPlayerButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        frame.setVisible(false);
        twoPlayerModeButtonPressed();
      }
    });
  }

  private void singleModeButtonPressed() {
    mode = GameMode.SINGLEPLAYER;
    PlayerFactory humanFactory = new HumanPlayerFactory();
    PlayerFactory computerFactory = new ComputerPlayerFactory();
    this.player1 = humanFactory.createPlayer("Player1", PlayerType.PLAYER1);
    this.player2 = computerFactory.createPlayer("Computer",
        PlayerType.COMPUTER);
    model = new ConnectFourModel(mode, this.player1, this.player2);
    new ConnectFourView(model, player1);
    model.startGame();
  }

  private void twoPlayerModeButtonPressed() {
    mode = GameMode.TWOPLAYER;
    PlayerFactory humanFactory = new HumanPlayerFactory();
    this.player1 = humanFactory.createPlayer("Player1", PlayerType.PLAYER1);
    this.player2 = humanFactory.createPlayer("Player2", PlayerType.PLAYER2);
    model = new ConnectFourModel(mode, this.player1, this.player2);
    new ConnectFourView(model, this.player1);
    new ConnectFourView(model, this.player2);
    model.startGame();
  }

  /**
   * Get the mode of the game.
   * 
   * @return An enum object of GameMode
   */
  public GameMode getMode() {
    return mode;
  }

  /**
   * Get the only instance of this class, instantiate the instance only if the
   * instance is null.
   * 
   * @return The only instance of ConnectFourStartMenu class.
   */
  public static ConnectFourStartMenu getInstance() {
    if (instance == null) {
      instance = new ConnectFourStartMenu();
    }
    return instance;
  }

  /**
   * Get the reference to JFrame object of ConnectFourStartMenu. This method is
   * called when the return button on the game window has been clicked.
   * 
   * @return A reference to JFrame object of ConnectFourStartMenu class.
   */
  public JFrame getFrame() {
    return frame;
  }
}
