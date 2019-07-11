package com.example.contactswhatsapp;

import java.io.Serializable;
import java.util.List;

public class User {
    private String userName;
    private String lastMesage;
    private String lastSpokeTime;
    private String telNumber;
    private int profileImage;
    private int callImage;
    private int id;
    private String message;
    private List<String> messageList;

    public User() {
    }

    public User(String message, int id) {
        this.message = message;
        this.id = id;
    }

    public User(int id, String userName, String message) {
        this.userName = userName;
        this.id = id;
        this.message = message;
    }

    //
    public User(int id, String userName) {
        this.id = id;
        this.userName = userName;

    }

    public User(String userName, String telNumber, int id) {
        this.userName = userName;
        this.telNumber = telNumber;
        this.id = id;
    }

    public User(int id, String userName, String lastMesage, int profileImage,String message) {
        this.id = id;
        this.userName = userName;
        this.lastMesage = lastMesage;
        this.message = message;
        this.profileImage = profileImage;
    }
//    public User(int id,String userName, String lastSpokeTime, int profileImage,int callImage) {
//        this.id = id;
//        this.userName = userName;
//        this.lastSpokeTime = lastSpokeTime;
//        this.profileImage = profileImage;
//        this.callImage=callImage;
//    }


    public List<String> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<String> messageList) {
        this.messageList = messageList;
    }

    public String getMessage() {
        return message;
    }

    public void setText(String message) {
        this.message = message;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastMesage() {
        return lastMesage;
    }

    public void setLastMesage(String lastMesage) {
        this.lastMesage = lastMesage;
    }

    public String getLastSpokeTime() {
        return lastSpokeTime;
    }

    public void setLastSpokeTime(String lastSpokeTime) {
        this.lastSpokeTime = lastSpokeTime;
    }

    public int getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(int profileImage) {
        this.profileImage = profileImage;
    }

    public int getCallImage() {
        return callImage;
    }

    public void setCallImage(int callImage) {
        this.callImage = callImage;
    }
}
