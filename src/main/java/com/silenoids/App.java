package com.silenoids;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.silenoids.utils.HttpClient;
import com.silenoids.view.MainView;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> manageCrash(e));

        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        Logger.getLogger("com.goxr3plus.streamplayer.stream.StreamPlayer").setLevel(Level.OFF);

        FlatLaf.setGlobalExtraDefaults(Map.of(
                "@accentColor", "#ffdd00",
                "@background" , "#292929",
                "@foreground" , "#E0D3DE"
        ));
        FlatDarkLaf.setup();

        URL appIcon = App.class.getClassLoader().getResource("micr.png");
        ImageIcon imageIcon = new ImageIcon(appIcon);

        SwingUtilities.invokeLater(() -> {
            MainView mainView = new MainView();
            JFrame frame = new JFrame("Alias recorder");
            frame.setSize(1600, 900);
            frame.setLocationRelativeTo(null);  //center
            frame.setContentPane(mainView.mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setIconImage(imageIcon.getImage());
            frame.setVisible(true);
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    mainView.dispose();
                    super.windowClosing(e);
                }
            });
        });
    }

    private static void manageCrash(Throwable throwable) {
        HttpClient.sendIFTTTCrashReport(throwable.toString());
    }
}