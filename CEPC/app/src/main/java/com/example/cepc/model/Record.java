package com.example.cepc.model;

public class Record {

    private int record_id;
    private String user_name;
    private double temperature;
    private String patient;
    private String date;
    private String address;

    public Record(int record_id,String user_name, double temperature, String patient, String date,String address) {
        this.record_id = record_id;
        this.user_name = user_name;
        this.temperature = temperature;
        this.patient = patient;
        this.date = date;
        this.address= address;
    }

    public int getRecord_id(){ return record_id;}

    public void setRecord_id(int record_id) {
        this.record_id = record_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String User_name) {
        this.user_name = User_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + record_id +
                ", name='" + user_name + '\'' +
                ", temperature=" + temperature +
                ", patient='" + patient + '\'' +
                ", date='" + date +
                ", address='" + date +
                '}';
    }
}
