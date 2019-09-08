package com.sample.nintextest.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Flight {

    @SerializedName("AirlineLogoAddress")
    private String airlineLogoAddress;

    @SerializedName("AirlineName")
    private String airlineName;

    @SerializedName("InboundFlightsDuration")
    private String inboundFlightsDuration;

    @SerializedName("ItineraryId")
    private transient int itineraryId;

    @SerializedName("OutboundFlightsDuration")
    private String outboundFlightsDuration;

    @SerializedName("Stops")
    private int stops;

    @SerializedName("TotalAmount")
    private float totalAmount;

    public Flight(String airlineLogoAddress, String airlineName, String inboundFlightsDuration, int itineraryId, String outboundFlightsDuration, int stops, float totalAmount) {
        this.airlineLogoAddress = airlineLogoAddress;
        this.airlineName = airlineName;
        this.inboundFlightsDuration = inboundFlightsDuration;
        this.itineraryId = itineraryId;
        this.outboundFlightsDuration = outboundFlightsDuration;
        this.stops = stops;
        this.totalAmount = totalAmount;
    }

    @NonNull
    public String getAirlineLogoAddress() {
        return airlineLogoAddress;
    }

    public void setAirlineLogoAddress(@NonNull String airlineLogoAddress) {
        this.airlineLogoAddress = airlineLogoAddress;
    }

    @NonNull
    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(@NonNull String airlineName) {
        this.airlineName = airlineName;
    }

    @NonNull
    public String getInboundFlightsDuration() {
        return inboundFlightsDuration;
    }

    public void setInboundFlightsDuration(@NonNull String inboundFlightsDuration) {
        this.inboundFlightsDuration = inboundFlightsDuration;
    }

    public int getItineraryId() {
        return itineraryId;
    }

    public void setItineraryId(int itineraryId) {
        this.itineraryId = itineraryId;
    }

    @NonNull
    public String getOutboundFlightsDuration() {
        return outboundFlightsDuration;
    }

    public void setOutboundFlightsDuration(@NonNull String outboundFlightsDuration) {
        this.outboundFlightsDuration = outboundFlightsDuration;
    }

    public int getStops() {
        return stops;
    }

    public void setStops(int stops) {
        this.stops = stops;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "airlineLogoAddress='" + airlineLogoAddress + '\'' +
                ", airlineName='" + airlineName + '\'' +
                ", inboundFlightsDuration='" + inboundFlightsDuration + '\'' +
                ", itineraryId=" + itineraryId +
                ", outboundFlightsDuration='" + outboundFlightsDuration + '\'' +
                ", stops=" + stops +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
