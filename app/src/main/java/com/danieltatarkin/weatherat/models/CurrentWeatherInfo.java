package com.danieltatarkin.weatherat.models;

import com.google.gson.annotations.SerializedName;

public class CurrentWeatherInfo {
    private long id;
    @SerializedName("main") private String condition;
    private String description;
    @SerializedName("icon") private String iconId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }
}
