package com.example.contactswhatsapp.model;

public class Data {

    private int id;
    private String data;
    private int data_type;
    private int senderId;
    private String sendDataTime;
    private int reciverId;

    public Data() {

    }

    public Data(int id, String data, int data_type, int senderId, String sendDataTime, int reciverId) {
        this.id = id;
        this.data = data;
        this.data_type = data_type;
        this.senderId = senderId;
        this.sendDataTime = sendDataTime;
        this.reciverId = reciverId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getData_type() {
        return data_type;
    }

    public void setData_type(int data_type) {
        this.data_type = data_type;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getSendDataTime() {
        return sendDataTime;
    }

    public void setSendDataTime(String sendDataTime) {
        this.sendDataTime = sendDataTime;
    }

    public int getReciverId() {
        return reciverId;
    }

    public void setReciverId(int reciverId) {
        this.reciverId = reciverId;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", data='" + data + '\'' +
                ", data_type=" + data_type +
                ", senderId=" + senderId +
                ", sendDataTime='" + sendDataTime + '\'' +
                ", reciverId=" + reciverId +
                '}';
    }
}
