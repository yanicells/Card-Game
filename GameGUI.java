import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * The GameGUI class is responsible for setting up and managing the graphical
 * user interface (GUI) of the card game.
 * It handles the creation and layout of various panels, buttons, and labels
 * that represent the game state and
 * interact with the user. The class also manages user input for starting the
 * game, taking actions (e.g., Attack, Swap),
 * and displaying the results of the game.
 * The GameGUI class implements ActionListener to handle button clicks and
 * initiate corresponding actions in the game.
 * 
 * @author Edrian Miguel E. Capistrano (240939)
 * @version December 5, 2024
 * 
 *          I have not discussed the Java language code in my program
 *          with anyone other than my instructor or the teaching assistants
 *          assigned to this course.
 * 
 *          I have not used Java language code obtained from another student,
 *          or any other unauthorized source, either modified or unmodified.
 * 
 *          If any Java language code or documentation used in my program
 *          was obtained from another source, such as a textbook or website,
 *          that has been clearly noted with a proper citation in the comments
 *          of my program.
 */

public class GameGUI implements ActionListener {
    private JFrame frame;
    private JPanel topPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel bottomPanel;
    private JButton attackButton;
    private JButton swapButton;
    private JButton ultimateButton;
    private JButton healButton;
    private JLabel player1MainCard;
    private JLabel player1Card2;
    private JLabel player1Card3;
    private JLabel player1Card4;
    private JLabel player1Card5;
    private JLabel player2MainCard;
    private JLabel player2Card2;
    private JLabel player2Card3;
    private JLabel player2Card4;
    private JLabel player2Card5;
    private JPanel subTopPanelLeft;
    private JPanel subBottomPanelLeft;
    private JPanel subTopPanelRight;
    private JPanel subBottomPanelRight;
    private JTextArea textArea;
    private JTextArea textArea2;
    private GameMaster gameMaster;
    private JLabel player1NameLabel;
    private JLabel player2NameLabel;

    private JFrame frameLaunch;
    private JLabel namePlayer1Label;
    private JLabel namePlayer2Label;
    private JLabel setRandomLabel;
    private JLabel setNewDeckLabel;

    private JTextField inputNamePlayer1;
    private JTextField inputNamePlayer2;
    private JTextField inputRandom;
    private JTextField inputNewDeck;
    private JPanel player1NamePanel;
    private JPanel player2NamePanel;
    private JButton startGameButton;

    private JButton exitGameButton;
    private JFrame endFrame;
    private JTextArea gameResult;

    private Clip clip;
    private File file;

    ImageIcon dragonImage;
    ImageIcon fairyImage;
    ImageIcon ghostImage;
    ImageIcon humanImage;

    /**
     * Constructor for the GameGUI class. It initializes all the GUI components,
     * including buttons, panels, labels,
     * text areas, and images. The constructor sets up the game interface for player
     * interaction, such as displaying
     * the player's cards, allowing them to choose actions (Attack, Swap, Ultimate,
     * Heal), and initializing the game window.
     */
    public GameGUI() {
        frame = new JFrame();
        topPanel = new JPanel();
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        bottomPanel = new JPanel();
        attackButton = new JButton("ATTACK");
        swapButton = new JButton("SWAP");
        ultimateButton = new JButton("ULTIMATE");
        healButton = new JButton("HEAL");
        player1MainCard = new JLabel();
        player1Card2 = new JLabel();
        player1Card3 = new JLabel();
        player1Card4 = new JLabel();
        player1Card5 = new JLabel();
        player2MainCard = new JLabel();
        player2Card2 = new JLabel();
        player2Card3 = new JLabel();
        player2Card4 = new JLabel();
        player2Card5 = new JLabel();
        subTopPanelLeft = new JPanel();
        subBottomPanelLeft = new JPanel();
        subTopPanelRight = new JPanel();
        subBottomPanelRight = new JPanel();
        textArea = new JTextArea();
        textArea2 = new JTextArea();

        frameLaunch = new JFrame("Launch Page");
        namePlayer1Label = new JLabel("Enter Player 1 Name: ");
        namePlayer2Label = new JLabel("Enter Player 2 Name: ");
        setRandomLabel = new JLabel("Random Cards ('random'): ");
        setNewDeckLabel = new JLabel("Add New Deck ('new'): ");
        inputNamePlayer1 = new JTextField(15);
        inputNamePlayer2 = new JTextField(15);
        inputRandom = new JTextField(15);
        inputNewDeck = new JTextField(15);
        startGameButton = new JButton("Click to Start Game");

        player1NameLabel = new JLabel();
        player2NameLabel = new JLabel();
        player1NamePanel = new JPanel();
        player2NamePanel = new JPanel();
    }

