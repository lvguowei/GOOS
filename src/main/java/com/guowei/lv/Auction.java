package com.guowei.lv;

public interface Auction {
    void bid(int price);
    void join();
    void addAuctionEventListener(AuctionEventListener auctionEventListener);
}
