package com.example.professorallocation.view.allocation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.professorallocation.R;
import com.example.professorallocation.data.repository.AllocationRepository;
import com.example.professorallocation.data.repository.CourseRepository;
import com.example.professorallocation.data.repository.ProfessorRepository;
import com.example.professorallocation.data.service.RepositoryCallBack;
import com.example.professorallocation.databinding.ActivityAddEditAllocationBinding;
import com.example.professorallocation.model.Allocation;
import com.example.professorallocation.model.AllocationRequest;
import com.example.professorallocation.model.Course;
import com.example.professorallocation.model.Professor;

import java.util.List;

public class AddEditAllocationActivity extends AppCompatActivity {

    private ActivityAddEditAllocationBinding binding;
    private ArrayAdapter<Professor> professorSpinner;
    private ArrayAdapter<Course> courseSpinner;
    private ArrayAdapter<String> daysOfWeekSpinner;
    private ProfessorRepository professorRepository;
    private CourseRepository courseRepository;
    private AllocationRepository allocationRepository;
    public static final String EXTRA_EDIT_ALLOCATION = "edit_allocation";
    private Allocation editAllocation = null;
    private String[] daysOfWeek = {"MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY","SUNDAY"};
    private String selectedDay = null;
    private Professor selectedProfessor = null;
    private Course selectedCourse = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEditAllocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        professorRepository = new ProfessorRepository();
        courseRepository = new CourseRepository();
        allocationRepository = new AllocationRepository();
        setupSpinnerCourseList();
        setupSpinnerProfessorList();
        setupSpinnerDaysOfWeek();
        courseList();
        professorList();
        daysOfWeekList();
        loadAllocationToEdit();
        setupOnClickListener();

    }

    private void setupOnClickListener() {
        binding.buttonSaveAllocation.setOnClickListener(view -> {
            AllocationRequest allocationRequest = createAllocationToBackend();
            if (editAllocation != null) {
                updateAllocation(editAllocation.getId(), allocationRequest);
            }
            else {
                createAllocation(allocationRequest);
            }
        });
    }

    private void updateAllocation(int id, AllocationRequest allocationRequest) {
        allocationRepository.changeAllocation(id, allocationRequest, new RepositoryCallBack<Allocation>() {
            @Override
            public void onResponse(Allocation response) {
                Toast.makeText(AddEditAllocationActivity.this, "Allocation updated", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("IPL1", "onFailure: failed to update allocation: " + t);
            }
        });
    }

    private void createAllocation(AllocationRequest allocationRequest) {
        allocationRepository.addAllocation(allocationRequest, new RepositoryCallBack<Allocation>() {
            @Override
            public void onResponse(Allocation response) {
                Toast.makeText(AddEditAllocationActivity.this, "Allocation created", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("IPL1", "onFailure: failed to create allocation: " + t);
            }
        });
    }

    private AllocationRequest createAllocationToBackend() {

        String start = binding.startHourAllocation.getText().toString() + "-0000";
        String end = binding.endHourAllocation.getText().toString() + "-0000";

        return new AllocationRequest(selectedProfessor.getId(), selectedCourse.getId(), selectedDay, start, end);
    }

    private void loadAllocationToEdit() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            Allocation allocation = (Allocation) bundle.getSerializable(EXTRA_EDIT_ALLOCATION);
            if (allocation != null) {
                editAllocation = allocation;
                binding.startHourAllocation.setText(allocation.getStart().substring(0, 5));
                binding.endHourAllocation.setText(allocation.getEnd().substring(0, 5));
            }
        }
    }

    private void setupSpinnerProfessorList() {
        professorSpinner = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        binding.spinnerProfessor.setAdapter(professorSpinner);
        binding.spinnerProfessor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                selectedProfessor = professorSpinner.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(AddEditAllocationActivity.this, "onNothingSelected", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupSpinnerCourseList() {
        courseSpinner = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        binding.spinnerCourse.setAdapter(courseSpinner);
        binding.spinnerCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                selectedCourse = courseSpinner.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(AddEditAllocationActivity.this, "onNothingSelected", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupSpinnerDaysOfWeek() {
        daysOfWeekSpinner = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        binding.spinnerDay.setAdapter(daysOfWeekSpinner);
        binding.spinnerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                selectedDay = daysOfWeekSpinner.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(AddEditAllocationActivity.this, "onNothingSelected", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void professorList() {
        professorRepository.getListOfProfessors(new RepositoryCallBack<List<Professor>>() {
            @Override
            public void onResponse(List<Professor> response) {
                Professor[] array = new Professor[response.size()];
                Professor[] arrayProfessors = response.toArray(array);
                professorSpinner.addAll(arrayProfessors);

                if (editAllocation != null) {
                    selectedProfessor = editAllocation.getProfessor();
                    int professorPosition = professorSpinner.getPosition(editAllocation.getProfessor());
                    binding.spinnerProfessor.setSelection(professorPosition, true);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("IPL1", "onFailure: " + t);
            }
        });
    }

    private void courseList() {
        courseRepository.getListOfCourses(new RepositoryCallBack<List<Course>>() {
            @Override
            public void onResponse(List<Course> response) {
                Course[] array = new Course[response.size()];
                Course[] arrayCourses = response.toArray(array);
                courseSpinner.addAll(arrayCourses);

                if (editAllocation != null) {
                    selectedCourse = editAllocation.getCourse();
                    int coursePosition = courseSpinner.getPosition(editAllocation.getCourse());
                    binding.spinnerCourse.setSelection(coursePosition, true);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("IPL1", "onFailure: " + t);
            }
        });
    }

    private void daysOfWeekList() {
        daysOfWeekSpinner.addAll(daysOfWeek);

        if (editAllocation != null) {
            selectedDay = editAllocation.getDay();
            int daysOfWeekPosition = daysOfWeekSpinner.getPosition(editAllocation.getDay());
            binding.spinnerDay.setSelection(daysOfWeekPosition, true);
        }
    }
}