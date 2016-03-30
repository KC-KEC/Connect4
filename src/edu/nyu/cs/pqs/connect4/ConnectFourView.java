package edu.nyu.cs.pqs.connect4;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
 * ConnectFourView class implements ConnectFourListener interface to serve as an
 * Observer to the ConnectFourModel of this game. The methods in the interface
 * that implements here are called by Model broadcasting and each
 * ConnectFourView object would respond to these method calls properly.
 * 
 * @author Ke Chen
 * @see ConnectFourListener
 * @see ConnectFourModel
 *
 */
public class ConnectFourView implements ConnectFourListener {

  private ConnectFourModel model;
  private JFrame frame;
  private JPanel mainPanel;
  private JPanel boardPanel;
  private JPanel[] columePanel;
  private JLabel[][] viewBoard;
  private JLabel playerIcon;
  private JLabel playerName;
  private JButton finalStatusButton;
  private GridBagConstraints constraint;
  private Player player;
  private boolean isMyTurn = true;
  private GameState gameState;

  /**
   * The constructor of ConnectFourView takes the model reference and a player
   * reference as the parameters. It maps the given player to this View and then
   * register itself into the model's listener list.
   * 
   * @param model
   *          A ConnectFourModel reference.
   * @param player
   *          A Player reference.
   */
  public ConnectFourView(ConnectFourModel model, Player player) {
    this.model = model;
    model.addListener(this);
    this.player = player;
    this.gameState = GameState.STOP;
    this.frame = new JFrame();
    this.mainPanel = new JPanel(new GridBagLayout());
    this.boardPanel = new JPanel(new GridBagLayout());
    this.columePanel = new JPanel[GameBoard.COLNUM];
    this.constraint = new GridBagConstraints();
    this.viewBoard = new JLabel[GameBoard.ROWNUM + 1][GameBoard.COLNUM];
  }

  private MouseListener createMouseListener(int col) {
    return new MouseListener() {

      @Override
      public void mouseClicked(MouseEvent e) {
        if (gameState != GameState.RUNNING) {
          return;
        }
        if (isMyTurn) {
          model.mouseClicked(player, col);
        }
        else {
          finalStatusButton.setText("Not Your Turn!");
        }
      }

      @Override
      public void mousePressed(MouseEvent e) {
        // Do nothing
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        // Do nothing
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        if (gameState != GameState.RUNNING) {
          return;
        }
        if (isMyTurn) {
          model.mouseEntered(player, col);
        }
      }

      @Override
      public void mouseExited(MouseEvent e) {
        if (gameState != GameState.RUNNING) {
          return;
        }
        if (isMyTurn) {
          model.mouseExited(player, col);
        }
      }
    };// end of Inner Anonymous class
  }

