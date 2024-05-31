package com.example.do_an_cs3.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_cs3.Model.Project;
import com.example.do_an_cs3.Model.User;
import com.example.do_an_cs3.R;
import com.example.do_an_cs3.View.Projects.DetailProjectActivity;
import com.example.do_an_cs3.ViewHolder.ProjectViewHolder;
import com.example.do_an_cs3.ViewHolder.UserViewHolder;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {

    private List<User> userList;
    private Context mContext;

    public UserAdapter(List<User> userList, Context mContext) {
        this.userList = userList;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);

        if(user != null){

            holder.tvUserName.setText(user.getUserName());
            holder.tvEmail.setText(user.getEmail());
            holder.tvDepartment.setText(user.getDeparment());
            holder.tvTotalProject.setText(user.getTotalProjects());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(mContext, DetailProjectActivity.class);
//                    intent.putExtra("projectCreationTime", project.getCreationTime());
//                    intent.putExtra("projectEmail", project.getEmail());
//                    intent.putExtra("projectName", project.getName());
//                    intent.putExtra("projectDeadline", project.getDeadline());
//                    intent.putExtra("projectStatus", project.getStatus());
//                    mContext.startActivity(intent);
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        if(userList != null){
            return userList.size();
        }
        return 0;
    }
    }
