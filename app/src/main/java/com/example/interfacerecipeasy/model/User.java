package com.example.interfacerecipeasy.model;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private int id;
    private String username;

    public User(JSONObject userJson) throws JSONException {
        this.id = userJson.getInt("id");
        this.username = userJson.getString("username");
    }

    public int getId() {
        return id;
    }

    public User(int id, String username) {
        this.id = id;
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
