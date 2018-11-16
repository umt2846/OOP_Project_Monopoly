package gui.baseFrame.panels;

import domain.controller.MonopolyGameController;
import domain.listeners.GameStartedListener;
import gui.baseFrame.BaseFrame;
import gui.baseFrame.ColorBox;
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

public class LobbyPanel extends JPanel implements GameStartedListener {
    private ReadyButton readyButton;
    private BackButton backButton;
    private StartButton startButton;
    private ColorBox colorBox;
    private AddBotButton addBotButton;
    private boolean isHost;


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


    public LobbyPanel(int width, int height, boolean b) {
        isHost = b;
        this.width = width;
        this.height = height;
        MonopolyGameController.getInstance().addGameStartedListener(this);
        initGUI();
        //backgroundLabel.setOpaque(true);

        this.setVisible(true);
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
    }


    private void initButtons() {
        backButton = new BackButton("Back");
        colorBox = new ColorBox();


        backButton.setBounds((this.width - (-1) * BUTTON_WIDTH) / 2,
                (this.height - (-8) * BUTTON_HEIGHT) / 2, BUTTON_WIDTH, BUTTON_HEIGHT);
        colorBox.setBounds((this.width - (-1) * BUTTON_WIDTH) / 2,
                (this.height - 3 * BUTTON_HEIGHT) / 2, BUTTON_WIDTH, BUTTON_HEIGHT);

        backButton.setBackground(Color.gray);
//        startButton.setBackground(Color.gray);

        backButton.setBorderPainted(false);
        this.add(backButton);
        this.add(colorBox);
    }

    public void initGUI() {
        this.setBackground(Color.GRAY);
        this.setLayout(null);

        lobbyPlayerListPanel = new LobbyPlayerListPanel();
        lobbyPlayerListPanel.setBounds(60, 60, 960, 80);
        this.add(lobbyPlayerListPanel);

        buttonPanel = new JPanel();
        buttonPanel.setBounds((this.width - (-1) * BUTTON_WIDTH) / 2,
                (this.height - (-5) * BUTTON_HEIGHT) / 2, BUTTON_WIDTH, 2*BUTTON_HEIGHT);
        cardLayout = new CardLayout();
        buttonPanel.setLayout(cardLayout);
        buttonPanel.setVisible(true);
        buttonPanel.setOpaque(true);

        initPanels();
        initButtons();
        initBgImage();
        this.add(buttonPanel);

        if(isHost){
            cardLayout.show(buttonPanel, "Host");
        }else{
            cardLayout.show(buttonPanel, "Client");
        }

        validate();
        repaint();
    }

    private void initPanels() {
        JPanel hostButtonPanel = new JPanel();
        hostButtonPanel.setLayout(new GridLayout(2, 1));

        startButton = new StartButton("Start");
        addBotButton = new AddBotButton("Add Bot");

        startButton.setBackground(startButtonColor);
        addBotButton.setBackground(Color.gray);

        startButton.setBorderPainted(false);
        addBotButton.setBorderPainted(false);
        startButton.setVisible(true);
        addBotButton.setVisible(true);

        hostButtonPanel.add(addBotButton);
        hostButtonPanel.add(startButton);

        JPanel clientButtonPanel = new JPanel();
        clientButtonPanel.setLayout(new GridLayout(1, 1));

        readyButton = new ReadyButton();
        readyButton.setVisible(true);
        readyButton.setBorderPainted(false);
        clientButtonPanel.add(readyButton);

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

    public void setHost(boolean b) {
        isHost = b;
        System.out.println("\n\n =======---------------==========\n" + "setHost\n\n");
        if (b) {
//            readyButton.setVisible(false);
//            readyButton.setEnabled(false);
//            startButton.setEnabled(true);
//            startButton.setVisible(true);
//
//            this.add(startButton);
//            this.add(addBotButton);
//            this.remove(readyButton);
//            cardLayout.show(buttonPanel, "Client");
        } else {
//            readyButton.setVisible(true);
//            readyButton.setEnabled(true);
//            startButton.setEnabled(false);
//            startButton.setVisible(false);
//            readyButton.setVisible(true);
//            readyButton.setEnabled(true);
//            this.add(readyButton);
//            this.remove(addBotButton);
//            this.remove(startButton);
//            cardLayout.show(buttonPanel, "Client");

        }
//        validate();
//        repaint();
    }

    public synchronized boolean getHost() {
        return isHost;
    }


    @Override
    public void onGameStartedEvent() {
        BaseFrame.setStatus("Game");
    }
}

