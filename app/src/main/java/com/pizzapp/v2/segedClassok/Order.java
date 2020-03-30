package com.pizzapp.v2.segedClassok;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;


public class Order {
    private String Address, Name;
    private int FullPrice, NumOfPizzas;

    @Keep
    public Order() {
    }

    public Order(String address, String name, int fullPrice, int numOfPizzas) {
        Address = address;
        Name = name;
        FullPrice = fullPrice;
        NumOfPizzas = numOfPizzas;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getFullPrice() {
        return FullPrice;
    }

    public void setFullPrice(int fullPrice) {
        FullPrice = fullPrice;
    }

    public int getNumOfPizzas() {
        return NumOfPizzas;
    }

    public void setNumOfPizzas(int numOfPizzas) {
        NumOfPizzas = numOfPizzas;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
