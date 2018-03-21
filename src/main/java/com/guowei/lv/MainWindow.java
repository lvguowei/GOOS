package com.guowei.lv;

import javax.swing.*;
import java.awt.*;

class MainWindow extends JFrame {

    private static final String SNIPERS_TABLE_NAME = "Snipers Table";

    public static final String APPLICATION_TITLE = "Auction Sniper";
    public static final String NEW_ITEM_ID_NAME = "new item id";
    public static final String JOIN_BUTTON_NAME = "join button";


    MainWindow(SnipersTableModel snipers) {
        super(APPLICATION_TITLE);
        setName(Main.MAIN_WINDOW_NAME);
        fillContentPanel(makeSnipersTable(snipers), makeControls());
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JPanel makeControls() {
        JPanel controls = new JPanel(new FlowLayout());
        final JTextField itemIdField = new JTextField();
        itemIdField.setName(NEW_ITEM_ID_NAME);
        controls.add(itemIdField);

        JButton joinAuctionButton = new JButton("Join Auction");
        joinAuctionButton.setName(JOIN_BUTTON_NAME);
        controls.add(joinAuctionButton);
        return controls;
    }

    private void fillContentPanel(JTable snipersTable, JPanel jPanel) {
        final Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(jPanel, BorderLayout.NORTH);
        contentPane.add(new JScrollPane(snipersTable), BorderLayout.CENTER);
    }

    private JTable makeSnipersTable(SnipersTableModel snipers) {
        final JTable snipersTable = new JTable(snipers);
        snipersTable.setName(SNIPERS_TABLE_NAME);
        return snipersTable;
    }
}
