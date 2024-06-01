package com.example.do_an_cs3.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_cs3.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProjectViewHolder extends RecyclerView.ViewHolder {
    public TextView tvPercent, tvNamePerson, tvProjectName, tvDescriptionProject,
            tvProjectDeadline, tvProjectCreationTime, tvProjectStatus, tvProjectDepartment;
    public CircleImageView circleImageView;

    public ProjectViewHolder(@NonNull View itemView) {
        super(itemView);
        circleImageView= itemView.findViewById(R.id.img_userProject);
        //tvPercent = itemView.findViewById(R.id.tvPercent);
        tvNamePerson = itemView.findViewById(R.id.NamePersonOfTaskUpdate);
        tvProjectName = itemView.findViewById(R.id.tvProjectName);
       // tvDescriptionProject = itemView.findViewById(R.id.tvDescriptionProject);
        tvProjectDeadline = itemView.findViewById(R.id.tvProjectDeadline);
        tvProjectCreationTime = itemView.findViewById(R.id.tvProjectCreationTime);
        tvProjectStatus = itemView.findViewById(R.id.tvProjectStatus);
        //  tvProjectDepartment =

    }
}