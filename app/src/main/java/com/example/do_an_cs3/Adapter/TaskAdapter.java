//package com.example.do_an_cs3.Adapter;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CheckBox;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.do_an_cs3.Database.DatabaseManager;
//import com.example.do_an_cs3.Model.Task;
//import com.example.do_an_cs3.Model.User;
//import com.example.do_an_cs3.R;
//import com.example.do_an_cs3.View.Project.DetailProjectActivity;
//
//import java.util.List;
//
//public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
//
//    private List<Task> tasks;
//    private Context mContext;
//
//    private DatabaseManager dbManager;
//
//    public TaskAdapter(List<Task> tasks, Context mContext) {
//        this.tasks = tasks;
//        this.mContext = mContext;
//    }
//
//    @NonNull
//    @Override
//    public TaskAdapter.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
//
//        return new TaskViewHolder(view);
//
//
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull TaskAdapter.TaskViewHolder holder, int position) {
//
//        User user = dbManager.getUserInfo(getCurrentUserEmail());
//        Task task = tasks.get(position);
//        if (task != null &&  user != null) {
//            holder.taskName.setText(task.getTaskName());
//            //holder.taskStatus.setText(task.getTaskStatus());
//            String taskStatus = task.getTaskStatus();
//// Kiểm tra trạng thái và thiết lập CheckBox
//            if ("Hoàn thành".equals(taskStatus)) {
//                holder.taskStatus.setChecked(true);
//            } else {
//                holder.taskStatus.setChecked(false);
//            }
//            holder.content.setText(task.getTaskDescription());
//            holder.namePersonUpdate.setText(user.getUserName());
//            //holder.timeUpdate.setText();
//            holder.deadline.setText(task.getTaskDeadline());
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return tasks != null ? tasks.size() : 0;
//    }
//
//    public class TaskViewHolder extends RecyclerView.ViewHolder {
//
//        private TextView taskName;
//        private TextView content;
//        private TextView deadline;
//        private TextView namePersonUpdate;
//        private CheckBox taskStatus;
//
//        private TextView timeUpdate;
//
//
//        @SuppressLint("WrongViewCast")
//        public TaskViewHolder(@NonNull View itemView) {
//            super(itemView);
//            taskName = itemView.findViewById(R.id.NameTaskNewUpdate);
//            content = itemView.findViewById(R.id.contentTask);
//            deadline = itemView.findViewById(R.id.tvDealine);
//            namePersonUpdate = itemView.findViewById(R.id.NamePersonOfTaskUpdate);
//            timeUpdate = itemView.findViewById(R.id.timeUpdateTask);
//            taskStatus = itemView.findViewById(R.id.statusTask);
//        }
//
//    }
//    private String getCurrentUserEmail() {
//        SharedPreferences sharedPreferences = mContext.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
//        return sharedPreferences.getString("user_email", null); // Trả về null nếu không tìm thấy email
//    }
//}
package com.example.do_an_cs3.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_cs3.Database.DatabaseManager;
import com.example.do_an_cs3.Model.Task;
import com.example.do_an_cs3.Model.User;
import com.example.do_an_cs3.R;
import com.example.do_an_cs3.View.MainActivity;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasks;
    private Context mContext;
    private DatabaseManager dbManager;
    private User currentUser;
    private MainActivity mainActivity;
    public TaskAdapter(){

    }

    public TaskAdapter(List<Task> tasks, Context mContext) {
        this.tasks = tasks;
        this.mContext = mContext;
        this.dbManager = new DatabaseManager(mContext);
        this.currentUser = dbManager.getUserInfo(getCurrentUserEmail());
    }

    @NonNull
    @Override
    public TaskAdapter.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        if (task != null && currentUser != null) {
            holder.taskName.setText(task.getTaskName());
            String taskStatus = task.getTaskStatus();

            // Kiểm tra trạng thái và thiết lập CheckBox
            if ("Hoàn thành".equals(taskStatus)) {
                holder.taskStatus.setChecked(true);
            } else {
                holder.taskStatus.setChecked(false);
            }

            holder.content.setText(task.getTaskDescription());
            holder.namePersonUpdate.setText(currentUser.getUserName());
            holder.deadline.setText(task.getTaskDeadline());
        }
    }

    @Override
    public int getItemCount() {
        return tasks != null ? tasks.size() : 0;
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {

        private TextView taskName;
        private TextView content;
        private TextView deadline;
        private TextView namePersonUpdate;
        private CheckBox taskStatus;
        private TextView timeUpdate;

        @SuppressLint("WrongViewCast")
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.NameTaskNewUpdate);
            content = itemView.findViewById(R.id.contentTask);
            deadline = itemView.findViewById(R.id.tvDealine);
            namePersonUpdate = itemView.findViewById(R.id.NamePersonOfTaskUpdate);
            timeUpdate = itemView.findViewById(R.id.timeUpdateTask);
            taskStatus = itemView.findViewById(R.id.statusTask);
        }
    }

    public String getCurrentUserEmail() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", null); // Trả về null nếu không tìm thấy email
    }
}
