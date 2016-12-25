package com.muthusoft.edi.model;

/**
 * Created by admin on 05-12-2016.
 */
public class Districts {

    private int id;
    private String district_name;
    public Districts() {
    }

    public Districts(int id, String district_name) {
        this.id = id;
        this.district_name = district_name;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }
}
