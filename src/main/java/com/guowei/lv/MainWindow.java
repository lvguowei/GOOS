package com.guowei.lv;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private static final String SNIPERS_TABLE_NAME = "Snipers Table";
    private final SnipersTableModel snipers = new SnipersTableModel();

    MainWindow() {
        super("Auction Sniper");
        setName(Main.MAIN_WINDOW_NAME);
        fillContentPanel(makeSnipersTable());
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void fillContentPanel(JTable snipersTable) {
        final Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new JScrollPane(snipersTable), BorderLayout.CENTER);
    }

    private JTable makeSnipersTable() {
        final JTable snipersTable = new JTable(snipers);
        snipersTable.setName(SNIPERS_TABLE_NAME);
        return snipersTable;
    }

    public void sniperStatusChanged(SniperSnapshot state) {
        snipers.sniperStateChanged(state);
    }
}
