package com.example.contactswhatsapp.model;

public class User {
    private String userName;
    private String telNumber;
    private int profileImage;
    private int id;




    public User() {

    }
    public User(int id,String userName,String telNumber,int profileImage){
        this.id=id;
        this.userName=userName;
        this.telNumber=telNumber;
        this.profileImage=profileImage;


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
