package com.guowei.lv;

import org.jivesoftware.smack.XMPPException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class XMPPAuctionHouseTest {

    private final FakeAuctionServer auctionServer = new FakeAuctionServer("item54321");
    private XMPPAuctionHouse auctionHouse;

    @Test
    public void receivesEventsFromAuctionServerAfterJoining() throws Exception {
        CountDownLatch auctionWasClosed = new CountDownLatch(1);

        Auction auction = auctionHouse.auctionFor(new Item(auctionServer.getItemId(), 567));
        auction.addAuctionEventListener(auctionClosedListener(auctionWasClosed));

        auction.join();
        auctionServer.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID);
        auctionServer.announceClosed();

        assertTrue("should have been closed", auctionWasClosed.await(2, TimeUnit.SECONDS));
    }

    private AuctionEventListener auctionClosedListener(CountDownLatch auctionWasClosed) {
        return new AuctionEventListener() {

            @Override
            public void auctionClosed() {
                auctionWasClosed.countDown();
            }

            @Override
            public void currentPrice(int price, int increment, PriceSource priceSource) {
                // no op
            }
        };
    }

    @Before
    public void createConnection() throws XMPPException {
        auctionHouse = XMPPAuctionHouse.connect(FakeAuctionServer.XMPP_HOSTNAME, ApplicationRunner.SNIPER_ID, ApplicationRunner.SNIPER_PASSWORD);
    }

    @Before
    public void startTheAuction() throws XMPPException {
        auctionServer.startSellingItem();
    }

    @After
    public void closeConnection() {
        if (auctionHouse != null) {
            auctionHouse.disconnect();
        }
    }

    @After
    public void stopAuction() {
        auctionServer.stop();
    }
}
