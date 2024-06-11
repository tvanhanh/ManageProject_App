package com.example.do_an_cs3.ViewHolder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_cs3.R;

import de.hdodenhof.circleimageview.CircleImageView;

public  class ProjectViewHolder extends RecyclerView.ViewHolder {
    public TextView tvPercent, tvNamePersonCreation, tvProjectName, tvDescriptionProject,
            tvProjectDeadline, tvProjectCreationTime, tvProjectStatus, tvProjectDepartment;
    public CircleImageView circleImageView;

    public LinearLayout lnTask;
    public CheckBox checkBoxTask;
    public TextView tvNameTaskNewUpdate;
    public TextView tvNamePersonOfTaskUpdate;
    public TextView timeUpdateTask;

    public ProjectViewHolder(@NonNull View itemView) {
        super(itemView);
        circleImageView= itemView.findViewById(R.id.img_userProject);
        //tvPercent = itemView.findViewById(R.id.tvPercent);
        tvNamePersonCreation = itemView.findViewById(R.id.tvPersonCreation);
        tvProjectName = itemView.findViewById(R.id.tvProjectName);
        tvDescriptionProject = itemView.findViewById(R.id.tvprojectContext);
        tvProjectDeadline = itemView.findViewById(R.id.tvProjectDeadline);
        tvProjectCreationTime = itemView.findViewById(R.id.tvProjectCreationTime);
        tvProjectStatus = itemView.findViewById(R.id.tvProjectStatus);
        //  tvProjectDepartment =
        lnTask = itemView.findViewById(R.id.lnTaskCompleteNew);
        checkBoxTask = itemView.findViewById(R.id.checkboxTask);
        tvNameTaskNewUpdate = itemView.findViewById(R.id.NameTaskNewUpdate);
        tvNamePersonOfTaskUpdate = itemView.findViewById(R.id.NamePersonOfTaskUpdate);
        timeUpdateTask = itemView.findViewById(R.id.TimeUpdateTask);
    }


}