package com.muthusoft.edi.model;

/**
 * Created by admin on 05-12-2016.
 */
public class Gender {

    private int id;
    private String gender_name;

    public Gender() {
    }

    public Gender(int id, String gender_name) {
        this.id = id;
        this.gender_name = gender_name;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGender_name() {
        return gender_name;
    }

    public void setGender_name(String gender_name) {
        this.gender_name = gender_name;
    }
}
