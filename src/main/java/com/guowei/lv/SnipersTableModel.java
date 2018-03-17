package com.guowei.lv;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;


public class SnipersTableModel extends AbstractTableModel implements SniperListener {

    private static String[] STATUS_TEXT = {"Joining", "Bidding", "Winning", "Lost", "Won"};

    private List<SniperSnapshot> snapshots = new ArrayList<>();

    public void addSniper(SniperSnapshot sniperSnapshot) {
        int row = snapshots.size();
        snapshots.add(sniperSnapshot);
        fireTableRowsInserted(row, row);
    }

    public enum Column {
        ITEM_IDENTIFIER("Item") {
            @Override
            public Object valueIn(SniperSnapshot snapshot) {
                return snapshot.itemId;
            }
        }, LAST_PRICE("Last Price") {
            @Override
            public Object valueIn(SniperSnapshot snapshot) {
                return snapshot.lastPrice;
            }
        }, LAST_BID("Last Bid") {
            @Override
            public Object valueIn(SniperSnapshot snapshot) {
                return snapshot.lastBid;
            }
        }, SNIPER_STATE("State") {
            @Override
            public Object valueIn(SniperSnapshot snapshot) {
                return textFor(snapshot.state);
            }
        };

        public final String name;

        Column(String name) {
            this.name = name;
        }

        public static Column at(int offset) {
            return values()[offset];
        }

        abstract public Object valueIn(SniperSnapshot snapshot);
    }

    @Override
    public String getColumnName(int column) {
        return Column.at(column).name;
    }

    @Override
    public int getRowCount() {
        return snapshots.size();
    }

    @Override
    public int getColumnCount() {
        return Column.values().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return Column.at(columnIndex).valueIn(snapshots.get(rowIndex));
    }

    public static String textFor(SniperState state) {
        return STATUS_TEXT[state.ordinal()];
    }

    @Override
    public void sniperStateChanged(SniperSnapshot newSnapshot) {
        for (int i = 0; i < snapshots.size(); i++) {
            if (newSnapshot.isForSameItemAs(snapshots.get(i))) {
                snapshots.set(i, newSnapshot);
                fireTableRowsUpdated(i, i);
                return;
            }
        }
        throw new RuntimeException("No existing Sniper state for " + newSnapshot.itemId);
    }

}
