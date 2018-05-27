package com.bmh10.twitteranalytics.facebook;

import facebook4j.*;
import facebook4j.auth.AccessToken;

public class FacebookFacade {

    private Facebook facebook;
    private String appId;
    private String appSecret;
    private String permissions;
    private String accessToken;

    public FacebookFacade() {
        this.facebook = new FacebookFactory().getInstance();
        facebook.setOAuthAppId(appId, appSecret);
        facebook.setOAuthPermissions(permissions);
        facebook.setOAuthAccessToken(new AccessToken(accessToken, null));
    }

    public ResponseList<User> getUsers(String query) throws FacebookException {
        return facebook.searchUsers(query);
    }

    public ResponseList<Post> getPosts(String query) throws FacebookException {
        return facebook.searchPosts(query);
    }

    public ResponseList<Place> searchPlaces(
            String query, double longitude, double latitude, int distance) throws FacebookException {
        return facebook.searchPlaces(query, new GeoLocation(longitude, latitude), distance);
    }
}


