package com.example.professorallocation.view.course;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.professorallocation.databinding.ItemCourseBinding;
import com.example.professorallocation.model.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseViewHolder> {

    private List<Course> courses;
    private RecyclerViewCallBack recyclerViewCallBack;

    public CourseListAdapter(RecyclerViewCallBack recyclerViewCallBack) {
        courses = new ArrayList<>();
        this.recyclerViewCallBack = recyclerViewCallBack;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCourseBinding binding = ItemCourseBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CourseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = courses.get(position);
        holder.binding.nameCourse.setText(course.getName());
        holder.binding.buttonDeleteCourse.setOnClickListener(view -> {
            recyclerViewCallBack.onClickDeleteCourse(course);
        });
        holder.binding.buttonEditCourse.setOnClickListener(view -> {
            recyclerViewCallBack.onClickEditCourse(course);
        });
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

//    public void addCourse(Course course) {
//        courses.add(course);
//        notifyDataSetChanged();
//    }

    public void removeCourse(Course course) {
        courses.remove(course);
        notifyDataSetChanged();
    }

    public void addCoursesList(List<Course> coursesList) {
        courses.clear();
        courses.addAll(coursesList);
        notifyDataSetChanged();
    }

    public static  class CourseViewHolder extends RecyclerView.ViewHolder {
        private final ItemCourseBinding binding;

        public CourseViewHolder(@NonNull ItemCourseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public interface RecyclerViewCallBack {
        void onClickDeleteCourse(Course deleteCourse);
        void onClickEditCourse(Course editCourse);
    }
}
