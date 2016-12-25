package com.muthusoft.edi.model;

/**
 * Created by admin on 05-12-2016.
 */
public class BankCategories {
    private int id;
    private String bank_name;

    public BankCategories() {
    }

    public BankCategories(int id, String bank_name) {
        this.id = id;
        this.bank_name = bank_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

}
