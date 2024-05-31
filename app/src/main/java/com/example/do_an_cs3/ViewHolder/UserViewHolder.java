package com.example.do_an_cs3.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_cs3.R;

public class UserViewHolder extends RecyclerView.ViewHolder {
 public TextView tvUserName, tvEmail, tvDepartment, tvTotalProject;

 public UserViewHolder(@NonNull View itemView) {
  super(itemView);
  tvUserName = itemView.findViewById(R.id.tvPersonCreation);
  tvEmail = itemView.findViewById(R.id.tvProjectName);
  tvDepartment = itemView.findViewById(R.id.tvProjectDeadline);
  tvTotalProject = itemView.findViewById(R.id.tvProjectCreationTime);
 }

}
