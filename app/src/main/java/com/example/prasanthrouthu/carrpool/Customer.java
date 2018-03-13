package com.example.prasanthrouthu.carrpool;

/**
 * Created by prasanthrouthu on 05/03/18.
 */

public class Customer {
    private String Source;
    private String Destination;

    public Customer() {
        // This is default constructor.
    }

    public String getCustomerSource() {

        return Source;
    }

    public void setCustomerSource(String Source) {

        this.Source = Source;
    }

    public String getCustomerDestination() {

        return Destination;
    }

    public void setCustomerDestination(String Destination) {

        this.Destination = Destination;
    }

}
