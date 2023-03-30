package com.silenoids.view;

import com.silenoids.utils.HTMLContentUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class InfoDialog extends JDialog {
    private JPanel contentPane;
    private JButton backBtn;
    private JEditorPane infoContent;

    public InfoDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(backBtn);
        this.setSize(1600, 900);

        infoContent.setText(HTMLContentUtils.getHelpContent());

        backBtn.addActionListener(e -> onClose());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onClose();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onClose(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onClose() {
        dispose();
    }

    // Quick dialog test
    // TODO: remove
    public static void main(String[] args) {
        InfoDialog dialog = new InfoDialog();
        dialog.setSize(new Dimension(600, 900));
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        System.exit(0);
    }

}
