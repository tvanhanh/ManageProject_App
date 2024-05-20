package com.example.do_an_cs3;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProjectViewHolder extends RecyclerView.ViewHolder {
    public TextView tvPercent, tvEmail, tvProjectName, tvDescriptionProject,
            tvProjectDeadline, tvProjectCreationTime, tvProjectStatus, tvProjectDepartment;

    public ProjectViewHolder(@NonNull View itemView) {
        super(itemView);
        tvPercent = itemView.findViewById(R.id.tvPercent);
        tvEmail = itemView.findViewById(R.id.tvEmail);
        tvProjectName = itemView.findViewById(R.id.tvProjectName);
        tvDescriptionProject = itemView.findViewById(R.id.tvDescriptionProject);
        tvProjectDeadline = itemView.findViewById(R.id.tvProjectDeadline);
        tvProjectCreationTime = itemView.findViewById(R.id.tvProjectCreationTime);
        tvProjectStatus = itemView.findViewById(R.id.tvProjectStatus);
        tvProjectDepartment = itemView.findViewById(R.id.tvDepartment);
    }
}