    /**
     * Sets up the graphical user interface (GUI) by configuring the layout,
     * initializing the game elements,
     * and adding action listeners for the buttons. This method also sets up the
     * audio and the game window.
     * This is the main window where the game is played. It is divided into two
     * parts. The left for player 1 while
     * the right is for player 2.
     */
    public void setUpGUI() {
        setUpAudio();
        Container container = frame.getContentPane();
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Card Game");

        leftPanel.setPreferredSize(new Dimension(400, 465));
        rightPanel.setPreferredSize(new Dimension(400, 465));
        topPanel.setPreferredSize(new Dimension(600, 45));
        bottomPanel.setPreferredSize(new Dimension(600, 180));
        topPanel.setBackground(new Color(238, 238, 238));
        leftPanel.setBackground(new Color(238, 238, 238));
        rightPanel.setBackground(new Color(238, 238, 238));
        bottomPanel.setBackground(new Color(238, 238, 238));
        bottomPanel.setLayout(new FlowLayout());

        topPanel.setLayout(new FlowLayout());

        attackButton.setPreferredSize(new Dimension(100, 35));
        swapButton.setPreferredSize(new Dimension(100, 35));
        ultimateButton.setPreferredSize(new Dimension(100, 35));
        healButton.setPreferredSize(new Dimension(100, 35));
        attackButton.setBackground(new Color(233, 233, 233));
        swapButton.setBackground(new Color(233, 233, 233));
        ultimateButton.setBackground(new Color(233, 233, 233));
        healButton.setBackground(new Color(233, 233, 233));
        attackButton.setFocusable(false);
        swapButton.setFocusable(false);
        ultimateButton.setFocusable(false);
        healButton.setFocusable(false);

        attackButton.addActionListener(this);
        swapButton.addActionListener(this);
        ultimateButton.addActionListener(this);
        healButton.addActionListener(this);

        player1MainCard.setBackground(Color.black);
        player1Card2.setBackground(Color.black);
        player1Card3.setBackground(Color.black);
        player1Card4.setBackground(Color.black);
        player1Card5.setBackground(Color.black);
        player2MainCard.setBackground(Color.black);
        player2Card2.setBackground(Color.black);
        player2Card3.setBackground(Color.black);
        player2Card4.setBackground(Color.black);
        player2Card5.setBackground(Color.black);

        player1MainCard.setOpaque(true);
        player1Card2.setOpaque(true);
        player1Card3.setOpaque(true);
        player1Card4.setOpaque(true);
        player1Card5.setOpaque(true);
        player2MainCard.setOpaque(true);
        player2Card2.setOpaque(true);
        player2Card3.setOpaque(true);
        player2Card4.setOpaque(true);
        player2Card5.setOpaque(true);

        player1MainCard.setPreferredSize(new Dimension(150, 175));
        player1Card2.setPreferredSize(new Dimension(87, 115));
        player1Card3.setPreferredSize(new Dimension(87, 115));
        player1Card4.setPreferredSize(new Dimension(87, 115));
        player1Card5.setPreferredSize(new Dimension(87, 115));

        player2MainCard.setPreferredSize(new Dimension(150, 175));
        player2Card2.setPreferredSize(new Dimension(87, 115));
        player2Card3.setPreferredSize(new Dimension(87, 115));
        player2Card4.setPreferredSize(new Dimension(87, 115));
        player2Card5.setPreferredSize(new Dimension(87, 115));

        Font mainFont = new Font("Arial", Font.PLAIN, 13);
        Font cardFont = new Font("Arial", Font.PLAIN, 10);
        Color whiteColor = Color.WHITE;

        player1MainCard.setFont(mainFont);
        player1MainCard.setForeground(whiteColor);
        player1MainCard.setHorizontalTextPosition(JLabel.CENTER);
        player1MainCard.setVerticalTextPosition(JLabel.TOP);
        player1MainCard.setHorizontalAlignment(JLabel.CENTER);

        player1Card2.setFont(cardFont);
        player1Card2.setForeground(whiteColor);
        player1Card2.setHorizontalTextPosition(JLabel.CENTER);
        player1Card2.setVerticalTextPosition(JLabel.TOP);
        player1Card2.setHorizontalAlignment(JLabel.CENTER);

        player1Card3.setFont(cardFont);
        player1Card3.setForeground(whiteColor);
        player1Card3.setHorizontalTextPosition(JLabel.CENTER);
        player1Card3.setVerticalTextPosition(JLabel.TOP);
        player1Card3.setHorizontalAlignment(JLabel.CENTER);

        player1Card4.setFont(cardFont);
        player1Card4.setForeground(whiteColor);
        player1Card4.setHorizontalTextPosition(JLabel.CENTER);
        player1Card4.setVerticalTextPosition(JLabel.TOP);
        player1Card4.setHorizontalAlignment(JLabel.CENTER);

        player1Card5.setFont(cardFont);
        player1Card5.setForeground(whiteColor);
        player1Card5.setHorizontalTextPosition(JLabel.CENTER);
        player1Card5.setVerticalTextPosition(JLabel.TOP);
        player1Card5.setHorizontalAlignment(JLabel.CENTER);

        player2MainCard.setFont(mainFont);
        player2MainCard.setForeground(whiteColor);
        player2MainCard.setHorizontalTextPosition(JLabel.CENTER);
        player2MainCard.setVerticalTextPosition(JLabel.TOP);
        player2MainCard.setHorizontalAlignment(JLabel.CENTER);

        player2Card2.setFont(cardFont);
        player2Card2.setForeground(whiteColor);
        player2Card2.setHorizontalTextPosition(JLabel.CENTER);
        player2Card2.setVerticalTextPosition(JLabel.TOP);
        player2Card2.setHorizontalAlignment(JLabel.CENTER);

        player2Card3.setFont(cardFont);
        player2Card3.setForeground(whiteColor);
        player2Card3.setHorizontalTextPosition(JLabel.CENTER);
        player2Card3.setVerticalTextPosition(JLabel.TOP);
        player2Card3.setHorizontalAlignment(JLabel.CENTER);

        player2Card4.setFont(cardFont);
        player2Card4.setForeground(whiteColor);
        player2Card4.setHorizontalTextPosition(JLabel.CENTER);
        player2Card4.setVerticalTextPosition(JLabel.TOP);
        player2Card4.setHorizontalAlignment(JLabel.CENTER);

        player2Card5.setFont(cardFont);
        player2Card5.setForeground(whiteColor);
        player2Card5.setHorizontalTextPosition(JLabel.CENTER);
        player2Card5.setVerticalTextPosition(JLabel.TOP);
        player2Card5.setHorizontalAlignment(JLabel.CENTER);

        leftPanel.setLayout(new BorderLayout());
        rightPanel.setLayout(new BorderLayout());

        subTopPanelLeft.setLayout(new FlowLayout());
        subBottomPanelLeft.setLayout(new FlowLayout());

        subTopPanelRight.setLayout(new FlowLayout());
        subBottomPanelRight.setLayout(new FlowLayout());

        dragonImage = new ImageIcon("dragon.png");
        fairyImage = new ImageIcon("fairy.png");
        ghostImage = new ImageIcon("ghost.png");
        humanImage = new ImageIcon("human.png");

        String displayPlayer1 = String.format("Player 1: %s | Tokens: %s", gameMaster.getPlayer1().getName(),
                gameMaster.getPlayer1().getTokens());
        String displayPlayer2 = String.format("Player 2: %s | Tokens: %s", gameMaster.getPlayer2().getName(),
                gameMaster.getPlayer2().getTokens());

        player1NameLabel.setText(displayPlayer1);
        player2NameLabel.setText(displayPlayer2);

        player1NamePanel.add(player1NameLabel);
        player2NamePanel.add(player2NameLabel);

        subTopPanelLeft.add(player1MainCard);
        subBottomPanelLeft.add(player1Card2);
        subBottomPanelLeft.add(player1Card3);
        subBottomPanelLeft.add(player1Card4);
        subBottomPanelLeft.add(player1Card5);

        subTopPanelRight.add(player2MainCard);
        subBottomPanelRight.add(player2Card2);
        subBottomPanelRight.add(player2Card3);
        subBottomPanelRight.add(player2Card4);
        subBottomPanelRight.add(player2Card5);

        leftPanel.add(player1NamePanel, BorderLayout.NORTH);
        leftPanel.add(subTopPanelLeft, BorderLayout.CENTER);
        leftPanel.add(subBottomPanelLeft, BorderLayout.SOUTH);
        rightPanel.add(player2NamePanel, BorderLayout.NORTH);
        rightPanel.add(subTopPanelRight, BorderLayout.CENTER);
        rightPanel.add(subBottomPanelRight, BorderLayout.SOUTH);

        topPanel.add(attackButton);
        topPanel.add(swapButton);
        topPanel.add(ultimateButton);
        topPanel.add(healButton);

        textArea.setPreferredSize(new Dimension(375, 170));
        textArea.setEditable(false);
        textArea.setBackground(new Color(233, 233, 233));
        textArea.setFont(mainFont);
        textArea2.setPreferredSize(new Dimension(375, 170));
        textArea2.setEditable(false);
        textArea2.setBackground(new Color(233, 233, 233));
        textArea2.setFont(mainFont);
        bottomPanel.add(textArea);
        bottomPanel.add(textArea2);

        container.setLayout(new BorderLayout());
        container.add(topPanel, BorderLayout.NORTH);
        container.add(leftPanel, BorderLayout.WEST);
        container.add(rightPanel, BorderLayout.EAST);
        container.add(bottomPanel, BorderLayout.SOUTH);

        String output = "";
        textArea.setText(output);
        textArea2.setText(output);
        for (int i = 0; i < 10; i++) {
            if (gameMaster.isRandomCard()) {
                output = gameMaster.dealRandomCard() + "\n";
            } else {
                output = gameMaster.dealCard() + "\n";
            }

            if (i % 2 == 0) {
                textArea.append(output);
            } else {
                textArea2.append(output);
            }
        }

        updateCards(gameMaster.getPlayer1(), gameMaster.getPlayer2());
        frame.setVisible(true);
    }

