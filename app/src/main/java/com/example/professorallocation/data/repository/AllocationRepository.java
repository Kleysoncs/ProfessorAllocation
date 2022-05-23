package com.example.professorallocation.data.repository;

import com.example.professorallocation.data.service.AllocationService;
import com.example.professorallocation.data.service.RepositoryCallBack;
import com.example.professorallocation.model.Allocation;
import com.example.professorallocation.model.AllocationRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllocationRepository {

    private final AllocationService service;

    public AllocationRepository() {
        service = RetrofitClient.getAllocationService();
    }

    public void getListOfAllocations(RepositoryCallBack<List<Allocation>> repositoryCallBack) {
        Call<List<Allocation>> call = service.getAllocations();
        call.enqueue(new Callback<List<Allocation>>() {
            @Override
            public void onResponse(Call<List<Allocation>> call, Response<List<Allocation>> response) {
                List<Allocation> list = response.body();
                repositoryCallBack.onResponse(list);
            }

            @Override
            public void onFailure(Call<List<Allocation>> call, Throwable t) {
                repositoryCallBack.onFailure(t);
            }
        });
    }

    public void addAllocation(AllocationRequest allocationRequest, RepositoryCallBack<Allocation> repositoryCallBack) {
        Call<Allocation> call = service.createAllocation(allocationRequest);
        call.enqueue(new Callback<Allocation>() {
            @Override
            public void onResponse(Call<Allocation> call, Response<Allocation> response) {
                repositoryCallBack.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<Allocation> call, Throwable t) {
                repositoryCallBack.onFailure(t);
            }
        });
    }

    public void removeAllocation(int allocationId, RepositoryCallBack<Void> repositoryCallBack) {
        Call<Void> call = service.deleteAllocation(allocationId);
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

    public void changeAllocation(int allocationId, AllocationRequest allocationRequest, RepositoryCallBack<Allocation> repositoryCallBack) {
        Call<Allocation> call = service.updateAllocation(allocationId, allocationRequest);
        call.enqueue(new Callback<Allocation>() {
            @Override
            public void onResponse(Call<Allocation> call, Response<Allocation> response) {
                repositoryCallBack.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<Allocation> call, Throwable t) {
                repositoryCallBack.onFailure(t);
            }
        });
    }
}
