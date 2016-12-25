package com.muthusoft.edi.model;

/**
 * Created by admin on 05-12-2016.
 */
public class University {

    private int id;
    private String univercity_name;

    public University() {
    }

    public University(int id, String univercity_name) {
        this.id = id;
        this.univercity_name = univercity_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnivercity_name() {
        return univercity_name;
    }

    public void setUnivercity_name(String univercity_name) {
        this.univercity_name = univercity_name;
    }


}
