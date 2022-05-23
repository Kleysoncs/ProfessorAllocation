package com.example.professorallocation.data.service;

import com.example.professorallocation.model.Professor;
import com.example.professorallocation.model.ProfessorRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProfessorService {

    @GET("/professors")
    Call<List<Professor>> getProfessors();

    @DELETE("/professors/{id}")
    Call<Void> deleteProfessor(@Path("id") int professorId);

    @POST("/professors")
    Call<Professor> createProfessor(@Body ProfessorRequest professorRequest);

    @PUT("/professors/{id}")
    Call<Professor> updateProfessor(@Path("id") int professorId, @Body ProfessorRequest professorRequest);
}