    /**
     * Launches the input part of the GUI.
     * Sets up the frame with player name inputs, randomization, deck options,
     * and the start game button, as well as configuring the layout and displaying
     * the window.
     */
    public void launchGUI() {
        frameLaunch.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameLaunch.setSize(350, 200);
        frameLaunch.setResizable(false);

        frameLaunch.setLayout(new GridLayout(5, 2));

        frameLaunch.add(namePlayer1Label);
        frameLaunch.add(inputNamePlayer1);

        frameLaunch.add(namePlayer2Label);
        frameLaunch.add(inputNamePlayer2);

        frameLaunch.add(setRandomLabel);
        frameLaunch.add(inputRandom);

        frameLaunch.add(setNewDeckLabel);
        frameLaunch.add(inputNewDeck);

        frameLaunch.add(new JPanel());
        frameLaunch.add(startGameButton);
        frameLaunch.setLocationRelativeTo(null);

        frameLaunch.setVisible(true);

        startGameButton.addActionListener(this);
    }

    /**
     * Displays the game statistics in a new window once the game is over.
     * Closes the current audio clip and opens a new window showing the results.
     *
     * @param statistic The game results to be displayed in the text area.
     */
    public void gameStatistics(String statistic) {
        clip.close();
        endFrame = new JFrame();
        gameResult = new JTextArea();
        gameResult.setText(statistic);
        gameResult.setEditable(false);

        endFrame.add(gameResult);
        endFrame.setResizable(false);
        endFrame.setSize(240, 300);
        endFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        endFrame.setTitle("Summary");
        endFrame.setVisible(true);
    }

