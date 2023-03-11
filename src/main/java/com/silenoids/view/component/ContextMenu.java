package com.silenoids.view.component;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ContextMenu extends JPopupMenu {
    private JMenuItem item;

    public ContextMenu() {
        item = new JMenuItem("wewe");
        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("todo");
            }
        });
        add(item);
    }

}
