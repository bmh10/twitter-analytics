package com.bmh10.twitteranalytics;

import twitter4j.*;

public class TwitterFacade {

    public static void main(String[] args) {
        try {
            Twitter twitter = new TwitterFactory().getInstance();
            long cursor = -1;
            PagableResponseList<User> friends;
            do {
                friends = twitter.getFriendsList("realDonaldTrump", cursor, 200);
                for (User friend : friends) {
                    System.out.println("@" + friend.getScreenName());
                }
            } while ((cursor = friends.getNextCursor()) != 0);

        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to lookup friendships: " + te.getMessage());
            System.exit(-1);
        }
    }

}
