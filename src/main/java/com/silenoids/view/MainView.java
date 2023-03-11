package com.silenoids.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.silenoids.control.PlayerNew;
import com.silenoids.control.RecorderNew;
import com.silenoids.control.Sandglass;
import com.silenoids.utils.FileUtils;
import com.silenoids.view.component.ContextMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class MainView {

    private PlayerNew player;
    private RecorderNew recorder;

    public JPanel mainPanel;
    private JButton inputDirBtn;
    private JButton outputDirBtn;
    private JList<String> fileList;
    private JButton playInputBtn;
    private JButton recordOutputButton;
    private JButton playOutputButton;
    private JLabel inputTime;
    private JProgressBar sandglassBar;
    private JCheckBox autoplayBox;

    private String inputDirPath;
    private String outputDirPath;
    private DefaultListModel<String> inputFileListModel;

    public MainView() {
        Sandglass.getInstance(sandglassBar);
        player = new PlayerNew();
        recorder = new RecorderNew();
        inputFileListModel = new DefaultListModel<>();
        fileList.setModel(inputFileListModel);

        inputDirBtn.addActionListener((ActionEvent e) -> {

            // Select dir
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.showOpenDialog(mainPanel);
            File selectedDirFile = fileChooser.getSelectedFile();
            inputDirPath = selectedDirFile.getPath();

            // Elaborate dir
            if (selectedDirFile != null) {
                inputDirBtn.setText(inputDirPath);
                for (File file : selectedDirFile.listFiles()) {
                    inputFileListModel.addElement(file.getName());
                }
            }

        });

        outputDirBtn.addActionListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.showOpenDialog(mainPanel);
            File selectedDirPath = fileChooser.getSelectedFile();
            if (selectedDirPath == null) {
                outputDirBtn.setText("No directory selected");
            } else {
                outputDirPath = selectedDirPath.getPath();
                outputDirBtn.setText(outputDirPath);
            }
        });

        fileList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (inputDirPath == null || outputDirPath == null) {
                    JOptionPane.showMessageDialog(mainPanel, "Both input and output directories have to be selected");
                    return;
                }
                // TODO: tasti per registrare
                //fileList.getActionForKeyStroke()
                // TODO: colora completati
//                fileList.setCellRenderer(new ListCellRenderer<String>() {
//                    @Override
//                    public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
//                        return null;
//                    }
//                });

                player.loadAudioFile(inputDirPath, fileList.getSelectedValue());
                inputTime.setText(player.getDurationText());

                playInputBtn.setEnabled(FileUtils.fileExists(inputDirPath, fileList.getSelectedValue()));
                playOutputButton.setEnabled(FileUtils.fileExists(outputDirPath, fileList.getSelectedValue()));
                recordOutputButton.setEnabled(true);

                if (autoplayBox.isSelected()) {
                    player.play();
                }
            }
        });

        playOutputButton.addActionListener(e -> {
            player.loadAudioFile(outputDirPath, fileList.getSelectedValue());
            player.play();
        });

        fileList.addMouseListener(new MouseAdapter() {

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

            private void doPop(MouseEvent mouseEvent) {
                ContextMenu menu = new ContextMenu();
                menu.show(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY());
            }
        });

        playInputBtn.addActionListener(e -> {
            if (inputDirPath == null || outputDirPath == null) {
                JOptionPane.showMessageDialog(mainPanel, "Both input and output directories have to be selected");
                return;
            }
            player.play();
        });

        recordOutputButton.addActionListener(e -> {
            if (inputDirPath == null || outputDirPath == null) {
                JOptionPane.showMessageDialog(mainPanel, "Both input and output directories have to be selected");
                return;
            }

            recorder.stop();
            Sandglass.getInstance().startSandglass(player.getDurationInMillis());
            recorder.start();
            try {
                Thread.sleep(player.getDurationInMillis() + 200);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            recorder.stop();

            try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            FileUtils.saveAudioStreamToFile(outputDirPath, fileList.getSelectedValue(), recorder.getAudioInputStream());

            System.out.println("---Running thread list:");
            Thread.getAllStackTraces().keySet().stream().map(Thread::getName).filter(s -> s.startsWith(" ")).sorted().forEach(System.out::println);


        });
    }

    void setRecordingStateView(boolean setActive) {
    }

    void setPlayingStateView(boolean setActive) {
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(1, 1, new Insets(12, 12, 12, 12), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        outputDirBtn = new JButton();
        outputDirBtn.setText("Output Directory");
        panel2.add(outputDirBtn, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        inputDirBtn = new JButton();
        inputDirBtn.setText("Input Directory");
        panel2.add(inputDirBtn, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel1.add(scrollPane1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        fileList = new JList();
        fileList.setLayoutOrientation(1);
        fileList.setVisible(true);
        fileList.setVisibleRowCount(100);
        scrollPane1.setViewportView(fileList);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 6, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        playInputBtn = new JButton();
        playInputBtn.setEnabled(false);
        playInputBtn.setText("Play Input");
        panel3.add(playInputBtn, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel3.add(spacer1, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        recordOutputButton = new JButton();
        recordOutputButton.setEnabled(false);
        recordOutputButton.setText("Record Output");
        panel3.add(recordOutputButton, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        playOutputButton = new JButton();
        playOutputButton.setEnabled(false);
        playOutputButton.setText("Play Output");
        panel3.add(playOutputButton, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        autoplayBox = new JCheckBox();
        autoplayBox.setSelected(true);
        autoplayBox.setText("Autoplay");
        panel3.add(autoplayBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        sandglassBar = new JProgressBar();
        panel4.add(sandglassBar, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        inputTime = new JLabel();
        inputTime.setText("0:00");
        panel4.add(inputTime, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}
