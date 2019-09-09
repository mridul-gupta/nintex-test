package com.sample.nintextest.ui;

import android.app.Application;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sample.nintextest.model.Flight;
import com.sample.nintextest.model.FlightRepository;
import com.sample.nintextest.network.ApiCallback;
import com.sample.nintextest.utils.Utils.Status;

import java.util.List;

import static com.sample.nintextest.utils.Utils.Status.*;

public class SearchViewModel extends ViewModel {


    private FlightRepository mRepository;
    public final ObservableList<Flight> mFlightlist;

    MutableLiveData<Status> responseStatus = new MutableLiveData<>(IDLE);

    public SearchViewModel(Application application) {
        mFlightlist = new ObservableArrayList<>();
        this.mRepository = initRepository();
    }

    private FlightRepository initRepository() {
        mRepository = FlightRepository.getInstance();
        return mRepository;
    }

    void getFlights(String departureAirportCode,
                    String arrivalAirportCode,
                    String departureDate,
                    String returnDate) {

        responseStatus.setValue(LOADING);

        mRepository.executeGetFlights(departureAirportCode, arrivalAirportCode, departureDate, returnDate, new ApiCallback<List<Flight>>() {

            @Override
            public void onFailure() {
                responseStatus.setValue(ERROR);
            }

            @Override
            public void onSuccess(List<Flight> flights) {
                mFlightlist.clear();
                mFlightlist.addAll(flights);
                responseStatus.setValue(SUCCESS);
            }
        });
    }
}
