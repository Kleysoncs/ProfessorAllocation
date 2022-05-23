package com.example.professorallocation.view.department;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.professorallocation.databinding.ItemDepartmentBinding;
import com.example.professorallocation.model.Department;

import java.util.ArrayList;
import java.util.List;

public class DepartmentListAdapter extends RecyclerView.Adapter<DepartmentListAdapter.DepartmentViewHolder> {

    private List<Department> departments;
    private RecyclerViewCallBack recyclerViewCallBack;

    public DepartmentListAdapter(RecyclerViewCallBack recyclerViewCallBack) {
        departments = new ArrayList<>();
        this.recyclerViewCallBack = recyclerViewCallBack;
    }

    @NonNull
    @Override
    public DepartmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDepartmentBinding binding = ItemDepartmentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DepartmentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DepartmentViewHolder holder, int position) {
        Department department = departments.get(position);
        holder.binding.nameDepartment.setText(department.getName());
        holder.binding.buttonDeleteDepartment.setOnClickListener(view -> {
            recyclerViewCallBack.onClickDeleteDepartment(department);
        });
        holder.binding.buttonEditDepartment.setOnClickListener(view -> {
            recyclerViewCallBack.onClickEditDepartment(department);
        });
    }

    @Override
    public int getItemCount() {
        return departments.size();
    }

    public void addDepartmentsList(List<Department> departmentsList) {
        departments.clear();
        departments.addAll(departmentsList);
        notifyDataSetChanged();
    }

    public void removeDepartment(Department department) {
        departments.remove(department);
        notifyDataSetChanged();
    }

    public static  class DepartmentViewHolder extends RecyclerView.ViewHolder {
        private  final ItemDepartmentBinding binding;

        public DepartmentViewHolder(@NonNull ItemDepartmentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface RecyclerViewCallBack {
        void onClickDeleteDepartment(Department deleteDepartment);
        void onClickEditDepartment(Department editDepartment);
    }

}
