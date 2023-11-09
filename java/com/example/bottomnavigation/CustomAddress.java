package com.example.bottomnavigation;

public class CustomAddress {
    private String id;
    private String address;
    private String mobileNumber; // New field for mobile number

    public CustomAddress() {
        // Default constructor required for Firebase
    }

    public CustomAddress(String id, String address, String mobileNumber) {
        this.id = id;
        this.address = address;
        this.mobileNumber = mobileNumber;
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }
}
