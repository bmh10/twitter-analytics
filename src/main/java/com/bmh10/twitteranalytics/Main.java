package com.bmh10.twitteranalytics;

public class Main {

    public static void main(String[] args) {
        //TwitterFacade twitterFacade = new TwitterFacade("realDonaldTrump");
        //twitterFacade.printAllUserInfo();

        FriendGraphBuilder friendGraphBuilder = new FriendGraphBuilder("realDonaldTrump", 1);
        FriendGraphBuilder.Node root = friendGraphBuilder.build();
        System.out.printf(root.toString());
    }
}
