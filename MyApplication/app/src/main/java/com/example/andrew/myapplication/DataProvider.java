package com.example.andrew.myapplication;

/**
 * Created by Andrew on 08/03/2016.
 */
public class DataProvider {
    private String petName;
    private String petBody;

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetBody() {
        return petBody;
    }

    public void setPetBody(String petBody) {
        this.petBody = petBody;
    }

    public DataProvider(String petName, String petBody) {
        this.petName = petName;
        this.petBody = petBody;
    }
}
