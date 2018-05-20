package com.bmh10.twitteranalytics;

public class Main {

    public static void main(String[] args) {
        TwitterFacade twitterFacade = new TwitterFacade("realDonaldTrump");
        twitterFacade.printAllUserInfo();
    }
}
