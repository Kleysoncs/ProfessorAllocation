package com.example.professorallocation.data.service;

import com.example.professorallocation.model.Department;
import com.example.professorallocation.model.DepartmentRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DepartmentService {

    @GET("/departments")
    Call<List<Department>> getDepartments();

    @DELETE("/departments/{id}")
    Call<Void> deleteDepartment(@Path("id") int departmentId);

    @POST("/departments")
    Call<Department> createDepartment(@Body DepartmentRequest departmentRequest);

    @PUT("/departments/{id}")
    Call<Department> updateDepartment(@Path("id") int departmentId, @Body DepartmentRequest departmentRequest);
}
