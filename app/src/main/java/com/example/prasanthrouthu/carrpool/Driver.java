package com.example.prasanthrouthu.carrpool;

import java.util.Date;

/**
 * Created by prasanthrouthu on 10/02/18.
 */



public class Driver {

    private String Source;
    private String Destination;
    private String CarModelS,CarNumberS,SeatsS,PhoneNumberS,RateS,User;
    private String Date,Time,Name;

    public Driver() {
        // This is default constructor.
    }

    public String getDriverSource() {

        return Source;
    }

    public void setDriverSource(String Source) {

        this.Source = Source;
    }

    public String getDriverDestination() {

        return Destination;
    }

    public void setDriverDestination(String Destination) {

        this.Destination = Destination;
    }

    public String getCarModelS(){

        return CarModelS;

    }

    public void setCarModelS(String CarModelS){

        this.CarModelS = CarModelS;

    }

    public String getCarNumberS(){

        return CarNumberS;

    }

    public void setCarNumberS(String CarNumberS){

        this.CarNumberS = CarNumberS;

    }

    public String getSeatsS(){

        return SeatsS;

    }

    public void setSeatsS(String SeatsS){

        this.SeatsS = SeatsS;

    }

    public String getPhoneNumberS(){

        return PhoneNumberS;

    }

    public void setPhoneNumberS(String PhoneNumberS){

        this.PhoneNumberS = PhoneNumberS;

    }

    public String getRateS(){

        return RateS;

    }

    public void setRateS(String RatesS){

        this.RateS = RatesS;

    }

    public String getDate(){

        return Date;

    }

    public void setDate(String Date){

        this.Date = Date;

    }

    public String getTime(){

        return Time;

    }

    public void setTime(String Time){

        this.Time = Time;

    }

    public  String getName(){

        return Name;

    }

    public void setName(String Name){

        this.Name = Name;

    }

}