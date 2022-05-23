package com.example.professorallocation.model;

import com.google.gson.annotations.SerializedName;

public class AllocationRequest {

    @SerializedName("professorId")
    private int professorId;

    @SerializedName("courseId")
    private int courseId;

    @SerializedName("day")
    private String day;

    @SerializedName("start")
    private String start;

    @SerializedName("end")
    private String end;

    public AllocationRequest(int professorId, int courseId, String day, String start, String end) {
        this.professorId = professorId;
        this.courseId = courseId;
        this.day = day;
        this.start = start;
        this.end = end;
    }

    public int getProfessorId() {
        return professorId;
    }

    public void setProfessorId(int professorId) {
        this.professorId = professorId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "AllocationRequest{" +
                "professorId=" + professorId +
                ", courseId=" + courseId +
                ", day='" + day + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                '}';
    }
}
