package com.bmh10.twitteranalytics.linkedin;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import java.io.*;


public class LinkedinFacade {

    private static String API_KEY = "xxxxxxxxxxxxxx";
    private static String API_SECRET = "xxxxxxxxxxxxxx";

    public static void main(String[] args) {
        OAuthService service = new ServiceBuilder()
                .provider(LinkedInApi.class)
                .apiKey(API_KEY)
                .apiSecret(API_SECRET)
                .build();

        Token accessToken = getAccess(service);
        printProfile(service, accessToken);
        printConncetions(service, accessToken);
    }

    public static void printProfile(OAuthService service, Token accessToken) {
        String url = "http://api.linkedin.com/v1/people/~";
        OAuthRequest request = new OAuthRequest(Verb.GET, url);
        request.addHeader("x-li-format", "json");
        service.signRequest(accessToken, request);
        Response response = request.send();
        System.out.println(response.getBody());
    }

    public static void printConncetions(OAuthService service, Token accessToken) {
        String url = "http://api.linkedin.com/v1/people/~/connections";
        OAuthRequest request = new OAuthRequest(Verb.GET, url);
        service.signRequest(accessToken, request);
        Response response = request.send();
        System.out.println(response.getBody());
    }

    public static Token getAccess(OAuthService oAuthService) {
        try {
            File file = new File("service.dat");

            if(file.exists()){
                //if the file exists we assume it has the AuthHandler in it - which in turn contains the Access Token
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
                AuthHandler authHandler = (AuthHandler) inputStream.readObject();
                return authHandler.getAccessToken();
            } else {
                System.out.println("There is no stored Access token we need to make one");
                //In the constructor the AuthHandler goes through the chain of calls to create an Access Token
                AuthHandler authHandler = new AuthHandler(oAuthService);
                ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("service.dat"));
                outputStream.writeObject( authHandler);
                outputStream.close();
                return authHandler.getAccessToken();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}