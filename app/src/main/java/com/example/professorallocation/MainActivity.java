package com.example.professorallocation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import com.example.professorallocation.databinding.ActivityMainBinding;
import com.example.professorallocation.view.allocation.AllocationsListActivity;
import com.example.professorallocation.view.course.CoursesListActivity;
import com.example.professorallocation.view.department.DepartmentsListActivity;
import com.example.professorallocation.view.professor.ProfessorsListActivity;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupOnCardClickListener();
    }

    private void setupOnCardClickListener() {
        binding.cardViewProfessors.setOnClickListener(view -> {
            Intent intent = new Intent(this, ProfessorsListActivity.class);
            startActivity(intent);
        });

        binding.cardViewCourses.setOnClickListener(view -> {
            Intent intent = new Intent(this, CoursesListActivity.class);
            startActivity(intent);
        });

        binding.cardViewDepartments.setOnClickListener(view -> {
            Intent intent = new Intent(this, DepartmentsListActivity.class);
            startActivity(intent);
        });

        binding.cardViewAllocations.setOnClickListener(view -> {
            Intent intent = new Intent(this, AllocationsListActivity.class);
            startActivity(intent);
        });
    }

}