package com.guowei.lv;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

import static com.guowei.lv.Main.STATUS_JOINING;

public class MainWindow extends JFrame {

    public static final String SNIPER_STATUS_NAME = "sniper status";
    private final JLabel sniperStatus = createLabel(STATUS_JOINING);

    public MainWindow() {
        super("Auction Sniper");
        setName(Main.MAIN_WINDOW_NAME);
        add(sniperStatus);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private static JLabel createLabel(String initialText) {
        JLabel result = new JLabel(initialText);
        result.setName(SNIPER_STATUS_NAME);
        result.setBorder(new LineBorder(Color.BLACK));
        return result;
    }
    
    public void showStatus(String status) {
        sniperStatus.setText(status);
    }
}
