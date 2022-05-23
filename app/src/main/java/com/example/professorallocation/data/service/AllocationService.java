package com.example.professorallocation.data.service;

import com.example.professorallocation.model.Allocation;
import com.example.professorallocation.model.AllocationRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AllocationService {

    @GET("/allocations")
    Call<List<Allocation>> getAllocations();

    @DELETE("/allocations/{id}")
    Call<Void> deleteAllocation(@Path("id") int allocationId);

    @POST("/allocations")
    Call<Allocation> createAllocation(@Body AllocationRequest allocationRequest);

    @PUT("/allocations/{id}")
    Call<Allocation> updateAllocation(@Path("id") int allocationId, @Body AllocationRequest allocationRequest);
}
