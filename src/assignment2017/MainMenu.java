/**
 * MainMenu Class
 * 
 * Class representing game graphical user interface menu
 */
package assignment2017;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import assignment2017.codeprovided.Connect4Player;

@SuppressWarnings("serial")
public class MainMenu extends JPanel {

  /** Denotes amount of types of players **/
  final public static int PLAYER_TYPES = 3; // Human, Random, AI

  /** Denotes names of possible players **/
  final public static String[] PLAYER_TYPE_NAMES = {"Human", "Random", "AI"};

  /** Denotes names of images for players **/
  final public static String[] PLAYER_IMGS = {"Human.png", "Random.png", "AI.png"};

  /** Denotes names of images for players when selected **/
  final public static String[] PLAYER_SELECTED_IMGS =
          {"HumanSelected.png", "RandomSelected.png", "AISelected.png"};

  // Instance variables
  private Connect4GuiDisplay frame;
  private JLabel gameTitle;
  private ButtonGroup groupPlayer1;
  private JLabel player1Title;
  private ButtonGroup groupPlayer2;
  private JLabel player2Title;
  private JRadioButton[] radioButtonPlayer1;
  private JPanel radioButtons;
  private JPanel radioButtonsWest;
  private JRadioButton[] radioButtonPlayer2;
  private JPanel radioButtonsEast;
  private JButton playButton;

