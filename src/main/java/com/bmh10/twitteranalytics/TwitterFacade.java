package com.bmh10.twitteranalytics;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import twitter4j.*;

public class TwitterFacade {

    private Twitter twitter;
    private String memberName;

    public TwitterFacade(String memberName) {
        this.memberName = memberName;
        twitter = new TwitterFactory().getInstance();
    }

    public void printAllUserInfo() {
        System.out.println("BASIC INFO");
        printBasicUserInfo();
        System.out.println("FRIENDS INFO");
        printUserFriendInfo();
    }

    public void printUserFriendInfo() {
        try {
            long cursor = -1;
            PagableResponseList<User> friends;
            do {
                friends = twitter.getFriendsList(memberName, cursor, 200);
                friends.forEach(this::printUser);
            } while ((cursor = friends.getNextCursor()) != 0);

        } catch (TwitterException te) {
            te.printStackTrace();
        }
    }

    public void printBasicUserInfo() {
        try {
            User user = twitter.showUser(memberName);
            printUser(user);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    private void printUser(User user) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(user));
    }
}
