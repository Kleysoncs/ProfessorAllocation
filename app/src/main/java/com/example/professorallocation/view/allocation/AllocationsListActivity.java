package com.example.professorallocation.view.allocation;

import static com.example.professorallocation.view.allocation.AddEditAllocationActivity.EXTRA_EDIT_ALLOCATION;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.professorallocation.data.repository.AllocationRepository;
import com.example.professorallocation.data.service.RepositoryCallBack;
import com.example.professorallocation.databinding.ActivityAllocationsListBinding;
import com.example.professorallocation.model.Allocation;

import java.util.List;

public class AllocationsListActivity extends AppCompatActivity {

    private ActivityAllocationsListBinding binding;
    private AllocationRepository allocationRepository;
    private AllocationListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllocationsListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        allocationRepository = new AllocationRepository();
        setupRecyclerView();
        setupOnClickListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showAllAllocations();
    }

    private void setupOnClickListener() {
        binding.buttonRefreshAllocationList.setOnClickListener(view -> {
            showAllAllocations();
        });
        binding.buttonAddAllocation.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddEditAllocationActivity.class);
            startActivity(intent);
        });
    }

    private void setupRecyclerView() {
        adapter = new AllocationListAdapter(new AllocationListAdapter.RecyclerViewCallBack() {
            @Override
            public void onClickDeleteAllocation(Allocation removeAllocation) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AllocationsListActivity.this);
                builder.setTitle("Atention!");
                builder.setMessage("Are you sure you want to delete this? ");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteAllocation(removeAllocation);
                    }
                });

                builder.setNegativeButton("No", null);
                builder.create().show();

            }

            @Override
            public void onClickEditAllocation(Allocation editAllocation) {
                Intent intent = new Intent(AllocationsListActivity.this, AddEditAllocationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(EXTRA_EDIT_ALLOCATION, editAllocation);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        binding.listAllocations.setAdapter(adapter);
        binding.listAllocations.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    private void showAllAllocations() {
        showProgressBar(View.VISIBLE);
        allocationRepository.getListOfAllocations(new RepositoryCallBack<List<Allocation>>() {
            @Override
            public void onResponse(List<Allocation> response) {
                adapter.addAllocationsList(response);
                showProgressBar(View.GONE);
            }

            @Override
            public void onFailure(Throwable t) {
                showProgressBar(View.GONE);
            }
        });
    }

    private void deleteAllocation(Allocation allocation) {
        showProgressBar(View.VISIBLE);
        allocationRepository.removeAllocation(allocation.getId(), new RepositoryCallBack<Void>() {
            @Override
            public void onResponse(Void response) {
                adapter.removeAllocation(allocation);
                showProgressBar(View.GONE);
            }

            @Override
            public void onFailure(Throwable t) {
                showProgressBar(View.GONE);
            }
        });
    }

    private void showProgressBar(int showProgressBar) {
        binding.progressBarAllocationList.setVisibility(showProgressBar);
    }

}