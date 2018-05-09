package com.guowei.lv.auctionsniper.xmpp;

public class MissingValueException extends RuntimeException {

    public MissingValueException(String fieldName) {
        super(fieldName);
    }
}
