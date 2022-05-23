package com.example.professorallocation.data.service;

import com.example.professorallocation.model.Course;
import com.example.professorallocation.model.CourseRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CourseService {

    @GET("/courses")
    Call<List<Course>> getCourses();

    @DELETE("/courses/{id}")
    Call<Void> deleteCourse(@Path("id") int courseId);

    @POST("/courses")
    Call<Course> createCourse(@Body CourseRequest courseRequest);

    @PUT("/courses/{id}")
    Call<Course> updateCourse(@Path("id") int courseId, @Body CourseRequest courseRequest);
}
