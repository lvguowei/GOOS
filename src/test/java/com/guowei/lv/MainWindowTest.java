package com.guowei.lv;

import com.objogate.wl.swing.probe.ValueMatcherProbe;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class MainWindowTest {
    private final SniperPortfolio portfolio = new SniperPortfolio();
    private final MainWindow mainWindow = new MainWindow(portfolio);
    private final AuctionSniperDriver driver = new AuctionSniperDriver(100);

    @Test
    public void makesUserRequestWhenJoinButtonClicked() {
        final ValueMatcherProbe<Item> itemProbe = new ValueMatcherProbe<>(equalTo(new Item("item-id", 789)), "item request");

        mainWindow.addUserRequestListener(itemProbe::setReceivedValue);

        driver.startBiddingFor("item-id", 789);
        driver.check(itemProbe);
    }
}
