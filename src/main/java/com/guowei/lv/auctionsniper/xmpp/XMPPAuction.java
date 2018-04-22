package com.guowei.lv.auctionsniper.xmpp;

import com.guowei.lv.Announcer;
import com.guowei.lv.Auction;
import com.guowei.lv.AuctionEventListener;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import static com.guowei.lv.XMPPAuctionHouse.AUCTION_ID_FORMAT;

public class XMPPAuction implements Auction {

    public static String JOIN_COMMAND_FORMAT = "SOLVersion: 1.1; Command: JOIN;";
    public static String BID_COMMAND_FORMAT = "SOLVersion: 1.1; Command: BID; Price: %d;";

    private final Announcer<AuctionEventListener> auctionEventListeners = Announcer.to(AuctionEventListener.class);
    private final Chat chat;

    public XMPPAuction(XMPPConnection connection, String auctionId) {
        chat = connection.getChatManager().createChat(auctionId, new AuctionMessageTranslator(connection.getUser(), auctionEventListeners.announce()));
    }

    @Override
    public void bid(int price) {
        sendMessage(String.format(BID_COMMAND_FORMAT, price));
    }

    @Override
    public void join() {
        sendMessage(JOIN_COMMAND_FORMAT);
    }

    @Override
    public void addAuctionEventListener(AuctionEventListener auctionEventListener) {
        auctionEventListeners.addListener(auctionEventListener);
    }

    private void sendMessage(final String message) {
        try {
            chat.sendMessage(message);
        } catch (XMPPException e) {
            e.printStackTrace();
        }
    }
}
