package com.sample.nintextest.network;

public interface ApiCallback<V> {
    void onFailure();

    void onSuccess(V data);
}