package com.muthusoft.edi.model;

/**
 * Created by admin on 05-12-2016.
 */
public class Colleges {
    private int id;
    private String colleges_name;

    public Colleges() {
    }

    public Colleges(int id, String colleges_name) {
        this.id = id;
        this.colleges_name = colleges_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColleges_name() {
        return colleges_name;
    }

    public void setColleges_name(String colleges_name) {
        this.colleges_name = colleges_name;
    }


}
