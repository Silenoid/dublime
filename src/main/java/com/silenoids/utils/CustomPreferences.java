package com.silenoids.utils;

public class CustomPreferences {

    private static CustomPreferences instance;

    public static CustomPreferences getInstance() {
        if (instance == null) {
            instance = new CustomPreferences();
        }
        return instance;
    }

    public void loadPreferences() {

    }

}
