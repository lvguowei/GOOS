package com.guowei.lv;

public class AuctionSniper implements AuctionEventListener {

    private SniperSnapshot snapshot;
    private final Announcer<SniperListener> listeners = Announcer.to(SniperListener.class);
    private Auction auction;

    AuctionSniper(String itemId, Auction auction) {
        this.auction = auction;
        this.snapshot = SniperSnapshot.joining(itemId);
    }

    public void addSniperListener(SniperListener listener) {
        this.listeners.addListener(listener);
    }

    @Override
    public void auctionClosed() {
        snapshot = snapshot.closed();
        notifyChange();
    }

    @Override
    public void currentPrice(int price, int increment, PriceSource priceSource) {
        switch (priceSource) {
            case FromSniper:
                snapshot = snapshot.winning(price);
                break;
            case FromOtherBidder:
                int bid = price + increment;
                auction.bid(bid);
                snapshot = snapshot.bidding(price, bid);
                break;
        }
        notifyChange();
    }

    private void notifyChange() {
        listeners.announce().sniperStateChanged(snapshot);
    }

    public SniperSnapshot getSnapshot() {
        return snapshot;
    }
}
