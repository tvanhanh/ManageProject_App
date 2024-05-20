package com.example.do_an_cs3.Ardapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_cs3.Model.Project;
import com.example.do_an_cs3.ProjectViewHolder;
import com.example.do_an_cs3.R;

import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectViewHolder> {
    private List<Project> projectList;

    public ProjectAdapter(List<Project> projectList) {
        this.projectList = projectList;
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project, parent, false);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        Project project = projectList.get(position);
        holder.tvEmail.setText(project.getEmail());
        holder.tvPercent.setText(project.getPercentCompleted());
        holder.tvProjectName.setText(project.getName());
        holder.tvDescriptionProject.setText(project.getDescription());
        String deadline = project.getDeadline();
        holder.tvProjectDeadline.setText(deadline);
        String creationTime = project.getCreationTime();
        holder.tvProjectCreationTime.setText(creationTime);
        holder.tvProjectStatus.setText(project.getStatus());
        holder.tvProjectDepartment.setText(project.getDepartment());
        // sau đổi thành task nếu task hoàn thành thì hiện ra
//        if (project.isProjectCompleted()) {
//            holder.tvProjectCompletion.setVisibility(View.VISIBLE);
//            holder.tvProjectCompletion.setText(project.getProjectCompletionInfo());
//        } else {
//            holder.tvProjectCompletion.setVisibility(View.GONE);
//        }
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }
}


