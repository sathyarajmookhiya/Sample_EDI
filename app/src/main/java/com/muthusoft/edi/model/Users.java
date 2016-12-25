package com.muthusoft.edi.model;

/**
 * Created by Hari on 06-09-2016.
 */
public class Users {

    int user_id;
    int prifix_id;
    String username;
    String name;
    String password;
    String password_hashed;
    String dob;
    int gender_id;
    String mobile;
    String email;
    String company_name;
    int districid;
    int pincode;
    String address;
    String industry_id;
    String specialization_id;
    String support_required;
    String aadhar_no;
    int is_active;
    String photo_path;
    int remember_me;

    public Users() {
    }

    public Users(String email, String mobile) {
        this.email = email;
        this.mobile = mobile;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPrifix_id() {
        return prifix_id;
    }

    public void setPrifix_id(int prifix_id) {
        this.prifix_id = prifix_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_hashed() {
        return password_hashed;
    }

    public void setPassword_hashed(String password_hashed) {
        this.password_hashed = password_hashed;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public int getGender_id() {
        return gender_id;
    }

    public void setGender_id(int gender_id) {
        this.gender_id = gender_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public int getDistricid() {
        return districid;
    }

    public void setDistricid(int districid) {
        this.districid = districid;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIndustry_id() {
        return industry_id;
    }

    public void setIndustry_id(String industry_id) {
        this.industry_id = industry_id;
    }

    public String getSpecialization_id() {
        return specialization_id;
    }

    public void setSpecialization_id(String specialization_id) {
        this.specialization_id = specialization_id;
    }

    public String getSupport_required() {
        return support_required;
    }

    public void setSupport_required(String support_required) {
        this.support_required = support_required;
    }

    public String getAadhar_no() {
        return aadhar_no;
    }

    public void setAadhar_no(String aadhar_no) {
        this.aadhar_no = aadhar_no;
    }

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }

    public int getRemember_me() {
        return remember_me;
    }

    public void setRemember_me(int remember_me) {
        this.remember_me = remember_me;
    }


}
