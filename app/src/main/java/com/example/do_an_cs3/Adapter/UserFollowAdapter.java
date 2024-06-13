package com.example.do_an_cs3.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_cs3.Model.User;
import com.example.do_an_cs3.R;
import com.example.do_an_cs3.Task.AddTaskActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserFollowAdapter extends RecyclerView.Adapter<UserFollowAdapter.UserFollowViewHolder> {

    private List<User> users;
    private Context context;

    public UserFollowAdapter(List<User> users) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public UserFollowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_follow, parent, false);
        return new UserFollowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserFollowViewHolder holder, int position) {
        User user = users.get(position);
        if (user != null) {
            // holder.tvUserName1.setText("");
            holder.tvUserName.setText(user.getUserName());
        }
    }

    @Override
    public int getItemCount() {
        return users != null ? users.size() : 0;
    }

    public class UserFollowViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView circleImageViewFollow;
        private TextView tvUserName;
        private TextView tvUserName1;
        private Button addTask;

        public UserFollowViewHolder(@NonNull View itemView) {
            super(itemView);
//            circleImageViewFollow = itemView.findViewById(R.id.img_use_follow);
            tvUserName = itemView.findViewById(R.id.userNameFollow);
            addTask = itemView.findViewById(R.id.addTask);
        }
    }
}

