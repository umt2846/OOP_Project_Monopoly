package gui.controlDisplay.butons;

import domain.client.UIUpdater;
import domain.client.PlayerActionController;
import domain.server.listeners.TurnChangedListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BuyButton extends JButton implements ActionListener, TurnChangedListener {

    public BuyButton(String text) {
        super(text);
        this.addActionListener(this);
        this.setEnabled(false);
        UIUpdater.getInstance().addTurnChangedListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        System.out.println("buy button  clicked");
//        PlayerActionController.getInstance().buy();


    }

    @Override
    public void onTurnChangedEvent(boolean isEnabled) {
        this.setEnabled(isEnabled);
    }
}
