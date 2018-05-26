package com.bmh10.twitteranalytics.twitter;

import twitter4j.*;

import java.util.ArrayList;
import java.util.List;

public class FriendGraphBuilder {

    private Twitter twitter;
    private String memberName;
    private int depth;

    public FriendGraphBuilder(String memberName, int depth) {
        this.memberName = memberName;
        twitter = new TwitterFactory().getInstance();
    }

    public Node build() {
        try {
            return build(memberName, depth);
        } catch (TwitterException e) {
            throw new RuntimeException(e);
        }
    }

    private Node build(String memberName, int depth) throws TwitterException {
        Node n = new Node();
        n.user = twitter.showUser(memberName);
        n.friends = new ArrayList<>();
        if (depth > 0) {
            for (User friend : getFriends(memberName)) {
                n.friends.add(build(friend.getScreenName(), depth - 1));
            }
        }

        return n;
    }

    private List<User> getFriends(String memberName) throws TwitterException {
        long cursor = -1;
        PagableResponseList<User> friends;
        List<User> fs = new ArrayList<>();
        do {
            friends = twitter.getFriendsList(memberName, cursor, 200);
            fs.addAll(friends);
        } while ((cursor = friends.getNextCursor()) != 0);

        return fs;
    }

    class Node {
        private User user;
        private List<Node> friends;
    }
}
