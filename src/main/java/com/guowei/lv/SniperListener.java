package com.guowei.lv;

import java.util.EventListener;

public interface SniperListener extends EventListener {
    void sniperLost();

    void sniperStateChanged(SniperSnapshot sniperSnapshot);

    void sniperWon();
}
