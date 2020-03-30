package com.pizzapp.v2.segedClassok;

public class Globals {
    private static Globals instance = new Globals();
    private String string;

    private Globals() {
    }

    public static Globals getInstance() {
        return instance;
    }

    public static void setInstance(Globals instance) {
        Globals.instance = instance;
    }

    public String getValue() {
        return string;
    }

    public void setValue(String s) {
        this.string = s;
    }
}
