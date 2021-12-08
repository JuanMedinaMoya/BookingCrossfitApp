package com.example.bookingcrossfitapp;


import java.util.HashMap;

class SingletonMap extends HashMap<String,Object> {
    private static class SingletonHolder {
        private static final SingletonMap ourInstance = new SingletonMap();
    }
    public static SingletonMap getInstance() {
        return SingletonHolder.ourInstance;
    }
    private SingletonMap() {}
}





