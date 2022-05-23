package com.example.professorallocation.view.department;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.professorallocation.data.repository.DepartmentRepository;
import com.example.professorallocation.data.service.RepositoryCallBack;
import com.example.professorallocation.databinding.ActivityAddEditDepartmentBinding;
import com.example.professorallocation.model.Department;
import com.example.professorallocation.model.DepartmentRequest;

public class AddEditDepartmentActivity extends AppCompatActivity {

    private ActivityAddEditDepartmentBinding binding;
    private DepartmentRepository departmentRepository;
    private Department editDepartment = null;
    public static final String EXTRA_EDIT_DEPARTMENT = "edit_department";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEditDepartmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        departmentRepository = new DepartmentRepository();
        loadDepartmentToEdit();
        setupOnClickListener();
    }

    private void setupOnClickListener() {
        binding.buttonSaveDepartment.setOnClickListener(view -> {
            DepartmentRequest departmentRequest = createDepartmentToBackend();
            if (editDepartment != null) {
                updateDepartment(editDepartment.getId(), departmentRequest);
            }
            else {
                createDepartment(departmentRequest);
            }
        });
    }

    private void updateDepartment(int id, DepartmentRequest departmentRequest) {
        departmentRepository.changeDepartment(id, departmentRequest, new RepositoryCallBack<Department>() {
            @Override
            public void onResponse(Department response) {
                Toast.makeText(AddEditDepartmentActivity.this, "Department updated", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("IPL1", "onFailure: failed to update department: " + t);
            }
        });
    }

    private void createDepartment(DepartmentRequest departmentRequest) {
        departmentRepository.addDepartment(departmentRequest, new RepositoryCallBack<Department>() {
            @Override
            public void onResponse(Department response) {
                Toast.makeText(AddEditDepartmentActivity.this, "Department created", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("IPL1", "onFailure: failed to create department: " + t);
            }
        });
    }

    private DepartmentRequest createDepartmentToBackend() {
        String departmentName = binding.departmentName.getText().toString();
        DepartmentRequest departmentRequest = new DepartmentRequest(departmentName);
        return departmentRequest;
    }

    private void loadDepartmentToEdit() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            Department department = (Department) bundle.getSerializable(EXTRA_EDIT_DEPARTMENT);
            if (department != null) {
                editDepartment = department;
                binding.departmentName.setText(department.getName());
            }
        }
    }


}