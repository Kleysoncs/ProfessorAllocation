package com.example.professorallocation.data.repository;

import com.example.professorallocation.data.service.DepartmentService;
import com.example.professorallocation.data.service.RepositoryCallBack;
import com.example.professorallocation.model.Department;
import com.example.professorallocation.model.DepartmentRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DepartmentRepository {

    private final DepartmentService service;

    public DepartmentRepository() {
        service = RetrofitClient.getDepartmentService();
    }

    public void getListOfDepartments(RepositoryCallBack<List<Department>> repositoryCallBack) {
        Call<List<Department>> call = service.getDepartments();
        call.enqueue(new Callback<List<Department>>() {
            @Override
            public void onResponse(Call<List<Department>> call, Response<List<Department>> response) {
                List<Department> list = response.body();
                repositoryCallBack.onResponse(list);
            }

            @Override
            public void onFailure(Call<List<Department>> call, Throwable t) {
                repositoryCallBack.onFailure(t);
            }
        });
    }

    public void addDepartment(DepartmentRequest departmentRequest, RepositoryCallBack<Department> repositoryCallBack) {
        Call<Department> call = service.createDepartment(departmentRequest);
        call.enqueue(new Callback<Department>() {
            @Override
            public void onResponse(Call<Department> call, Response<Department> response) {
                repositoryCallBack.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<Department> call, Throwable t) {
                repositoryCallBack.onFailure(t);
            }
        });
    }

    public void removeDepartment(int departmentId, RepositoryCallBack<Void> repositoryCallBack) {
        Call<Void> call = service.deleteDepartment(departmentId);
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

    public void changeDepartment(int departmentId, DepartmentRequest departmentRequest, RepositoryCallBack<Department> repositoryCallBack) {
        Call<Department> call = service.updateDepartment(departmentId, departmentRequest);
        call.enqueue(new Callback<Department>() {
            @Override
            public void onResponse(Call<Department> call, Response<Department> response) {
                repositoryCallBack.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<Department> call, Throwable t) {
                repositoryCallBack.onFailure(t);
            }
        });
    }
}
