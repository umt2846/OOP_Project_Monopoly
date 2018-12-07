package gui.baseFrame.buttons.multiplayerButtons;

import gui.baseFrame.BaseFrame;
import gui.baseFrame.buttons.BaseButton;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HostGameButton extends BaseButton {
    public HostGameButton(String text) {
        super(text);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        System.out.println("Host Button Pressed");
        BaseFrame.setStatus("Host");
    }
}
