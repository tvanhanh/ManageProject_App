package com.example.do_an_cs3.View.Projects;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_cs3.R;

public class ProjectViewHolder extends RecyclerView.ViewHolder {
    public TextView tvPercent, tvNamePerson, tvProjectName, tvDescriptionProject,
            tvProjectDeadline, tvProjectCreationTime, tvProjectStatus, tvProjectDepartment;

    public ProjectViewHolder(@NonNull View itemView) {
        super(itemView);
        //tvPercent = itemView.findViewById(R.id.tvPercent);
        tvNamePerson = itemView.findViewById(R.id.tvPersonCreation);
        tvProjectName = itemView.findViewById(R.id.tvProjectName);
        //   tvDescriptionProject = itemView.findViewById(R.id.tvDescriptionProject);
        tvProjectDeadline = itemView.findViewById(R.id.tvProjectDeadline);
        tvProjectCreationTime = itemView.findViewById(R.id.tvProjectCreationTime);
        tvProjectStatus = itemView.findViewById(R.id.tvProjectStatus);
        //  tvProjectDepartment =


    }
}
