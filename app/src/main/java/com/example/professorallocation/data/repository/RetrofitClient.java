package com.example.professorallocation.data.repository;

import com.example.professorallocation.data.service.AllocationService;
import com.example.professorallocation.data.service.CourseService;
import com.example.professorallocation.data.service.DepartmentService;
import com.example.professorallocation.data.service.ProfessorService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;
    private static final String URL_BASE = "http://192.168.0.108:8080";

    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ProfessorService getProfessorService() {
        if (retrofit == null) {
            getInstance();
        }
        return retrofit.create(ProfessorService.class);
    }

    public static CourseService getCourseService() {
        if (retrofit == null) {
            getInstance();
        }
        return retrofit.create(CourseService.class);
    }

    public static DepartmentService getDepartmentService() {
        if (retrofit == null) {
            getInstance();
        }
        return retrofit.create(DepartmentService.class);
    }

    public static AllocationService getAllocationService() {
        if (retrofit == null) {
            getInstance();
        }
        return retrofit.create(AllocationService.class);
    }
}