    /**
     * Handles button click events for various actions such as attack, swap,
     * ultimate, heal,
     * and starting a new game. Updates the game state and GUI accordingly.
     *
     * @param e The action event triggered by the button click.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String textOutput = "";
        String endOutput = "";
        if (e.getSource() == attackButton) {
            textOutput = gameMaster.play("attack");
            updateTextArea(textOutput);
            updateCards(gameMaster.getPlayer1(), gameMaster.getPlayer2());
        } else if (e.getSource() == swapButton) {
            textOutput = gameMaster.play("swap");
            updateTextArea(textOutput);
            updateCards(gameMaster.getPlayer1(), gameMaster.getPlayer2());
        } else if (e.getSource() == ultimateButton) {
            textOutput = gameMaster.play("ultimate");
            updateTextArea(textOutput);
            updateCards(gameMaster.getPlayer1(), gameMaster.getPlayer2());
        } else if (e.getSource() == healButton) {
            textOutput = gameMaster.play("heal");
            updateTextArea(textOutput);
            updateCards(gameMaster.getPlayer1(), gameMaster.getPlayer2());
        } else if (e.getSource() == startGameButton) {
            String gameDetails = String.format("%s %s %s %s", inputNamePlayer1.getText(), inputNamePlayer2.getText(),
                    inputRandom.getText(), inputNewDeck.getText());
            String[] input = gameDetails.split(" ");
            gameMaster = new GameMaster(new Player(input[0]), new Player(input[1]), input[2].equals("random"),
                    input[3].equals("new"));
            frameLaunch.setVisible(false);
            setUpGUI();
        }
        if (gameMaster.hasWinner()) {
            attackButton.setEnabled(false);
            swapButton.setEnabled(false);
            ultimateButton.setEnabled(false);
            healButton.setEnabled(false);
            endOutput = gameMaster.gameReport();
            gameStatistics(endOutput);
        } else {
            updateCards(gameMaster.getPlayer1(), gameMaster.getPlayer2());
        }
    }

    /**
     * Updates the display of cards for both players.
     * Refreshes the card information and the visual representations of the cards on
     * the GUI.
     * Calls the addPicture method as a helper.
     *
     * @param player1 The first player whose cards will be updated.
     * @param player2 The second player whose cards will be updated.
     */
    public void updateCards(Player player1, Player player2) {
        updateName();
        Card[] player1Cards = player1.getCards();
        Card[] player2Cards = player2.getCards();

        player1MainCard.setText(String.format("%s | %s | %s",
                player1.getActiveCard().getName(),
                player1.getActiveCard().getHealth(),
                player1.getActiveCard().getPower()));
        addPicture(player1.getActiveCard().getType(), player1MainCard, true);

        player1Card2.setText(String.format("%s | %s | %s",
                player1Cards[1].getName(),
                player1Cards[1].getHealth(),
                player1Cards[1].getPower()));
        addPicture(player1Cards[1].getType(), player1Card2, false);

        player1Card3.setText(String.format("%s | %s | %s",
                player1Cards[2].getName(),
                player1Cards[2].getHealth(),
                player1Cards[2].getPower()));
        addPicture(player1Cards[2].getType(), player1Card3, false);

        player1Card4.setText(String.format("%s | %s | %s",
                player1Cards[3].getName(),
                player1Cards[3].getHealth(),
                player1Cards[3].getPower()));
        addPicture(player1Cards[3].getType(), player1Card4, false);

        player1Card5.setText(String.format("%s | %s | %s",
                player1Cards[4].getName(),
                player1Cards[4].getHealth(),
                player1Cards[4].getPower()));
        addPicture(player1Cards[4].getType(), player1Card5, false);

        player2MainCard.setText(String.format("%s | %s | %s",
                player2.getActiveCard().getName(),
                player2.getActiveCard().getHealth(),
                player2.getActiveCard().getPower()));
        addPicture(player2.getActiveCard().getType(), player2MainCard, true);

        player2Card2.setText(String.format("%s | %s | %s",
                player2Cards[1].getName(),
                player2Cards[1].getHealth(),
                player2Cards[1].getPower()));
        addPicture(player2Cards[1].getType(), player2Card2, false);

        player2Card3.setText(String.format("%s | %s | %s",
                player2Cards[2].getName(),
                player2Cards[2].getHealth(),
                player2Cards[2].getPower()));
        addPicture(player2Cards[2].getType(), player2Card3, false);

        player2Card4.setText(String.format("%s | %s | %s",
                player2Cards[3].getName(),
                player2Cards[3].getHealth(),
                player2Cards[3].getPower()));
        addPicture(player2Cards[3].getType(), player2Card4, false);

        player2Card5.setText(String.format("%s | %s | %s",
                player2Cards[4].getName(),
                player2Cards[4].getHealth(),
                player2Cards[4].getPower()));
        addPicture(player2Cards[4].getType(), player2Card5, false);
    }

