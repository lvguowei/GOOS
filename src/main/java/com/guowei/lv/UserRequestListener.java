package com.guowei.lv;

import java.util.EventListener;

public interface UserRequestListener extends EventListener {
    void joinAuction(String itemId);
}
