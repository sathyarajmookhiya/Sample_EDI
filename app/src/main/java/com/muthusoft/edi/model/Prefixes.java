package com.muthusoft.edi.model;

/**
 * Created by admin on 05-12-2016.
 */
public class Prefixes {

    private int id;
    private String prefixes_name;
    public Prefixes() {
    }

    public Prefixes(int id, String prefixes_name) {
        this.id = id;
        this.prefixes_name = prefixes_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrefixes_name() {
        return prefixes_name;
    }

    public void setPrefixes_name(String prefixes_name) {
        this.prefixes_name = prefixes_name;
    }
}
