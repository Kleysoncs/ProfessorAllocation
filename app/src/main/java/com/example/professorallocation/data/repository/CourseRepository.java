package com.example.professorallocation.data.repository;

import android.util.Log;

import com.example.professorallocation.data.service.CourseService;
import com.example.professorallocation.data.service.RepositoryCallBack;
import com.example.professorallocation.model.Course;
import com.example.professorallocation.model.CourseRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseRepository {

    private final CourseService service;

    public CourseRepository() {
        service = RetrofitClient.getCourseService();
    }

    public void getListOfCourses(RepositoryCallBack<List<Course>> repositoryCallBack) {
        Call<List<Course>> call = service.getCourses();
        call.enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                List<Course> list = response.body();
                repositoryCallBack.onResponse(list);
                Log.d("IPL1", "onResponse: Sucesso! " + response.body());
            }

            @Override
            public void onFailure(Call<List<Course>> call, Throwable t) {
                repositoryCallBack.onFailure(t);
                Log.d("IPL1", "onFailure: Erro! " + t);
            }
        });
    }

    public void addCourse(CourseRequest courseRequest, RepositoryCallBack<Course> repositoryCallBack) {
        Call<Course> call = service.createCourse(courseRequest);
        call.enqueue(new Callback<Course>() {
            @Override
            public void onResponse(Call<Course> call, Response<Course> response) {
                repositoryCallBack.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<Course> call, Throwable t) {
                repositoryCallBack.onFailure(t);
            }
        });
    }

    public void removeCourse(int courseId, RepositoryCallBack<Void> repositoryCallBack) {
        Call<Void> call = service.deleteCourse(courseId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                repositoryCallBack.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                repositoryCallBack.onFailure(t);
            }
        });
    }

    public void changeCourse(int courseId, CourseRequest courseRequest, RepositoryCallBack<Course> repositoryCallBack) {
        Call<Course> call = service.updateCourse(courseId, courseRequest);
        call.enqueue(new Callback<Course>() {
            @Override
            public void onResponse(Call<Course> call, Response<Course> response) {
                repositoryCallBack.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<Course> call, Throwable t) {
                repositoryCallBack.onFailure(t);
            }
        });
    }
}
