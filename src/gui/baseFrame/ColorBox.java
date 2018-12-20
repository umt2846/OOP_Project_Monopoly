package gui.baseFrame;

import domain.client.PlayerActionController;
import domain.client.UIUpdater;
import domain.server.listeners.PlayerListChangedListener;
import domain.server.listeners.ReadinessChangedListener;
import domain.util.GameInfo;
import gui.util.ColorBoxRenderer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ColorBox extends JComboBox implements ActionListener, ReadinessChangedListener {

    public static final ArrayList<String> colorList = (ArrayList<String>) (Stream.of("White", "LightGray", "Gray", "Blue", "Cyan", "Pink", "Green",
            "Orange", "Magenta", "Yellow", "Red", "Turquoise").collect(Collectors.toList()));

    public ColorBox() {
        super();
        for (int i = 0; i < colorList.size(); i++) {
            this.insertItemAt(colorList.get(i), i);
        }
        this.setRenderer(ColorBoxRenderer.getInstance());
        this.addActionListener(this);
        UIUpdater.getInstance().addReadinessChangedListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        PlayerActionController.getInstance().changePlayerColor((String) this.getSelectedItem());
    }

    @Override
    public void onReadinessChangedEvent(boolean isReady) {
        this.setEnabled(!isReady);
    }
}
