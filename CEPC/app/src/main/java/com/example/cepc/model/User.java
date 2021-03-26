package com.example.cepc.model;

public class User {
    private int user_id;
    private String username;
    private String password;
    private int daymarks;

    public User(int user_id,String username, String password, int daymarks) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.daymarks = daymarks;
    }


    public int getId() {
        return user_id;
    }

    public void setId(int id) {
        this.user_id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String User_name) {
        this.username = User_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String aPassword) {
        this.password = aPassword;
    }

    public int getDaymarks() {
        return daymarks;
    }

    public void setDaymarks(int daymarks) {
        this.daymarks = daymarks;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + user_id +
                ", name='" + username + '\'' +
                ", daymarks='" + daymarks +
                '}';
    }
}

