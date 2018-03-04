package com.guowei.lv;

import java.util.EventListener;

public interface SniperListener extends EventListener {
    void sniperStateChanged(SniperSnapshot sniperSnapshot);
}
