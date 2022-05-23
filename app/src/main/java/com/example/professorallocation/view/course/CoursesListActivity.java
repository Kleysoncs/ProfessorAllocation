package com.example.professorallocation.view.course;

import static com.example.professorallocation.view.course.AddEditCourseActivity.EXTRA_EDIT_COURSE;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.professorallocation.data.repository.CourseRepository;
import com.example.professorallocation.data.service.RepositoryCallBack;
import com.example.professorallocation.databinding.ActivityCoursesListBinding;
import com.example.professorallocation.model.Course;

import java.util.List;

public class CoursesListActivity extends AppCompatActivity {

    private ActivityCoursesListBinding binding;
    private CourseRepository courseRepository;
    private CourseListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCoursesListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        courseRepository = new CourseRepository();
        setupRecyclerView();
        setupOnClickListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showAllCourses();
    }
    private void setupOnClickListener() {
        binding.buttonRefreshCourseList.setOnClickListener(view -> {
            showAllCourses();
        });
        binding.buttonAddCourse.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddEditCourseActivity.class);
            startActivity(intent);
        });
    }

    private void setupRecyclerView() {
        adapter = new CourseListAdapter(new CourseListAdapter.RecyclerViewCallBack() {
            @Override
            public void onClickDeleteCourse(Course removeCourse) {

                AlertDialog.Builder builder = new AlertDialog.Builder(CoursesListActivity.this);
                builder.setTitle("Atention!");
                builder.setMessage("Are you sure you want to delete this? ");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteCourse(removeCourse);
                    }
                });

                builder.setNegativeButton("No", null);
                builder.create().show();

            }

            @Override
            public void onClickEditCourse(Course editCourse) {
                Intent intent = new Intent(CoursesListActivity.this, AddEditCourseActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(EXTRA_EDIT_COURSE, editCourse);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        binding.listCourses.setAdapter(adapter);
        binding.listCourses.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    private void showAllCourses() {
        showProgressBar(View.VISIBLE);
        courseRepository.getListOfCourses(new RepositoryCallBack<List<Course>>() {
            @Override
            public void onResponse(List<Course> response) {
                adapter.addCoursesList(response);
                showProgressBar(View.GONE);
            }

            @Override
            public void onFailure(Throwable t) {
                showProgressBar(View.GONE);
            }
        });
    }

    private void deleteCourse(Course course) {
        showProgressBar(View.VISIBLE);
        courseRepository.removeCourse(course.getId(), new RepositoryCallBack<Void>() {
            @Override
            public void onResponse(Void response) {
                adapter.removeCourse(course);
                showProgressBar(View.GONE);
            }

            @Override
            public void onFailure(Throwable t) {
                showProgressBar(View.GONE);
            }
        });
    }

    private void showProgressBar(int showProgressBar) {
        binding.progressBarCoursesList.setVisibility(showProgressBar);
    }

}
