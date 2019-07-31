package com.example.contactswhatsapp;

import java.io.Serializable;
import java.util.List;

public class User {
    private String userName;
    private String telNumber;
    private int profileImage;
    private int id;
    private int dataId;
    private Data data;




    public User() {

    }
    public User(int id,String userName,String telNumber,int profileImage){
        this.id=id;
        this.userName=userName;
        this.telNumber=telNumber;
        this.profileImage=profileImage;


    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public int getDataId() {
        return dataId;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public int getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(int profileImage) {
        this.profileImage = profileImage;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", telNumber='" + telNumber + '\'' +
                ", profileImage=" + profileImage +
                ", id=" + id +
                '}';
    }
}
