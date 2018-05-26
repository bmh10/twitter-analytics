package com.bmh10.twitteranalytics;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import twitter4j.*;

public class TwitterFacade {

    private Twitter twitter;
    private String memberName;
    private Gson gson;

    public TwitterFacade(String memberName) {
        this.memberName = memberName;
        this.twitter = new TwitterFactory().getInstance();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void printAllUserInfo() {
        System.out.println("BASIC INFO");
        printBasicUserInfo();
        System.out.println("FRIENDS INFO");
        printUserFriendInfo();
        System.out.println("STATUS INFO");
        printUserStatuses();
    }

    public void printBasicUserInfo() {
        try {
            User user = twitter.showUser(memberName);
            printUser(user);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
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

    public void printUserStatuses() {
        try {
            ResponseList<Status> statuses = twitter.getUserTimeline(memberName);
            statuses.forEach(this::printStatus);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    private void printUser(User user) {
        System.out.println(gson.toJson(user));
    }

    private void printStatus(Status status) {
        System.out.println(gson.toJson(status));
    }
}
