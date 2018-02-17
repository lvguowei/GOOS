package com.guowei.lv;

public class AuctionSniper implements AuctionEventListener {

    private SniperListener sniperListener;

    public AuctionSniper(SniperListener listener) {
        this.sniperListener = listener;
    }

    @Override
    public void auctionClosed() {
        sniperListener.sniperLost();
    }

    @Override
    public void currentPrice(int price, int increment) {

    }
}
