package com.guowei.lv;

import javax.swing.table.AbstractTableModel;

import static com.guowei.lv.MainWindow.STATUS_JOINING;

public class SnipersTableModel extends AbstractTableModel {

    private static String[] STATUS_TEXT = {MainWindow.STATUS_JOINING, MainWindow.STATUS_BIDDING, MainWindow.STATUS_WINNING, MainWindow.STATUS_LOST, MainWindow.STATUS_WON};

    private final static SniperSnapshot STARTING_UP = new SniperSnapshot("", 0, 0, SniperState.JOINING);
    private SniperSnapshot sniperSnapshot = STARTING_UP;

    public enum Column {
        ITEM_IDENTIFIER, LAST_PRICE, LAST_BID, SNIPER_STATUS;

        public static Column at(int offset) {
            return values()[offset];
        }
    }

    private String statusText = STATUS_JOINING;

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public int getColumnCount() {
        return Column.values().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (Column.at(columnIndex)) {
            case ITEM_IDENTIFIER:
                return sniperSnapshot.itemId;
            case LAST_BID:
                return sniperSnapshot.lastBid;
            case LAST_PRICE:
                return sniperSnapshot.lastPrice;
            case SNIPER_STATUS:
                return statusText;
            default:
                throw new IllegalArgumentException();
        }
    }

    public void setStatusText(String newStatusText) {
        this.statusText = newStatusText;
        fireTableRowsUpdated(0, 0);
    }

    public void sniperStateChanged(SniperSnapshot newSniperSnapshot) {
        sniperSnapshot = newSniperSnapshot;
        statusText = STATUS_TEXT[newSniperSnapshot.sniperState.ordinal()];
        fireTableRowsUpdated(0, 0);

    }
}
