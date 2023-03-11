package com.silenoids;

import com.formdev.flatlaf.FlatDarkLaf;
import com.silenoids.view.MainView;

import javax.swing.*;

public class App {

    public static void main(String[] args) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        FlatDarkLaf.setup();

        SwingUtilities.invokeLater(() -> {
            MainView mainView = new MainView();
            JFrame frame = new JFrame("Alias recorder");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(mainView.mainPanel);
            frame.setSize(600, 400);
            frame.setVisible(true);
        });
    }
}