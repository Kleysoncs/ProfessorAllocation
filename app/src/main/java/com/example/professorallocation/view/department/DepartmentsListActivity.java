package com.example.professorallocation.view.department;

import static com.example.professorallocation.view.department.AddEditDepartmentActivity.EXTRA_EDIT_DEPARTMENT;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.professorallocation.data.repository.DepartmentRepository;
import com.example.professorallocation.data.service.RepositoryCallBack;
import com.example.professorallocation.databinding.ActivityDepartmentsListBinding;
import com.example.professorallocation.model.Department;

import java.util.List;

public class DepartmentsListActivity extends AppCompatActivity {

    private ActivityDepartmentsListBinding binding;
    private DepartmentRepository departmentRepository;
    private DepartmentListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDepartmentsListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        departmentRepository = new DepartmentRepository();
        setupRecyclerView();
        setupOnClickListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showAllDepartments();
    }

    private void setupOnClickListener() {
        binding.buttonRefreshDepartmentList.setOnClickListener(view -> {
            showAllDepartments();
        });
        binding.buttonAddDepartment.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddEditDepartmentActivity.class);
            startActivity(intent);
        });
    }

    private void setupRecyclerView() {
        adapter = new DepartmentListAdapter(new DepartmentListAdapter.RecyclerViewCallBack() {
            @Override
            public void onClickDeleteDepartment(Department removeDepartment) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DepartmentsListActivity.this);
                builder.setTitle("Atention!");
                builder.setMessage("Are you sure you want to delete this? ");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteDepartment(removeDepartment);
                    }
                });

                builder.setNegativeButton("No", null);
                builder.create().show();
            }

            @Override
            public void onClickEditDepartment(Department editDepartment) {
                Intent intent = new Intent(DepartmentsListActivity.this, AddEditDepartmentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(EXTRA_EDIT_DEPARTMENT, editDepartment);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        binding.listDepartments.setAdapter(adapter);
        binding.listDepartments.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    private void showAllDepartments() {
        showProgressBar(View.VISIBLE);
        departmentRepository.getListOfDepartments(new RepositoryCallBack<List<Department>>() {
            @Override
            public void onResponse(List<Department> response) {
                adapter.addDepartmentsList(response);
                showProgressBar(View.GONE);
            }

            @Override
            public void onFailure(Throwable t) {
                showProgressBar(View.GONE);
            }
        });
    }

    private void deleteDepartment(Department department) {
        showProgressBar(View.VISIBLE);
        departmentRepository.removeDepartment(department.getId(), new RepositoryCallBack<Void>() {
            @Override
            public void onResponse(Void response) {
                adapter.removeDepartment(department);
                showProgressBar(View.GONE);
            }

            @Override
            public void onFailure(Throwable t) {
                showProgressBar(View.GONE);
            }
        });
    }

    private void showProgressBar(int showProgressBar) {
        binding.progressBarDepartmentList.setVisibility(showProgressBar);
    }

}