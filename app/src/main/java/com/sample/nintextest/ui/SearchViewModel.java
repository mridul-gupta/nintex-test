package com.sample.nintextest.ui;

import android.app.Application;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.ViewModel;

import com.sample.nintextest.model.Flight;
import com.sample.nintextest.model.FlightRepository;
import com.sample.nintextest.network.ApiCallback;

import java.util.List;

public class SearchViewModel extends ViewModel {


    private FlightRepository mRepository;
    public final ObservableList<Flight> mFlightlist;

    public SearchViewModel(Application application) {
        mFlightlist = new ObservableArrayList<>();
        this.mRepository = initRepository();
    }

    public FlightRepository initRepository() {
        mRepository = FlightRepository.getInstance();
        return mRepository;
    }

    public void getFlights(String departureAirportCode,
                           String arrivalAirportCode,
                           String departureDate,
                           String returnDate) {

        mRepository.executeGetFlights(departureAirportCode, arrivalAirportCode, departureDate, returnDate, new ApiCallback<List<Flight>>() {

            @Override
            public void onFailure() {

            }

            @Override
            public void onSuccess(List<Flight> flights) {
                mFlightlist.clear();
                mFlightlist.addAll(flights);
            }
        });


    }
}
