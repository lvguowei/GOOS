package com.guowei.lv;

public class AuctionSniper implements AuctionEventListener {

    private final Item item;
    private SniperSnapshot snapshot;
    private final Announcer<SniperListener> listeners = Announcer.to(SniperListener.class);
    private Auction auction;

    AuctionSniper(Item item, Auction auction) {
        this.item = item;
        this.auction = auction;
        this.snapshot = SniperSnapshot.joining(item.identifier);
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
                if (item.allowsBid(bid)) {
                    auction.bid(bid);
                    snapshot = snapshot.bidding(price, bid);
                } else {
                    snapshot = snapshot.losing(price);
                }

                break;
        }
        notifyChange();
    }

    @Override
    public void auctionFailed() {
        snapshot = snapshot.failed();
        listeners.announce().sniperStateChanged(snapshot);
    }

    private void notifyChange() {
        listeners.announce().sniperStateChanged(snapshot);
    }

    SniperSnapshot getSnapshot() {
        return snapshot;
    }
}
