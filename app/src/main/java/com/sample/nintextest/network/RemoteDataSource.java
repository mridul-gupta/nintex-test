package com.sample.nintextest.network;

public class RemoteDataSource {
    private static ApiInterface sApiInterface = ApiClient.getClient().create(ApiInterface.class);

    public static ApiInterface getApiInterface() {
        return sApiInterface;
    }
}
