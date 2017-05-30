package ru.ijava.pkpp.model;

import java.io.Serializable;

/**
 * Created by rele on 5/21/17.
 */
public class Person implements Serializable {

    private int id;
    private String fullName;
    private String position;
    private String phone;
    private String dect;
    private String mobile;
    private String email;
    private int departmentId;

    public Person() {}

    public Person(int id, String fullName, String position,	String phone,
                  String dect, String mobile, String email, int departmentId)
    {
        this.id = id;
        this.fullName = fullName;
        this.position = position;
        this.phone = phone;
        this.dect = dect;
        this.mobile = mobile;
        this.email = email;
        this.departmentId = departmentId;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDect(String dect) {
        this.dect = dect;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPosition() {
        return position;
    }

    public String getPhone() {
        return phone;
    }

    public String getDect() {
        return dect;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public int getDepartmentId() {
        return departmentId;
    }

}
