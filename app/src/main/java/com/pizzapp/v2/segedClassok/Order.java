package com.pizzapp.v2.segedClassok;

import androidx.annotation.Keep;

public class Order {
    private String deliName;

    @Keep
    public Order() {

    }

    @Keep
    public Order(String deliName) {
        this.deliName = deliName;
    }

    public String getDeliName() {
        return deliName;
    }

    public void setDeliName(String deliName) {
        this.deliName = deliName;
    }
}
