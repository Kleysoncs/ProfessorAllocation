package com.example.professorallocation.view.professor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.professorallocation.data.repository.DepartmentRepository;
import com.example.professorallocation.data.repository.ProfessorRepository;
import com.example.professorallocation.data.service.RepositoryCallBack;
import com.example.professorallocation.databinding.ActivityAddEditProfessorBinding;
import com.example.professorallocation.model.Department;
import com.example.professorallocation.model.Professor;
import com.example.professorallocation.model.ProfessorRequest;

import java.util.List;

public class AddEditProfessorActivity extends AppCompatActivity {

    private ActivityAddEditProfessorBinding binding;
    private ArrayAdapter<Department> departmentSpinner;
    private DepartmentRepository departmentRepository;
    private ProfessorRepository professorRepository;
    public static final String EXTRA_EDIT_PROFESSOR = "edit_professor";
    private Professor editProfessor = null;
    Department selectedDepartment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEditProfessorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        departmentRepository = new DepartmentRepository();
        professorRepository = new ProfessorRepository();
        setupSpinnerDepartmentList();
        departmentList();
        loadProfessorToEdit();
        setupOnClickListener();
    }

    private void setupOnClickListener() {
        binding.buttonSaveProfessor.setOnClickListener(view -> {
            ProfessorRequest professorRequest = createProfessorToBackend();
            if (editProfessor != null) {
                updateProfessor(editProfessor.getId(), professorRequest);
            }
            else {
                createProfessor(professorRequest);
            }
        });
    }

    private void updateProfessor(int id, ProfessorRequest professorRequest) {
        professorRepository.changeProfessor(id, professorRequest, new RepositoryCallBack<Professor>() {
            @Override
            public void onResponse(Professor response) {
                Toast.makeText(AddEditProfessorActivity.this, "Professor updated", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("IPL1", "onFailure: failed to update professor: " + t);
            }
        });
    }

    private void createProfessor(ProfessorRequest professorRequest) {
        professorRepository.addProfessor(professorRequest, new RepositoryCallBack<Professor>() {
            @Override
            public void onResponse(Professor response) {
                Toast.makeText(AddEditProfessorActivity.this, "Professor created", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("IPL1", "onFailure: failed to create professor: " + t);
            }
        });
    }

    @NonNull
    private ProfessorRequest createProfessorToBackend() {
        String professorCpf = binding.professorCpf.getText().toString();
        String professorName = binding.professorName.getText().toString();
        return new ProfessorRequest(professorName, professorCpf, selectedDepartment.getId());
    }

    private void loadProfessorToEdit() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            Professor professor = (Professor) bundle.getSerializable(EXTRA_EDIT_PROFESSOR);
            if (professor != null) {
                editProfessor = professor;
                binding.professorName.setText(professor.getName());
                binding.professorCpf.setText(professor.getCpf());

            }
        }
    }

    private void setupSpinnerDepartmentList() {
        departmentSpinner = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        binding.spinnerDepartment.setAdapter(departmentSpinner);
        binding.spinnerDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDepartment = departmentSpinner.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(AddEditProfessorActivity.this, "onNothingSelected", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void departmentList() {
        departmentRepository.getListOfDepartments(new RepositoryCallBack<List<Department>>() {
            @Override
            public void onResponse(List<Department> response) {
                Department[] array = new Department[response.size()];
                Department[] arrayDepartments = response.toArray(array);
                departmentSpinner.addAll(arrayDepartments);

                if (editProfessor != null) {
                    selectedDepartment = editProfessor.getDepart();
                    int departmentPosition = departmentSpinner.getPosition(editProfessor.getDepart());
                    binding.spinnerDepartment.setSelection(departmentPosition, true);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("IPL1", "onFailure: " + t);
            }
        });
    }
}