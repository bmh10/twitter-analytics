package com.bmh10.twitteranalytics.linkedin;

import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import java.io.Serializable;
import java.util.Scanner;

public class AuthHandler implements Serializable {
    private Token accessToken = null;

    public AuthHandler(OAuthService serviceProvider) {
        Scanner in = new Scanner(System.in);
        Token requestToken = serviceProvider.getRequestToken();
        System.out.println(serviceProvider.getAuthorizationUrl(requestToken));
        System.out.println("And paste the verifier here");
        System.out.print(">>");
        Verifier verifier = new Verifier(in.nextLine());
        accessToken = serviceProvider.getAccessToken(requestToken, verifier);
    }

    public Token getAccessToken() {
        return this.accessToken;
    }
}
