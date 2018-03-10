package com.guowei.lv;

import static com.guowei.lv.FakeAuctionServer.XMPP_HOSTNAME;
import static com.guowei.lv.SnipersTableModel.textFor;

public class ApplicationRunner {

    private String itemId;

    public static final String SNIPER_XMPP_ID = "sniper@localhost/Auction";

    public static final String SNIPER_ID = "sniper";
    public static final String SNIPER_PASSWORD = "sniper";

    private AuctionSniperDriver driver;

    public void startBiddingIn(FakeAuctionServer auction) {
        itemId = auction.getItemId();
        Thread thread = new Thread("Test Application") {
            @Override
            public void run() {
                try {
                    Main.main(XMPP_HOSTNAME, SNIPER_ID, SNIPER_PASSWORD, auction.getItemId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
        driver = new AuctionSniperDriver(1000);
        driver.hasTitle(MainWindow.APPLICATION_TITLE);
        driver.hasColumnTitles();
        driver.showsSniperStatus("", 0, 0, textFor(SniperState.JOINING));
    }

    public void showSniperHasLostAuction(int lastPrice, int lastBid) {
        driver.showsSniperStatus(itemId, lastPrice, lastBid, textFor(SniperState.LOST));
    }

    public void showSniperHasWonAuction(int lastPrice) {
        driver.showsSniperStatus(itemId, lastPrice, lastPrice, textFor(SniperState.WON));
    }

    public void stop() {
        if (driver != null) {
            driver.dispose();
        }
    }

    public void hasShownSniperIsBidding(int lastPrice, int lastBid) {
        driver.showsSniperStatus(itemId, lastPrice, lastBid, textFor(SniperState.BIDDING));
    }

    public void hasShownSniperIsWinning(int winningBid) {
        driver.showsSniperStatus(itemId, winningBid, winningBid, textFor(SniperState.WINNING));
    }
}
