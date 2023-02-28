package view;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import view.component.ContextMenu;
import view.panels.OpenGL2Panel;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainView {

    public JPanel mainPanel;
    private JButton changeThemeButton;
    private JPanel graphicPanel;

    private boolean isDark = true;

    public MainView() {
        changeThemeButton.addActionListener(actionEvent -> {
            changeTheme(isDark);
            isDark = !isDark;
        });

        changeThemeButton.addMouseListener(new MouseAdapter() {
            private void doPop(MouseEvent mouseEvent) {
                ContextMenu menu = new ContextMenu();
                menu.show(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY());
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                if (mouseEvent.isPopupTrigger())
                    doPop(mouseEvent);
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                if (mouseEvent.isPopupTrigger())
                    doPop(mouseEvent);
            }
        });
    }

    private void changeTheme(boolean isDark) {
        if (isDark) {
            FlatLightLaf.setup();
        } else {
            FlatDarkLaf.setup();
        }
        FlatLaf.updateUI();
    }

    private void createUIComponents() {
        graphicPanel = OpenGL2Panel.produceBasicPanel();
    }
}
