package com.silenoids;

import com.formdev.flatlaf.FlatDarkLaf;
import com.silenoids.view.MainView;

import javax.swing.*;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    public static void main(String[] args) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        Logger.getLogger("com.goxr3plus.streamplayer.stream.StreamPlayer").setLevel(Level.OFF);

        FlatDarkLaf.setup();

        URL appIcon = App.class.getClassLoader().getResource("mic.png");
        ImageIcon imageIcon = new ImageIcon(appIcon);

        SwingUtilities.invokeLater(() -> {
            MainView mainView = new MainView();
            JFrame frame = new JFrame("Alias recorder");
            frame.setIconImage(imageIcon.getImage());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(mainView.mainPanel);
            frame.setSize(600, 400);
            frame.setVisible(true);
        });
    }
}