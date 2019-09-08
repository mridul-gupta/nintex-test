package com.sample.nintextest.network;

import com.sample.nintextest.model.Flight;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/api/Flight")
    Call<List<Flight>> getAvailableFlights(@Query("DepartureAirportCode") String DepartureAirportCode,
                                           @Query("ArrivalAirportCode") String ArrivalAirportCode,
                                           @Query("DepartureDate") String DepartureDate,
                                           @Query("ReturnDate") String ReturnDate);
}
