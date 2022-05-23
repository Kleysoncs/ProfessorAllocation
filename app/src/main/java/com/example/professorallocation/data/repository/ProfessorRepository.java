package com.example.professorallocation.data.repository;

import android.util.Log;

import com.example.professorallocation.data.service.ProfessorService;
import com.example.professorallocation.data.service.RepositoryCallBack;
import com.example.professorallocation.model.Professor;
import com.example.professorallocation.model.ProfessorRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfessorRepository {

    private final ProfessorService service;

    public ProfessorRepository() {
        service = RetrofitClient.getProfessorService();
    }

    public void getListOfProfessors(RepositoryCallBack<List<Professor>> repositoryCallBack) {
        Call<List<Professor>> call = service.getProfessors();
        call.enqueue(new Callback<List<Professor>>() {
            @Override
            public void onResponse(Call<List<Professor>> call, Response<List<Professor>> response) {
                List<Professor> list = response.body();
                repositoryCallBack.onResponse(list);
                Log.d("IPL1", "onResponse: sucesso" + list);
            }

            @Override
            public void onFailure(Call<List<Professor>> call, Throwable t) {
                repositoryCallBack.onFailure(t);
            }
        });
    }

    public  void addProfessor(ProfessorRequest professorRequest, RepositoryCallBack<Professor> repositoryCallBack) {
        Call<Professor> call = service.createProfessor(professorRequest);
        call.enqueue(new Callback<Professor>() {
            @Override
            public void onResponse(Call<Professor> call, Response<Professor> response) {
                repositoryCallBack.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<Professor> call, Throwable t) {
                repositoryCallBack.onFailure(t);
            }
        });
    }

    public void removeProfessor(int professorId, RepositoryCallBack<Void> repositoryCallBack) {
        Call<Void> call = service.deleteProfessor(professorId);
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

    public void changeProfessor(int professorId, ProfessorRequest professorRequest, RepositoryCallBack<Professor> repositoryCallBack) {
        Call<Professor> call = service.updateProfessor(professorId, professorRequest);
        call.enqueue(new Callback<Professor>() {
            @Override
            public void onResponse(Call<Professor> call, Response<Professor> response) {
                repositoryCallBack.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<Professor> call, Throwable t) {
                repositoryCallBack.onFailure(t);
            }
        });
    }
}
