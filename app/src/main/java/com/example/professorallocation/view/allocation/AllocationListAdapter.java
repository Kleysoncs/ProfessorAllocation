package com.example.professorallocation.view.allocation;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.professorallocation.databinding.ItemAllocationBinding;
import com.example.professorallocation.model.Allocation;

import java.util.ArrayList;
import java.util.List;

public class AllocationListAdapter extends RecyclerView.Adapter<AllocationListAdapter.AllocationViewHolder> {

    private List<Allocation> allocations;
    private RecyclerViewCallBack recyclerViewCallBack;

    public AllocationListAdapter(RecyclerViewCallBack recyclerViewCallBack) {
        allocations = new ArrayList<>();
        this.recyclerViewCallBack = recyclerViewCallBack;
    }

    @NonNull
    @Override
    public AllocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAllocationBinding binding = ItemAllocationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AllocationViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AllocationViewHolder holder, int position) {
        Allocation allocation = allocations.get(position);
        holder.binding.professorNameAllocation.setText("Prof. " + allocation.getProfessor().getName());
        holder.binding.professorCpfAllocation.setText("CPF: " + allocation.getProfessor().getCpf());
        holder.binding.departmentNameAllocation.setText("Dept. " + allocation.getProfessor().getDepart().getName());
        holder.binding.courseNameAllocation.setText(allocation.getCourse().getName());
        holder.binding.dayWeekAllocation.setText(allocation.getDay());
        holder.binding.endTimeAllocation.setText(allocation.getEnd().substring(0, 5));
        holder.binding.startTimeAllocation.setText(allocation.getStart().substring(0, 5));
        holder.binding.buttonDeleteAllocation.setOnClickListener(view -> {
            recyclerViewCallBack.onClickDeleteAllocation(allocation);
        });
        holder.binding.buttonEditAllocation.setOnClickListener(view -> {
            recyclerViewCallBack.onClickEditAllocation(allocation);
        });
    }

    @Override
    public int getItemCount() {
        return allocations.size();
    }

    public void removeAllocation(Allocation allocation) {
        allocations.remove(allocation);
        notifyDataSetChanged();
    }

    public void addAllocationsList(List<Allocation> allocationsList) {
        allocations.clear();
        allocations.addAll(allocationsList);
        notifyDataSetChanged();
    }

    public static class AllocationViewHolder extends RecyclerView.ViewHolder {
        private final ItemAllocationBinding binding;

        public AllocationViewHolder(@NonNull ItemAllocationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface RecyclerViewCallBack {
        void onClickDeleteAllocation(Allocation deleteAllocation);
        void onClickEditAllocation(Allocation editAllocation);

    }
}
