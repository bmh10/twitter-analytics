package com.bmh10.twitteranalytics.facebook;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;
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
}
