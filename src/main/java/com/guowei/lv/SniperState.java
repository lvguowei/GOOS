package com.guowei.lv;

public enum  SniperState {
    JOINING {
        @Override
        public SniperState whenAuctionClosed() {
            return LOST;
        }
    },
    BIDDING {
        @Override
        public SniperState whenAuctionClosed() {
            return LOST;
        }
    },
    WINNING {
        @Override
        public SniperState whenAuctionClosed() {
            return WON;
        }
    },
    LOST,
    WON;

    public SniperState whenAuctionClosed() {
        throw new RuntimeException("Auction is already closed");
    }
}
