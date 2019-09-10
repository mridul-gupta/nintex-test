package com.sample.nintextest.model;


import android.util.Log;

import androidx.annotation.NonNull;

import com.sample.nintextest.network.ApiCallback;
import com.sample.nintextest.network.ApiInterface;
import com.sample.nintextest.network.RemoteDataSource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlightRepository {

    private volatile static FlightRepository INSTANCE = null;


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

        ApiInterface apiInterface = RemoteDataSource.getApiInterface();

        Call<List<Flight>> call = apiInterface.getAvailableFlights(departureAirportCode, arrivalAirportCode, departureDate, returnDate);
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
            public void onFailure(@NonNull Call<List<Flight>> call, @NonNull Throwable t) {
                apiCallback.onFailure();
            }
        });
    }
}
