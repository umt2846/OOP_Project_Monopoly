package gui.baseFrame.panels;

import gui.baseFrame.BotDifficultyBox;
import gui.baseFrame.ColorBox;
import gui.baseFrame.LoadBox;
import gui.baseFrame.buttons.lobbyButtons.AddBotButton;
import gui.baseFrame.buttons.lobbyButtons.ReadyButton;
import gui.baseFrame.buttons.lobbyButtons.StartButton;
import gui.baseFrame.buttons.multiplayerButtons.BackButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LobbyPanel extends JPanel {
    private ReadyButton readyButton;
    private BackButton backButton;
    private StartButton startButton;
    private AddBotButton addBotButton;
    private ColorBox colorBox;
    private BotDifficultyBox botDifficultyBox;
    private LoadBox loadBox;

    private int width;
    private int height;

    private LobbyPlayerListPanel lobbyPlayerListPanel;
    private BufferedImage image;
    private JLabel backgroundLabel;

    private JPanel buttonPanel;
    private CardLayout cardLayout;


    private final int BUTTON_WIDTH = 300;
    private final int BUTTON_HEIGHT = 50;

    private final Color startButtonColor = Color.ORANGE;


    public LobbyPanel(int width, int height) {
        this.width = width;
        this.height = height;

        //MonopolyGameController.getInstance().addGameStartedListener(this);

        initGUI();
        this.setVisible(true);
    }

    public void initGUI() {
        this.setBackground(Color.GRAY);
        this.setLayout(null);

        initButtons();
        initPanels();
        this.add(buttonPanel);


        validate();
        repaint();
        initBgImage();
    }

    private void initBgImage() {
        try {
            if (System.getProperty("os.name").startsWith("Windows")) {
                image = ImageIO.read(new File("res\\Monopoly Background 7.jpg"));
            } else {
                image = ImageIO.read(new File("res/Monopoly Background 7.jpg"));
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }

        backgroundLabel = new JLabel(new ImageIcon(image));
        this.add(backgroundLabel);
        backgroundLabel.setBounds(0, 0, width, height);
        backgroundLabel.setOpaque(true);
    }


    private void initButtons() {
        backButton = new BackButton("Back");
        colorBox = new ColorBox();
        botDifficultyBox = new BotDifficultyBox();


        backButton.setBounds((this.width - (-1) * BUTTON_WIDTH) / 2,
                (this.height - (-10) * BUTTON_HEIGHT) / 2, BUTTON_WIDTH, BUTTON_HEIGHT);
        colorBox.setBounds((this.width - (-1) * BUTTON_WIDTH) / 2,
                (this.height - 3 * BUTTON_HEIGHT) / 2, BUTTON_WIDTH, BUTTON_HEIGHT);
        botDifficultyBox.setBounds((this.width - (-1) * BUTTON_WIDTH) / 2,
                (this.height - 8 * BUTTON_HEIGHT) / 2, BUTTON_WIDTH, BUTTON_HEIGHT);

        backButton.setBackground(Color.gray);
        backButton.setBorderPainted(false);

        this.add(backButton);
        this.add(colorBox);
        this.add(botDifficultyBox);

    }

    private void initPanels() {
        lobbyPlayerListPanel = new LobbyPlayerListPanel();
        lobbyPlayerListPanel.setBounds(60, 60, 960, 80);
        this.add(lobbyPlayerListPanel);

        JPanel hostButtonPanel = new JPanel();
        hostButtonPanel.setLayout(null);
        hostButtonPanel.setOpaque(false);

        startButton = new StartButton("Start");
        addBotButton = new AddBotButton("Add Bot");
        loadBox = new LoadBox();

        startButton.setBounds(0, 4 * 30, BUTTON_WIDTH, BUTTON_HEIGHT);
        addBotButton.setBounds(0, 2 * 30, BUTTON_WIDTH, BUTTON_HEIGHT);
        loadBox.setBounds(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT);

        startButton.setBackground(startButtonColor);
        addBotButton.setBackground(Color.gray);

        startButton.setBorderPainted(false);
        addBotButton.setBorderPainted(false);
        startButton.setVisible(true);
        addBotButton.setVisible(true);

        hostButtonPanel.add(addBotButton);
        hostButtonPanel.add(startButton);
        hostButtonPanel.add(loadBox);

        JPanel clientButtonPanel = new JPanel();
        clientButtonPanel.setLayout(null);
        clientButtonPanel.setOpaque(false);

        readyButton = new ReadyButton();
        readyButton.setVisible(true);
        readyButton.setBorderPainted(false);
        readyButton.setBounds(0, 2 * BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
        clientButtonPanel.add(readyButton);

        buttonPanel = new JPanel();
        buttonPanel.setBounds((this.width - (-1) * BUTTON_WIDTH) / 2,
                (this.height - (-1) * BUTTON_HEIGHT) / 2, BUTTON_WIDTH, 5 * BUTTON_HEIGHT);
        cardLayout = new CardLayout();
        buttonPanel.setLayout(cardLayout);
        buttonPanel.setVisible(true);
        buttonPanel.setOpaque(false);

        buttonPanel.add(hostButtonPanel, "Host");
        buttonPanel.add(clientButtonPanel, "Client");

    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        this.add(backgroundLabel);
        backgroundLabel.setBounds(0, 0, width, height);
        backgroundLabel.setOpaque(false);

    }

    public synchronized void setHost(boolean b) {
        if (b) {
            cardLayout.show(buttonPanel, "Host");
            botDifficultyBox.setEnabled(true);
            botDifficultyBox.setVisible(true);
        } else {
            cardLayout.show(buttonPanel, "Client");
            botDifficultyBox.setEnabled(false);
            botDifficultyBox.setVisible(false);
        }
        validate();
        repaint();
    }

    public void refresh() {
        lobbyPlayerListPanel.revalidate();
        lobbyPlayerListPanel.repaint();
    }
}