  public MainMenu(Connect4GuiDisplay frame) {
    this.frame = frame;
    // Set Current Panel to layout
    setLayout(new BorderLayout());
    // Game Title
    this.gameTitle = new JLabel("Connect 4", SwingConstants.CENTER);
    this.gameTitle.setFont(new Font("Sans Serif", Font.PLAIN,
            Connect4GuiDisplay.FONT_SIZE * this.frame.getFontScale() * 2));
    add(this.gameTitle, BorderLayout.NORTH);
    // RadioButtons Panel
    this.player1Title = new JLabel("Player 1:", SwingConstants.CENTER);
    this.player1Title.setFont(new Font("Sans Serif", Font.PLAIN,
            Connect4GuiDisplay.FONT_SIZE * this.frame.getFontScale()));
    this.player2Title = new JLabel("Player 2:", SwingConstants.CENTER);
    this.player2Title.setFont(new Font("Sans Serif", Font.PLAIN,
            Connect4GuiDisplay.FONT_SIZE * this.frame.getFontScale()));
    this.radioButtons = new JPanel(new GridLayout(1, 0));
    add(this.radioButtons, BorderLayout.CENTER);
    int imgWidth = this.frame.getWidth() / 8;
    // Player 1 options
    this.groupPlayer1 = new ButtonGroup();
    this.radioButtonPlayer1 = new JRadioButton[PLAYER_TYPES];
    this.radioButtonsWest = new JPanel(new GridLayout(0, 1));
    this.radioButtonsWest.add(player1Title);
    for (int i = 0; i < PLAYER_TYPES; i++) {
      this.radioButtonPlayer1[i] = new JRadioButton(PLAYER_TYPE_NAMES[i]);
      ImageIcon imageIcon = new ImageIcon(PLAYER_IMGS[i]);
      Image image = imageIcon.getImage();
      Image newimg = image.getScaledInstance(imgWidth, imgWidth, Image.SCALE_SMOOTH);  
      imageIcon = new ImageIcon(newimg);
      this.radioButtonPlayer1[i].setIcon(imageIcon);
      ImageIcon imageIcon2 = new ImageIcon(PLAYER_SELECTED_IMGS[i]);
      Image image2 = imageIcon2.getImage();
      Image newimg2 = image2.getScaledInstance(imgWidth, imgWidth, Image.SCALE_SMOOTH);  
      imageIcon2 = new ImageIcon(newimg2);
      this.radioButtonPlayer1[i].setSelectedIcon(imageIcon2);
      this.radioButtonPlayer1[i].setFont(new Font("Sans Serif", Font.PLAIN,
              Connect4GuiDisplay.FONT_SIZE * this.frame.getFontScale()));
      this.radioButtonPlayer1[i].setHorizontalAlignment(SwingConstants.CENTER);
      this.groupPlayer1.add(this.radioButtonPlayer1[i]);
      this.radioButtonsWest.add(this.radioButtonPlayer1[i]);
    }
    this.radioButtonPlayer1[0].setSelected(true);
    this.radioButtons.add(this.radioButtonsWest);
    // Player 2 options
    this.groupPlayer2 = new ButtonGroup();
    this.radioButtonPlayer2 = new JRadioButton[PLAYER_TYPES];
    this.radioButtonsEast = new JPanel(new GridLayout(0, 1));
    this.radioButtonsEast.add(player2Title);
    for (int i = 0; i < PLAYER_TYPES; i++) {
      this.radioButtonPlayer2[i] = new JRadioButton(PLAYER_TYPE_NAMES[i]);
      ImageIcon imageIcon = new ImageIcon(PLAYER_IMGS[i]);
      Image image = imageIcon.getImage();
      Image newimg = image.getScaledInstance(imgWidth, imgWidth, Image.SCALE_SMOOTH);  
      imageIcon = new ImageIcon(newimg);
      this.radioButtonPlayer2[i].setIcon(imageIcon);
      ImageIcon imageIcon2 = new ImageIcon(PLAYER_SELECTED_IMGS[i]);
      Image image2 = imageIcon2.getImage();
      Image newimg2 = image2.getScaledInstance(imgWidth, imgWidth, Image.SCALE_SMOOTH);  
      imageIcon2 = new ImageIcon(newimg2);
      this.radioButtonPlayer2[i].setSelectedIcon(imageIcon2);
      this.radioButtonPlayer2[i].setFont(new Font("Sans Serif", Font.PLAIN,
              Connect4GuiDisplay.FONT_SIZE * this.frame.getFontScale()));
      this.radioButtonPlayer2[i].setHorizontalAlignment(SwingConstants.CENTER);
      this.groupPlayer2.add(this.radioButtonPlayer2[i]);
      this.radioButtonsEast.add(this.radioButtonPlayer2[i]);
    }
    this.radioButtonPlayer2[0].setSelected(true);
    this.radioButtons.add(this.radioButtonsEast);
    // Play Button
    this.playButton = new JButton("Play");
    add(this.playButton, BorderLayout.SOUTH);
    this.playButton.setFont(new Font("Sans Serif", Font.PLAIN,
            Connect4GuiDisplay.FONT_SIZE * this.frame.getFontScale()));
    this.playButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        // Get Player1 Type
        int selectedPlayer1 = 0;
        for (int i = 0; i < radioButtonPlayer1.length; i++) {
          if (radioButtonPlayer1[i].isSelected()) {
            selectedPlayer1 = i;
          }
        }
        // Get Player2 Type
        int selectedPlayer2 = 0;
        for (int i = 0; i < radioButtonPlayer2.length; i++) {
          if (radioButtonPlayer2[i].isSelected()) {
            selectedPlayer2 = i;
          }
        }
        // Set players
        Connect4Player red = new GuiPlayer(frame.getGamePanel()); // Initialize
        switch (selectedPlayer1) {
          case 0: // Human
            red = new GuiPlayer(frame.getGamePanel());
            break;
          case 1: // Random
            red = new RandomGuiPlayer(frame.getGamePanel());
            break;
          case 2: // AI
            red = new IntelligentPlayer(frame.getGamePanel());
            break;
        }
        Connect4Player yellow = new GuiPlayer(frame.getGamePanel()); // Initialize
        switch (selectedPlayer2) {
          case 0: // Human
            yellow = new GuiPlayer(frame.getGamePanel());
            break;
          case 1: // Random
            yellow = new RandomGuiPlayer(frame.getGamePanel());
            break;
          case 2: // AI
            yellow = new IntelligentPlayer(frame.getGamePanel());
            break;
        }
        frame.setPlayers(red, yellow);
        frame.displayBoard();
      }
    });
  }
}
