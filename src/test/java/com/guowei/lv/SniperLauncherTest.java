package com.guowei.lv;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.States;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class SniperLauncherTest {
    private final Mockery context = new Mockery();
    private final Auction auction = context.mock(Auction.class);
    private final AuctionHouse auctionHouse = context.mock(AuctionHouse.class);
    private final SniperCollector sniperCollector = context.mock(SniperCollector.class);
    private final SniperLauncher launcher = new SniperLauncher(auctionHouse, sniperCollector);
    private final States auctionState = context.states("auction state").startsAs("not joined");

    @Test
    public void addsNewSniperToCollectorAndThenJoinsAuction() {
        final Item item = new Item("item 123", 456);
        context.checking(new Expectations() {{
            allowing(auctionHouse).auctionFor(item); will(returnValue(auction));

            oneOf(auction).addAuctionEventListener(with(sniperForItem(item)));
            when(auctionState.is("not joined"));

            oneOf(sniperCollector).addSniper(with(sniperForItem(item)));
            when(auctionState.is("not joined"));

            oneOf(auction).join(); then(auctionState.is("joined"));
        }});

        launcher.joinAuction(item);
    }

    private Matcher<AuctionSniper> sniperForItem(Item item) {
        return new FeatureMatcher<AuctionSniper, String>(equalTo(item.identifier), "sniper with itemId id", "itemId") {
            @Override
            protected String featureValueOf(AuctionSniper actual) {
                return actual.getSnapshot().getItemId();
            }
        };
    }
}
