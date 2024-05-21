package com.example.do_an_cs3.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_cs3.Model.Project;
import com.example.do_an_cs3.ProjectViewHolder;
import com.example.do_an_cs3.R;
import com.example.do_an_cs3.View.DetailProjectActivity;

import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectViewHolder> {
    private List<Project> projectList;
    private Context mContext;

    public ProjectAdapter(List<Project> projectList, Context mContext) {
        this.projectList = projectList;
        this.mContext = mContext;
    }

    public void setData(List<Project> listProject){
        this.projectList = listProject;
        notifyDataSetChanged();
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

        if(project != null){

            holder.tvProjectCreationTime.setText(project.getCreationTime());
            holder.tvNamePerson.setText(project.getEmail());
          //  holder.tvPercent.setText(String.valueOf(project.getPercentCompleted()));
            holder.tvProjectName.setText(project.getName());
          //  holder.tvDescriptionProject.setText(project.getDescription());
            holder.tvProjectDeadline.setText(project.getDeadline());
          //  holder.tvProjectCreationTime.setText(project.getCreationTime());
            holder.tvProjectStatus.setText(project.getStatus());
           // holder.tvProjectDepartment.setText(project.getDepartment());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DetailProjectActivity.class);
                    intent.putExtra("projectCreationTime", project.getCreationTime());
                    intent.putExtra("projectEmail", project.getEmail());
                    intent.putExtra("projectName", project.getName());
                    intent.putExtra("projectDeadline", project.getDeadline());
                    intent.putExtra("projectStatus", project.getStatus());
                    mContext.startActivity(intent);
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        if(projectList != null){
            return projectList.size();
        }
        return 0;
    }
}
