package com.guowei.lv;

import java.util.EventListener;

public interface SniperListener extends EventListener {
    void sniperLost();

    void sniperBidding(SniperState sniperState);

    void sniperWinning();

    void sniperWon();
}
