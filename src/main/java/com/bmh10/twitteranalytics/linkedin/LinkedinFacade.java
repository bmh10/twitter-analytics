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

        System.out.println();
        System.out.println("********A basic user profile call********");
        //The ~ means yourself - so this should return the basic default information for your profile in XML format
        //https://developer.linkedin.com/documents/profile-api
        String url = "http://api.linkedin.com/v1/people/~";
        OAuthRequest request = new OAuthRequest(Verb.GET, url);
        service.signRequest(accessToken, request);
        Response response = request.send();
        System.out.println(response.getBody());
        System.out.println();System.out.println();

        System.out.println("********Get the profile in JSON********");
        //This basic call profile in JSON format
        //You can read more about JSON here http://json.org
        url = "http://api.linkedin.com/v1/people/~";
        request = new OAuthRequest(Verb.GET, url);
        request.addHeader("x-li-format", "json");
        service.signRequest(accessToken, request);
        response = request.send();
        System.out.println(response.getBody());
        System.out.println();System.out.println();

        System.out.println("********Get the profile in JSON using query parameter********");
        //This basic call profile in JSON format. Please note the call above is the preferred method.
        //You can read more about JSON here http://json.org
        url = "http://api.linkedin.com/v1/people/~";
        request = new OAuthRequest(Verb.GET, url);
        request.addQuerystringParameter("format", "json");
        service.signRequest(accessToken, request);
        response = request.send();
        System.out.println(response.getBody());
        System.out.println();System.out.println();


        System.out.println("********Get my connections - going into a resource********");
        //This basic call gets all your connections each one will be in a person tag with some profile information
        //https://developer.linkedin.com/documents/connections-api
        url = "http://api.linkedin.com/v1/people/~/connections";
        request = new OAuthRequest(Verb.GET, url);
        service.signRequest(accessToken, request);
        response = request.send();
        System.out.println(response.getBody());
        System.out.println();System.out.println();


        System.out.println("********Get only 10 connections - using parameters********");
        //This basic call gets only 10 connections  - each one will be in a person tag with some profile information
        //https://developer.linkedin.com/documents/connections-api
        //more basic about query strings in a URL here http://en.wikipedia.org/wiki/Query_string
        url = "http://api.linkedin.com/v1/people/~/connections";
        request = new OAuthRequest(Verb.GET, url);
        request.addQuerystringParameter("count", "10");
        service.signRequest(accessToken, request);
        response = request.send();
        System.out.println(response.getBody());
        System.out.println();System.out.println();


        System.out.println("********GET network updates that are CONN and SHAR********");
        //This basic call get connection updates from your connections
        //https://developer.linkedin.com/documents/get-network-updates-and-statistics-api
        //specifics on updates  https://developer.linkedin.com/documents/network-update-types

        url = "http://api.linkedin.com/v1/people/~/network/updates";
        request = new OAuthRequest(Verb.GET, url);
        request.addQuerystringParameter("type","SHAR");
        request.addQuerystringParameter("type","CONN");
        service.signRequest(accessToken, request);
        response = request.send();
        System.out.println(response.getBody());
        System.out.println();System.out.println();


        System.out.println("********People Search using facets and Encoding input parameters i.e. UTF8********");
        //This basic call get connection updates from your connections
        //https://developer.linkedin.com/documents/people-search-api#Facets
        //Why doesn't this look like
        //people-search?title=developer&location=fr&industry=4

        //url = "http://api.linkedin.com/v1/people-search?title=D%C3%A9veloppeur&facets=location,industry&facet=location,fr,0";
        url = "http://api.linkedin.com/v1/people-search:(people:(first-name,last-name,headline),facets:(code,buckets))";
        request = new OAuthRequest(Verb.GET, url);
        request.addQuerystringParameter("title", "Développeur");
        request.addQuerystringParameter("facet", "industry,4");
        request.addQuerystringParameter("facets", "location,industry");
        System.out.println(request.getUrl());
        service.signRequest(accessToken, request);
        response = request.send();
        System.out.println(response.getBody());
        System.out.println();System.out.println();

        /////////////////field selectors
        System.out.println("********A basic user profile call with field selectors********");
        //The ~ means yourself - so this should return the basic default information for your profile in XML format
        //https://developer.linkedin.com/documents/field-selectors
        url = "http://api.linkedin.com/v1/people/~:(first-name,last-name,positions)";
        request = new OAuthRequest(Verb.GET, url);
        service.signRequest(accessToken, request);
        response = request.send();
        System.out.println(response.getHeaders().toString());
        System.out.println(response.getBody());
        System.out.println();System.out.println();


        System.out.println("********A basic user profile call with field selectors going into a subresource********");
        //The ~ means yourself - so this should return the basic default information for your profile in XML format
        //https://developer.linkedin.com/documents/field-selectors
        url = "http://api.linkedin.com/v1/people/~:(first-name,last-name,positions:(company:(name)))";
        request = new OAuthRequest(Verb.GET, url);
        service.signRequest(accessToken, request);
        response = request.send();
        System.out.println(response.getHeaders().toString());
        System.out.println(response.getBody());
        System.out.println();System.out.println();


        System.out.println("********A basic user profile call into a subresource return data in JSON********");
        //The ~ means yourself - so this should return the basic default information for your profile
        //https://developer.linkedin.com/documents/field-selectors
        url = "https://api.linkedin.com/v1/people/~/connections:(first-name,last-name,headline)?format=json";
        request = new OAuthRequest(Verb.GET, url);
        service.signRequest(accessToken, request);
        response = request.send();
        System.out.println(response.getHeaders().toString());
        System.out.println(response.getBody());
        System.out.println();System.out.println();

        System.out.println("********A more complicated example using postings into groups********");
        //https://developer.linkedin.com/documents/field-selectors
        //https://developer.linkedin.com/documents/groups-api
        url = "http://api.linkedin.com/v1/groups/3297124/posts:(id,category,creator:(id,first-name,last-name),title,summary,creation-timestamp,site-group-post-url,comments,likes)";
        request = new OAuthRequest(Verb.GET, url);
        service.signRequest(accessToken, request);
        response = request.send();
        System.out.println(response.getHeaders().toString());
        System.out.println(response.getBody());
        System.out.println();System.out.println();

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