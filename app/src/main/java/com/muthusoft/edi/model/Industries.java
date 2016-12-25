package com.muthusoft.edi.model;

/**
 * Created by admin on 05-12-2016.
 */
public class Industries {

    private int id;
    private String industies_name;
    public Industries() {
    }

    public Industries(int id, String industies_name) {
        this.id = id;
        this.industies_name = industies_name;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIndusties_name() {
        return industies_name;
    }

    public void setIndusties_name(String industies_name) {
        this.industies_name = industies_name;
    }
}
