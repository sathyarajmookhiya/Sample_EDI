package com.muthusoft.edi.model;

/**
 * Created by admin on 05-12-2016.
 */
public class Specilazation {
    private int id;
    private int indust_id;
    private String specilazation_name;

    public Specilazation() {
    }

    public Specilazation(int id, int indust_id, String specilazation_name) {
        this.id = id;
        this.indust_id = indust_id;
        this.specilazation_name = specilazation_name;
    }

    public int getIndust_id() {
        return indust_id;
    }

    public void setIndust_id(int indust_id) {
        this.indust_id = indust_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpecilazation_name() {
        return specilazation_name;
    }

    public void setSpecilazation_name(String specilazation_name) {
        this.specilazation_name = specilazation_name;
    }
}
