package com.example.professorallocation.view.course;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.professorallocation.data.repository.CourseRepository;
import com.example.professorallocation.data.service.RepositoryCallBack;
import com.example.professorallocation.databinding.ActivityAddEditCourseBinding;
import com.example.professorallocation.model.Course;
import com.example.professorallocation.model.CourseRequest;

public class AddEditCourseActivity extends AppCompatActivity {

    private ActivityAddEditCourseBinding binding;
    private CourseRepository courseRepository;
    private Course editCourse = null;
    public static final String EXTRA_EDIT_COURSE = "edit_course";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEditCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        courseRepository = new CourseRepository();
        loadCourseToEdit();
        setupOnClickListener();
    }

    private void setupOnClickListener() {
        binding.buttonSaveCourse.setOnClickListener(view -> {
            CourseRequest courseRequest = createCourseToBackend();
            if (editCourse != null) {
                updateCourse(editCourse.getId(), courseRequest);
            }
            else {
                createCourse(courseRequest);
            }
        });
    }

    private void updateCourse(int id, CourseRequest courseRequest) {
        courseRepository.changeCourse(id, courseRequest, new RepositoryCallBack<Course>() {
            @Override
            public void onResponse(Course response) {
                Toast.makeText(AddEditCourseActivity.this, "Course updated", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("IPL1", "onFailure: failed to update course: " + t);
            }
        });
    }

    private void createCourse(CourseRequest courseRequest) {
        courseRepository.addCourse(courseRequest, new RepositoryCallBack<Course>() {
            @Override
            public void onResponse(Course response) {
                Toast.makeText(AddEditCourseActivity.this, "Course created", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("IPL1", "onFailure: failed to create course: " + t);
            }
        });
    }

    private CourseRequest createCourseToBackend() {
        String courseName = binding.courseName.getText().toString();
        CourseRequest courseRequest = new CourseRequest(courseName);
        return courseRequest;
    }

    private void loadCourseToEdit() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            Course course = (Course) bundle.getSerializable(EXTRA_EDIT_COURSE);
            if (course != null) {
                editCourse = course;
                binding.courseName.setText(course.getName());
            }
        }
    }
}