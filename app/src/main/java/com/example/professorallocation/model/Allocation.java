package com.example.professorallocation.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Allocation implements Serializable {

	@SerializedName("professor")
	private Professor professor;

	@SerializedName("start")
	private String start;

	@SerializedName("course")
	private Course course;

	@SerializedName("end")
	private String end;

	@SerializedName("id")
	private int id;

	@SerializedName("day")
	private String day;

	public void setProfessor(Professor professor){
		this.professor = professor;
	}

	public Professor getProfessor(){
		return professor;
	}

	public void setStart(String start){
		this.start = start;
	}

	public String getStart(){
		return start;
	}

	public void setCourse(Course course){
		this.course = course;
	}

	public Course getCourse(){
		return course;
	}

	public void setEnd(String end){
		this.end = end;
	}

	public String getEnd(){
		return end;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setDay(String day){
		this.day = day;
	}

	public String getDay(){
		return day;
	}

	@Override
 	public String toString(){
		return 
			"Allocation{" + 
			"professor = '" + professor + '\'' + 
			",start = '" + start + '\'' + 
			",course = '" + course + '\'' + 
			",end = '" + end + '\'' + 
			",id = '" + id + '\'' + 
			",day = '" + day + '\'' + 
			"}";
		}
}