package com.example.professorallocation.model;

import com.google.gson.annotations.SerializedName;

public class ProfessorRequest {

    @SerializedName("name")
    private String name;

    @SerializedName("cpf")
    private String cpf;

    @SerializedName("departmentId")
    private int departmentId;

    public ProfessorRequest(String name, String cpf, int departmentId) {
        this.name = name;
        this.cpf = cpf;
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        return "ProfessorRequest{" +
                "name='" + name + '\'' +
                ", cpf='" + cpf + '\'' +
                ", departmentId=" + departmentId +
                '}';
    }
}

