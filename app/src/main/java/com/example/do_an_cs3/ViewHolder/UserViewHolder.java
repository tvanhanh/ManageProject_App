package com.example.do_an_cs3.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_cs3.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserViewHolder extends RecyclerView.ViewHolder {
 public TextView tvUserName, tvEmail, tvDepartment, tvTotalProject;
 public CircleImageView circleImageView;

 public UserViewHolder(@NonNull View itemView) {
  super(itemView);
  tvUserName = itemView.findViewById(R.id.tvUserName);
  tvEmail = itemView.findViewById(R.id.tvEmail);
  tvDepartment = itemView.findViewById(R.id.tvDepartment);
  circleImageView = itemView.findViewById(R.id.img_user);
  //tvTotalProject = itemView.findViewById(R.id.);
 }

}