  private void initBoard() {
    // create board
    for (int i = 0; i < GameBoard.ROWNUM + 1; i++) {
      for (int j = 0; j < GameBoard.COLNUM; j++) {
        if (i == 0) {
          columePanel[j] = new JPanel(new GridBagLayout());
          columePanel[j].setBackground(Color.WHITE);
          columePanel[j].addMouseListener(createMouseListener(j));

          try {
            BufferedImage redPiece = ImageIO
                .read(new File("image/redpiece.jpg"));
            JLabel pieceLabel = new JLabel(new ImageIcon(redPiece));
            viewBoard[i][j] = pieceLabel;
            constraint.gridx = j;
            constraint.gridy = i;
            constraint.ipady = 25;
            columePanel[j].add(viewBoard[i][j], constraint);
          } catch (IOException e) {
            int result = JOptionPane.showConfirmDialog(null,
                "ERROR: Image files not found", "ERROR",
                JOptionPane.CLOSED_OPTION);
            if (result == JOptionPane.YES_OPTION) {
              System.exit(0);
            }
          }
        }
        else {
          try {
            BufferedImage cellImage = ImageIO.read(new File("image/cell.png"));
            JLabel imageLabel = new JLabel(new ImageIcon(cellImage));
            viewBoard[i][j] = imageLabel;
            constraint.gridx = j;
            constraint.gridy = i;
            constraint.ipady = 0;
            columePanel[j].add(viewBoard[i][j], constraint);
          } catch (IOException e) {
            int result = JOptionPane.showConfirmDialog(null,
                "ERROR: Image files not found", "ERROR",
                JOptionPane.CLOSED_OPTION);
            if (result == JOptionPane.YES_OPTION) {
              System.exit(0);
            }
          }
        }
      }
    }

    for (int j = 0; j < GameBoard.COLNUM; j++) {
      constraint.gridx = j;
      boardPanel.add(columePanel[j], constraint);
    }

    // Add playerIcon component
    BufferedImage playerImage;
    playerIcon = null;
    try {
      if (player.getType() == PlayerType.PLAYER1) {
        playerImage = ImageIO.read(new File("image/redpiece.jpg"));
      }
      else {
        playerImage = ImageIO.read(new File("image/yellowpiece.jpg"));
      }
      playerIcon = new JLabel(new ImageIcon(playerImage));
    } catch (IOException e) {
      int result = JOptionPane.showConfirmDialog(null,
          "ERROR: Image files not found", "ERROR", JOptionPane.CLOSED_OPTION);
      if (result == JOptionPane.YES_OPTION) {
        System.exit(0);
      }
    }
    constraint.gridy = 0;
    constraint.gridx = 0;
    constraint.anchor = GridBagConstraints.WEST;
    constraint.ipadx = 50;
    constraint.ipady = 50;
    mainPanel.add(playerIcon, constraint);

    // Add PlayerName Component
    playerName = new JLabel(player.getName());
    playerName.setFont(new Font(Font.MONOSPACED, Font.BOLD + Font.ITALIC, 35));
    constraint.gridx = 1;
    constraint.ipadx = 50;
    constraint.ipady = 50;
    playerName.setBackground(Color.RED);
    mainPanel.add(playerName, constraint);

    // Add finalStatusLabel
    finalStatusButton = new JButton();
    finalStatusButton.setBorderPainted(false);
    finalStatusButton.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
    finalStatusButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (gameState == GameState.STOP) {
          model.returnToMenu();
        }
      }
    });
    constraint.anchor = GridBagConstraints.EAST;
    constraint.gridx = 2;
    constraint.ipady = 25;
    mainPanel.add(finalStatusButton, constraint);

    // Add boardPanel
    constraint.gridx = 0;
    constraint.gridy = 1;
    constraint.gridwidth = 3;
    mainPanel.add(boardPanel, constraint);

    // Setup Frame
    mainPanel.setBackground(Color.WHITE);
    boardPanel.setBackground(Color.WHITE);
    frame.getContentPane().add(mainPanel);
    frame.setTitle("Connect Four Game");
    frame.setSize(550, 700);
    if (player.getType() == PlayerType.PLAYER1) {
      frame.setLocation(0, 0);
    }
    else {
      frame.setLocation(800, 0);
    }
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame.setVisible(true);
  }

  @Override
  public void closeWindow() {
    frame.setVisible(false);
  }

  @Override
  public void gameStart() {
    initBoard();
    this.gameState = GameState.RUNNING;
  }

  @Override
  public void mouseInEachColume(Player whichPlayer, int col) {
    for (int j = 0; j < viewBoard[0].length; j++) {
      if (j != col) {
        BufferedImage emptyImage;
        try {
          emptyImage = ImageIO.read(new File("image/emptypiece.png"));
          viewBoard[0][j].setIcon(new ImageIcon(emptyImage));
        } catch (IOException e) {
          int result = JOptionPane.showConfirmDialog(null,
              "ERROR: Image files not found", "ERROR",
              JOptionPane.CLOSED_OPTION);
          if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
          }
        }
      }
    }
  }

  @Override
  public void mouseOutEachColume(Player whichPlayer, int col) {
    for (int j = 0; j < viewBoard[0].length; j++) {
      BufferedImage emptyImage;
      try {
        if (whichPlayer.getType() == PlayerType.PLAYER1) {
          emptyImage = ImageIO.read(new File("image/redpiece.jpg"));
        }
        else {
          emptyImage = ImageIO.read(new File("image/yellowpiece.jpg"));
        }
        viewBoard[0][j].setIcon(new ImageIcon(emptyImage));
      } catch (IOException e) {
        int result = JOptionPane.showConfirmDialog(null,
            "ERROR: Image files not found", "ERROR", JOptionPane.CLOSED_OPTION);
        if (result == JOptionPane.YES_OPTION) {
          System.exit(0);
        }
      }
    }
  }

  @Override
  public void mouseClickEachColume(Player whichPlayer, Coordinate pos) {
    int row = pos.getX() + 1;
    int col = pos.getY();
    BufferedImage filledCellImage;
    try {
      if (whichPlayer.getType() == PlayerType.PLAYER1) {
        filledCellImage = ImageIO.read(new File("image/redcell.png"));
        viewBoard[row][col].setIcon(new ImageIcon(filledCellImage));
      }
      else {
        filledCellImage = ImageIO.read(new File("image/yellowcell.png"));
        viewBoard[row][col].setIcon(new ImageIcon(filledCellImage));
      }
      finalStatusButton.setText("");
    } catch (IOException e) {
      int result = JOptionPane.showConfirmDialog(null,
          "ERROR: Image files not found", "ERROR", JOptionPane.CLOSED_OPTION);
      if (result == JOptionPane.YES_OPTION) {
        System.exit(0);
      }
    }
  }

  @Override
  public void switchTurnTo(Player whichPlayer) {
    if (!whichPlayer.equals(this.player)) {
      this.isMyTurn = false;
    }
    else {
      this.isMyTurn = true;
      finalStatusButton.setText("");
    }
  }

  @Override
  public void gameOverWithWinner(Player whichPlayer) {
    gameState = GameState.STOP;
    if (!whichPlayer.equals(this.player)) {
      finalStatusButton.setBorderPainted(true);
      finalStatusButton.setText("You lose!");
    }
    else {
      finalStatusButton.setBorderPainted(true);
      finalStatusButton.setText("You win!");
    }
  }

  @Override
  public void gameTie() {
    gameState = GameState.STOP;
    finalStatusButton.setBorderPainted(true);
    finalStatusButton.setText("DRAW");
  }

  @Override
  public void columeFull(Player whichPlayer) {
    if (!whichPlayer.equals(this.player)) {
      return;
    }
    finalStatusButton.setText("Column is Full");
  }

  /**
   * Get if it's current player's turn.
   * 
   * @return A boolean variable that if true, it's turn to the player that
   *         correspond to this View. Otherwise, it's not the player's turn.
   */
  public boolean getIsMyTurn() {
    return this.isMyTurn;
  }

  /**
   * Get the player that correspond to this View.
   * 
   * @return A Player reference.
   */
  public Player getPlayer() {
    return this.player;
  }

  /**
   * Get the GameState, either RUNNING or STOP.
   * 
   * @return A enum object of GameState.
   */
  public GameState getGameState() {
    return this.gameState;
  }
}