    /**
     * This is a helper method that adds a picture representing a card type to the
     * corresponding label.
     * The size of the image is adjusted based on whether it is the main card or
     * not.
     *
     * @param type     The type of the card (e.g., "Dragon", "Ghost", "Fairy",
     *                 "Human").
     * @param card     The JLabel to which the image will be added.
     * @param mainCard True if the card is the main card, false otherwise.
     */
    private void addPicture(String type, JLabel card, boolean mainCard) {
        if (type.equals("Dragon")) {
            if (mainCard) {
                Image resized = dragonImage.getImage().getScaledInstance(120, 140, Image.SCALE_SMOOTH);
                ImageIcon newImage = new ImageIcon(resized);
                card.setIcon(newImage);
            } else {
                Image resized = dragonImage.getImage().getScaledInstance(65, 85, Image.SCALE_SMOOTH);
                ImageIcon newImage = new ImageIcon(resized);
                card.setIcon(newImage);
            }
        } else if (type.equals("Ghost")) {
            if (mainCard) {
                Image resized = ghostImage.getImage().getScaledInstance(120, 140, Image.SCALE_SMOOTH);
                ImageIcon newImage = new ImageIcon(resized);
                card.setIcon(newImage);
            } else {
                Image resized = ghostImage.getImage().getScaledInstance(65, 85, Image.SCALE_SMOOTH);
                ImageIcon newImage = new ImageIcon(resized);
                card.setIcon(newImage);
            }
        } else if (type.equals("Fairy")) {
            if (mainCard) {
                Image resized = fairyImage.getImage().getScaledInstance(120, 140, Image.SCALE_SMOOTH);
                ImageIcon newImage = new ImageIcon(resized);
                card.setIcon(newImage);
            } else {
                Image resized = fairyImage.getImage().getScaledInstance(65, 85, Image.SCALE_SMOOTH);
                ImageIcon newImage = new ImageIcon(resized);
                card.setIcon(newImage);
            }
        } else if (type.equals("Human")) {
            if (mainCard) {
                Image resized = humanImage.getImage().getScaledInstance(120, 140, Image.SCALE_SMOOTH);
                ImageIcon newImage = new ImageIcon(resized);
                card.setIcon(newImage);
            } else {
                Image resized = humanImage.getImage().getScaledInstance(65, 85, Image.SCALE_SMOOTH);
                ImageIcon newImage = new ImageIcon(resized);
                card.setIcon(newImage);
            }
        }
    }

