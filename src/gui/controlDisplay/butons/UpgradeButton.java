package gui.controlDisplay.butons;

import domain.client.PlayerActionController;
import domain.client.UIUpdater;
import domain.server.listeners.ButtonChangeListener;
import domain.server.listeners.TurnChangedListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpgradeButton extends JButton implements ActionListener, TurnChangedListener, ButtonChangeListener {

    private final int INDEX = 6;

    public UpgradeButton(String text) {
        super(text);
        this.addActionListener(this);
        this.setEnabled(false);
        UIUpdater.getInstance().addTurnChangedListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //TODO should call another label for choosing own deeds.
        PlayerActionController.getInstance().upgrade();
        // System.out.println("Upgrade Button is clicked");
    }

    @Override
    public void onTurnChangedEvent(String enable) {
        this.setEnabled(enable.charAt(INDEX) == '1');
    }

    @Override
    public void onButtonChangeEvent() {

    }
}