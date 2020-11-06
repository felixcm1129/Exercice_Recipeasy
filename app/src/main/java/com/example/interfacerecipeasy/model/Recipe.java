package com.example.interfacerecipeasy.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Recipe implements Parcelable {
    private int id;
    private String name;
    private String category;
    private int durationHours;
    private int durationMinutes;
    private String description;

    public Recipe(JSONObject recipesJson) throws JSONException {
        this.id = recipesJson.getInt("id");
        this.name = recipesJson.getString("name");
        this.category = recipesJson.getString("category");
        this.durationHours = recipesJson.getInt("duration_hours");
        this.durationMinutes = recipesJson.getInt("duration_minutes");
        this.description = recipesJson.getString("description");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getDurationHours() {
        return durationHours;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public String getDescription() {
        return description;
    }

    public Recipe(int id, String category, String name, int durationHours, int durationMinutes, String description) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.durationHours = durationHours;
        this.durationMinutes = durationMinutes;
        this.description = description;
    }

    public void update(String category, String name, int hours, int minutes, String description) {
        this.category = category;
        this.name = name;
        this.durationHours = hours;
        this.durationMinutes = minutes;
        this.description = description;
    }

    protected Recipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        category = in.readString();
        durationHours = in.readInt();
        durationMinutes = in.readInt();
        description = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(category);
        parcel.writeInt(durationHours);
        parcel.writeInt(durationMinutes);
        parcel.writeString(description);
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", durationHours=" + durationHours +
                ", durationMinutes=" + durationMinutes +
                ", description='" + description + '\'' +
                '}';
    }
}
