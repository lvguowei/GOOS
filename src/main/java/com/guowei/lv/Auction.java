package com.guowei.lv;

public interface Auction {
    String ITEM_ID_AS_LOGIN = "auction-%s";
    String AUCTION_RESOURCE = "Auction";
    String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%s/" + AUCTION_RESOURCE;
    String JOIN_COMMAND_FORMAT = "SOLVersion: 1.1; Command: JOIN;";
    String BID_COMMAND_FORMAT = "SOLVersion: 1.1; Command: BID; Price: %d;";

    void bid(int price);
    void join();
    void addAuctionEventListener(AuctionEventListener auctionEventListener);
}