    /**
     * Updates the displayed player names and token counts on the GUI.
     * Also alternates the color of the labels to indicate whose turn it is.
     */
    private void updateName() {
        String displayPlayer1 = String.format("Player 1: %s | Tokens: %s", gameMaster.getPlayer1().getName(),
                gameMaster.getPlayer1().getTokens());
        String displayPlayer2 = String.format("Player 2: %s | Tokens: %s", gameMaster.getPlayer2().getName(),
                gameMaster.getPlayer2().getTokens());
        player1NameLabel.setText(displayPlayer1);
        player2NameLabel.setText(displayPlayer2);

        if (gameMaster.getTurnCounter() % 2 == 0) {
            player2NameLabel.setForeground(Color.RED);
            player1NameLabel.setForeground(Color.BLACK);
        } else {
            player1NameLabel.setForeground(Color.RED);
            player2NameLabel.setForeground(Color.BLACK);
        }
    }

    /**
     * Sets up and plays background music during the game.
     * It loads the audio file and prepares the audio clip for playback.
     * The music will loop for 15 iterations.
     */
    private void setUpAudio() {
        file = new File("theme.wav");
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        clip.loop(15);
    }

    /**
     * Updates the game text output based on the turn counter.
     * Displays the output either in the left or right text area depending on the
     * player's turn.
     *
     * @param output The text that will be displayed in the text area.
     */
    private void updateTextArea(String output) {
        int turnUpdate = gameMaster.hasWinner() ? gameMaster.getTurnCounter() + 1 : gameMaster.getTurnCounter();
        if (turnUpdate % 2 == 0) {
            textArea.setText(output);
        } else {
            textArea2.setText(output);
        }
    }
}
