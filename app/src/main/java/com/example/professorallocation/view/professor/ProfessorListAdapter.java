package com.example.professorallocation.view.professor;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.professorallocation.databinding.ItemProfessorBinding;
import com.example.professorallocation.model.Professor;

import java.util.ArrayList;
import java.util.List;

public class ProfessorListAdapter extends RecyclerView.Adapter<ProfessorListAdapter.ProfessorViewHolder> {

    private List<Professor> professors;
    private RecyclerViewCallBack recyclerViewCallBack;

    public ProfessorListAdapter(RecyclerViewCallBack recyclerViewCallBack) {
        professors = new ArrayList<>();
        this.recyclerViewCallBack = recyclerViewCallBack;
    }

    @NonNull
    @Override
    public ProfessorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProfessorBinding binding = ItemProfessorBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProfessorViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfessorViewHolder holder, int position) {
        Professor professor = professors.get(position);
        holder.binding.nameProfessor.setText("Prof. " + professor.getName());
        holder.binding.departmentProfessor.setText("Dept. " + professor.getDepart().getName());
        holder.binding.cpfProfessor.setText("CPF: " + professor.getCpf());
        holder.binding.buttonDeleteProfessor.setOnClickListener(view -> {
            recyclerViewCallBack.onClickDeleteProfessor(professor);
        });
        holder.binding.buttonEditProfessor.setOnClickListener(view -> {
            recyclerViewCallBack.onClickEditProfessor(professor);
        });
    }

    @Override
    public int getItemCount() {
        return professors.size();
    }

    public void removeProfessor(Professor professor) {
        professors.remove(professor);
        notifyDataSetChanged();
    }

    public void addProfessorsList(List<Professor> professorsList) {
        professors.clear();
        professors.addAll(professorsList);
        notifyDataSetChanged();
    }

    public static class ProfessorViewHolder extends RecyclerView.ViewHolder {
        private final ItemProfessorBinding binding;

        public ProfessorViewHolder(@NonNull ItemProfessorBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface RecyclerViewCallBack {
        void onClickDeleteProfessor(Professor deleteProfessor);
        void onClickEditProfessor(Professor editProfessor);
    }
}
