package com.sample.nintextest.model;


import android.util.Log;

import androidx.annotation.NonNull;

import com.sample.nintextest.network.ApiCallback;
import com.sample.nintextest.network.ApiClient;
import com.sample.nintextest.network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlightRepository {

    private volatile static FlightRepository INSTANCE = null;

    public FlightRepository() {
        //this.apiInterface = apiCallInterface;
    }

    public static FlightRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (FlightRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FlightRepository();
                }
            }
        }
        return INSTANCE;
    }

    /*
     * method to call get flights api
     * */
    public void executeGetFlights(String departureAirportCode,
                                  String arrivalAirportCode,
                                  String departureDate,
                                  String returnDate,
                                  ApiCallback<List<Flight>> apiCallback) {


        Call<List<Flight>> call = ApiClient.getClient().create(ApiInterface.class).getAvailableFlights(departureAirportCode, arrivalAirportCode, departureDate, returnDate);
        call.enqueue(new Callback<List<Flight>>() {

            @Override
            public void onResponse(@NonNull Call<List<Flight>> call, @NonNull Response<List<Flight>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (apiCallback != null)
                        apiCallback.onSuccess(response.body());
                    else
                        Log.e("executeGetFlights: ", "success : Network callback null");
                }
            }

            @Override
            public void onFailure(Call<List<Flight>> call, Throwable t) {
                apiCallback.onFailure();
            }
        });
    }
}
