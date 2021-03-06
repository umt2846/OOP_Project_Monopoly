package gui.baseFrame.panels;

import gui.baseFrame.buttons.initialScreenButons.CreditsButton;
import gui.baseFrame.buttons.initialScreenButons.ExitButton;
import gui.baseFrame.buttons.initialScreenButons.MultiPlayerButton;
import gui.baseFrame.buttons.initialScreenButons.SinglePlayerButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class InitialScreenPanel extends JPanel {
    private SinglePlayerButton singlePlayerButton;
    private MultiPlayerButton multiPlayerButton;
    private ExitButton exitButton;
    private CreditsButton creditsButton;

    private int width;
    private int height;

    private BufferedImage image1;
    private BufferedImage image2;
    private JLabel logoLabel;
    private JLabel backgroundLabel;

    private final int BUTTON_WIDTH = 300;
    private final int BUTTON_HEIGHT = 50;


    public InitialScreenPanel(int width, int height) {
        this.width = width;
        this.height = height;

        this.setLayout(null);
        this.setBackground(Color.white);

        try {
            if (System.getProperty("os.name").startsWith("Windows")) {
                image1 = ImageIO.read(new File("res\\Monopoly Logo.png"));
                image2 = ImageIO.read(new File("res\\Monopoly Background.jpg"));
            } else {
                image1 = ImageIO.read(new File("res/Monopoly Logo.png"));
                image2 = ImageIO.read(new File("res/Monopoly Background.jpg"));
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }

        logoLabel = new JLabel(new ImageIcon(image1));
        this.add(logoLabel);
        logoLabel.setBounds(0, 0, width, 200);
        logoLabel.setOpaque(true);

        initButtons();

        backgroundLabel = new JLabel(new ImageIcon(image2));
        this.add(backgroundLabel);
        backgroundLabel.setBounds(0, 200, width, 520);
        backgroundLabel.setOpaque(true);

        this.setVisible(true);
    }

    private void initButtons() {
        singlePlayerButton = new SinglePlayerButton("Single Player");
        multiPlayerButton = new MultiPlayerButton("Multiplayer");
        exitButton = new ExitButton("Exit");
        creditsButton = new CreditsButton("Credits");

        singlePlayerButton.setBounds((this.width - BUTTON_WIDTH) / 2,
                (this.height - 4 * BUTTON_HEIGHT) / 2, BUTTON_WIDTH, BUTTON_HEIGHT);
        multiPlayerButton.setBounds((this.width - BUTTON_WIDTH) / 2,
                (this.height - BUTTON_HEIGHT) / 2, BUTTON_WIDTH, BUTTON_HEIGHT);
        creditsButton.setBounds((this.width - BUTTON_WIDTH) / 2,
                (this.height - (-2) * BUTTON_HEIGHT) / 2, BUTTON_WIDTH, BUTTON_HEIGHT);
        exitButton.setBounds((this.width - BUTTON_WIDTH) / 2,
                (this.height - (-5) * BUTTON_HEIGHT) / 2, BUTTON_WIDTH, BUTTON_HEIGHT);

        singlePlayerButton.setBackground(Color.lightGray);
        multiPlayerButton.setBackground(Color.lightGray);
        exitButton.setBackground(Color.lightGray);
        creditsButton.setBackground(Color.lightGray);

        singlePlayerButton.setBorderPainted(false);
        multiPlayerButton.setBorderPainted(false);
        exitButton.setBorderPainted(false);
        creditsButton.setBorderPainted(false);

        this.add(singlePlayerButton);
        this.add(multiPlayerButton);
        this.add(exitButton);
        this.add(creditsButton);
    }


}
