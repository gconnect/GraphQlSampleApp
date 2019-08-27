package com.pdl.graghqlexample;

import com.apollographql.apollo.ApolloClient;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class MyApoloClient {
    private static final String BASE_URL = "https://api.graph.cool/simple/v1/cjylj8e2d080s0130bqorvc5l";
    private static ApolloClient myApoloClient;

    public static ApolloClient getMyApoloClient(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        myApoloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(client)
                .build();

        return myApoloClient;

    }
}
