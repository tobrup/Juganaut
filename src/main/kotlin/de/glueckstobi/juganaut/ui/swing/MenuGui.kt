package de.glueckstobi.juganaut.ui.swing;

import de.glueckstobi.juganaut.bl.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuGui extends JFrame {
    private JPanel contentPane;
    private JLabel nameLabel;
    private JButton quitButton;
    private JPanel buttonPane;
    private JButton startButton;

    public MenuGui(Game game) {
        setContentPane(contentPane);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(1024, 1000);

        setVisible(true);

        getContentPane().requestFocus();

        quitButton.addActionListener(e -> dispose());

        startButton.addActionListener(e -> startPlaying());
    }

    private void startPlaying() {
        System.out.println("START PLAYING!!!!!!!!!!!!!!");
    }

}
