package com.guowei.lv;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;

class MainWindow extends JFrame {

    private static final String SNIPERS_TABLE_NAME = "Snipers Table";

    public static final String APPLICATION_TITLE = "Auction Sniper";
    public static final String NEW_ITEM_ID_NAME = "new item id";
    public static final String JOIN_BUTTON_NAME = "join button";
    public static final String NEW_ITEM_STOP_PRICE_NAME = "stop price";

    private final Announcer<UserRequestListener> userRequests = Announcer.to(UserRequestListener.class);


    MainWindow(SniperPortfolio sniperPortfolio) {
        super(APPLICATION_TITLE);
        setName(Main.MAIN_WINDOW_NAME);
        fillContentPanel(makeSnipersTable(sniperPortfolio), makeControls());
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JPanel makeControls() {
        JPanel controls = new JPanel(new FlowLayout());
        final JTextField itemIdField = itemIdField();
        final JFormattedTextField stopPriceField = stopPriceField();
        controls.add(itemIdField);
        controls.add(stopPriceField);

        JButton joinAuctionButton = new JButton("Join Auction");
        joinAuctionButton.setName(JOIN_BUTTON_NAME);
        joinAuctionButton.addActionListener(e -> userRequests.announce().joinAuction(itemIdField.getText()));
        controls.add(joinAuctionButton);

        return controls;
    }

    private JFormattedTextField stopPriceField() {
        JFormattedTextField stopPriceField = new JFormattedTextField(NumberFormat.getIntegerInstance());
        stopPriceField.setColumns(7);
        stopPriceField.setName(NEW_ITEM_STOP_PRICE_NAME);
        return stopPriceField;
    }

    private JTextField itemIdField() {
        JTextField itemIdField = new JTextField();
        itemIdField.setColumns(10);
        itemIdField.setName(NEW_ITEM_ID_NAME);
        return itemIdField;
    }


    private void fillContentPanel(JTable snipersTable, JPanel controls) {
        final Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(controls, BorderLayout.NORTH);
        contentPane.add(new JScrollPane(snipersTable), BorderLayout.CENTER);
    }

    private JTable makeSnipersTable(SniperPortfolio portfolio) {
        SnipersTableModel tableModel = new SnipersTableModel();
        portfolio.addPortfolioListener(tableModel);
        final JTable snipersTable = new JTable(tableModel);
        snipersTable.setName(SNIPERS_TABLE_NAME);
        return snipersTable;
    }

    public void addUserRequestListener(UserRequestListener userRequestListener) {
        userRequests.addListener(userRequestListener);
    }
}
