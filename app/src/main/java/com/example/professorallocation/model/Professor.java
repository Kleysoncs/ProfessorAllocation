package com.example.professorallocation.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Professor implements Serializable {

	@SerializedName("name")
	private String name;

	@SerializedName("cpf")
	private String cpf;

	@SerializedName("id")
	private int id;

	@SerializedName("depart")
	private Department depart;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setCpf(String cpf){
		this.cpf = cpf;
	}

	public String getCpf(){
		return cpf;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setDepart(Department depart){
		this.depart = depart;
	}

	public Department getDepart(){
		return depart;
	}

	@Override
	public String toString() {
		return "Prof. " + name + " / " + depart;
	}
}