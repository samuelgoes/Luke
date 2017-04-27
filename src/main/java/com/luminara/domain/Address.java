package com.luminara.domain;

import org.springframework.data.mongodb.core.mapping.Field;

public class Address {

    private String addressLine1;
    private String addressLine2;
    private String city;
    @Field("zip_code")
    private Integer zipCode;


    public Address(){}

    @Override
    public String toString(){
        StringBuilder toString = new StringBuilder();

        toString.append("Address Line 1: " + addressLine1)
                .append("Address Line 2: " + addressLine2)
                .append("City: " + city)
                .append("Zip Code: " + zipCode);

        return toString.toString();
    }


    //****************************
    //*     GETTERS & SETTERS    *
    //****************************


    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }
}
