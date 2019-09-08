package com.sample.nintextest.utils;

public class Utils {
    public static final int FLIGHT_SEARCH_SCREEN = 10001;
    public static final int FLIGHT_RESULT_SCREEN = 10002;

    public static final String BASE_URL = "https://nmflightapi.azurewebsites.net/";

    public enum Status {
        LOADING,
        SUCCESS,
        ERROR
    }
}
