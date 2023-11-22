package com.example.calmo;

public class StudentModel {
    public int id;
    public int type;
    public String firstName,lastName,contact;
//    public String lastName;

    public StudentModel(int id, String firstName, String lastName,int type,String contact) {
        this.id = id;
        this.type =type;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contact = contact;
    }


    public String getContact() {
        return contact;
    }

    public int getsId() {

        return id;
    }


    public int getType() {

        return type;
    }

    public String getFirstName() {

        return firstName;
    }

    public String getLastName() {

        return lastName;
    }
}
