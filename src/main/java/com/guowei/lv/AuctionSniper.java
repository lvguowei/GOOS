package com.guowei.lv;

public class AuctionSniper implements AuctionEventListener {

    private SniperListener sniperListener;

    private Auction auction;

    private String itemId;

    private boolean isWinning = false;

    public AuctionSniper(String itemId, Auction auction, SniperListener listener) {
        this.itemId = itemId;
        this.sniperListener = listener;
        this.auction = auction;
    }

    @Override
    public void auctionClosed() {
        if (isWinning) {
            sniperListener.sniperWon();
        } else {
            sniperListener.sniperLost();
        }
    }

    @Override
    public void currentPrice(int price, int increment, PriceSource priceSource) {
        isWinning = priceSource == PriceSource.FromSniper;

        if (isWinning) {
            sniperListener.sniperWinning();
        } else {
            int bid = price + increment;
            auction.bid(bid);
            sniperListener.sniperBidding(new SniperState(itemId, price, bid));
        }
    }
}
