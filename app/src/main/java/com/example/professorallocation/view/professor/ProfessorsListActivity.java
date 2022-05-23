package com.example.professorallocation.view.professor;

import static com.example.professorallocation.view.professor.AddEditProfessorActivity.EXTRA_EDIT_PROFESSOR;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.professorallocation.data.repository.ProfessorRepository;
import com.example.professorallocation.data.service.RepositoryCallBack;
import com.example.professorallocation.databinding.ActivityProfessorsListBinding;
import com.example.professorallocation.model.Professor;
import com.example.professorallocation.view.department.DepartmentsListActivity;

import java.util.List;

public class ProfessorsListActivity extends AppCompatActivity {

    private ActivityProfessorsListBinding binding;
    private ProfessorRepository professorRepository;
    private ProfessorListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfessorsListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        professorRepository = new ProfessorRepository();
        setupRecyclerView();
        setupOnClickListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showAllProfessors();
    }

    private void setupOnClickListener() {
        binding.buttonRefreshProfessorList.setOnClickListener(view -> {
            showAllProfessors();
        });
        binding.buttonAddProfessor.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddEditProfessorActivity.class);
            startActivity(intent);
        });
    }

    private void setupRecyclerView() {
        adapter = new ProfessorListAdapter(new ProfessorListAdapter.RecyclerViewCallBack() {
            @Override
            public void onClickDeleteProfessor(Professor removeProfessor) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ProfessorsListActivity.this);
                builder.setTitle("Atention!");
                builder.setMessage("Are you sure you want to delete this? ");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteProfessor(removeProfessor);
                    }
                });

                builder.setNegativeButton("No", null);
                builder.create().show();


            }

            @Override
            public void onClickEditProfessor(Professor editProfessor) {
                Intent intent = new Intent(ProfessorsListActivity.this, AddEditProfessorActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(EXTRA_EDIT_PROFESSOR, editProfessor);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        binding.listProfessors.setAdapter(adapter);
        binding.listProfessors.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    private void showAllProfessors() {
        showProgressBar(View.VISIBLE);
        professorRepository.getListOfProfessors(new RepositoryCallBack<List<Professor>>() {
            @Override
            public void onResponse(List<Professor> response) {
                adapter.addProfessorsList(response);
                showProgressBar(View.GONE);
            }

            @Override
            public void onFailure(Throwable t) {
                showProgressBar(View.GONE);
            }
        });
    }

    private void deleteProfessor(Professor professor) {
        showProgressBar(View.VISIBLE);
        professorRepository.removeProfessor(professor.getId(), new RepositoryCallBack<Void>() {
            @Override
            public void onResponse(Void response) {
                adapter.removeProfessor(professor);
                showProgressBar(View.GONE);
            }

            @Override
            public void onFailure(Throwable t) {
                showProgressBar(View.GONE);
            }
        });
    }

    private void showProgressBar(int showProgressBar) {
        binding.progressBarProfessorsList.setVisibility(showProgressBar);
    }
}