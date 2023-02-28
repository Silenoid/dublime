package view.component;

import javax.swing.*;

public class ContextMenu extends JPopupMenu {
    private JMenuItem item;

    public ContextMenu() {
        item = new JMenuItem("wewe");
        add(item);
    }

}
