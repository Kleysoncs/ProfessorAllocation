package com.example.professorallocation.model;

import com.google.gson.annotations.SerializedName;

public class DepartmentRequest {

    @SerializedName("name")
    private String name;

    public DepartmentRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DepartmentRequest{" +
                "name='" + name + '\'' +
                '}';
    }
}
