package com.example.do_an_cs3.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_cs3.Database.DatabaseFirebaseManager;
import com.example.do_an_cs3.Model.Task;
import com.example.do_an_cs3.R;
import com.example.do_an_cs3.Task.EditTaskActivity;
import com.example.do_an_cs3.View.Project.DetailProjectActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasks;
    private Context mContext;
    private DatabaseFirebaseManager dbFBManager;
    private int selectedPosition = -1;
    private DetailProjectActivity activityDetail;

    // Phương thức để đặt vị trí của mục được chọn từ ProjectAdapter
    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged(); // Cập nhật lại RecyclerView
    }

    public TaskAdapter(List<Task> tasks, Context mContext,DetailProjectActivity detailProjectActivity) {
        this.tasks = tasks;
        this.mContext = mContext;
        activityDetail = detailProjectActivity;
    }

    public TaskAdapter() {
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
        dbFBManager = new DatabaseFirebaseManager();
        if (task != null) {
            holder.taskName.setText(task.getTaskName());
            holder.content.setText(task.getTaskDescription());
            holder.deadline.setText(task.getTaskDeadline());
            holder.timeUpdate.setText(task.getTaskStatus());
            String emailEncoded = task.getEmailParticipant();
            emailEncoded = emailEncoded.replace(",", ".");
            holder.userdo.setText(emailEncoded);
            displayUserInfo(task.getEmailParticipant(),holder.userName,holder.circleImageView);
            holder.options.setOnClickListener(v -> showPopupMenu(v, task.getId(), task.getTaskName(), position));
            // Log giá trị của task.getId() và task.getTaskStatus()
            Log.d("DEBUG", "Task ID: " + task.getId());
            Log.d("DEBUG", "Task Status: " + task.getTaskStatus());
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
        private TextView timeUpdate;
        private Button addTassk;
        private TextView userdo;
        private TextView options;
        private CircleImageView circleImageView;
        private TextView userName;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.NameTaskNewUpdate1);
            content = itemView.findViewById(R.id.contentTask);
            deadline = itemView.findViewById(R.id.tvDealine);
            timeUpdate = itemView.findViewById(R.id.timeUpdateTask);
            addTassk = itemView.findViewById(R.id.addTask);
            userdo = itemView.findViewById(R.id.userDO);
            options = itemView.findViewById(R.id.options);
            circleImageView = itemView.findViewById(R.id.avatarPartiTask);
            userName = itemView.findViewById(R.id.userNameDo);
        }
    }

    public Task getTask(int position) {
        if (position >= 0 && position < tasks.size()) {
            return tasks.get(position);
        }
        return null;
    }

    private void showPopupMenu(View view, String taskId, String taskName, int position) {
        PopupMenu popupMenu = new PopupMenu(mContext, view);
        popupMenu.inflate(R.menu.popup_menutask);
        popupMenu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.action_edit) {
                if (taskId != null) {
                    editTask(taskId);
                } else {
                    Toast.makeText(mContext, "Không thể tìm thấy công việc để chỉnh sửa", Toast.LENGTH_SHORT).show();
                }
                return true;
            } else if (itemId == R.id.action_delete) {
                deleteTask(taskId, taskName);
                return true;
            } else if (itemId == R.id.comeplete) {
                updateTaskStatus(mContext, taskId, position);
                return true;
            } else {
                return false;
            }
        });
        popupMenu.show();
    }

    private void deleteTask(String taskId, String taskName) {
        DatabaseReference departmentRef = FirebaseDatabase.getInstance().getReference("tasks").child(taskId);
        departmentRef.removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(mContext, "Xóa công việc " + taskName + " thành công", Toast.LENGTH_SHORT).show();
                updateTaskList(taskId);
            } else {
                Toast.makeText(mContext, "Xóa công việc thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateTaskList(String deletedDepartmentId) {
        List<Task> updatedList = new ArrayList<>();
        for (Task task : tasks) {
            if (!task.getId().equals(deletedDepartmentId)) {
                updatedList.add(task);
            }
        }
        tasks = updatedList;
        notifyDataSetChanged();
    }

    public void updateTaskStatus(Context context, String taskId, int position) {
        DatabaseReference taskRef = FirebaseDatabase.getInstance().getReference("tasks").child(taskId);
        taskRef.child("taskStatus").setValue("Đã hoàn thành").addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                saveTaskStatusToSharedPreferences(taskId, "Đã hoàn thành");
                tasks.get(position).setTaskStatus("Đã hoàn thành");
                notifyItemChanged(position);
                Toast.makeText(context, "Cập nhật trạng thái công việc thành công.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Cập nhật trạng thái công việc thất bại.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void saveTaskStatusToSharedPreferences(String taskId, String status) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("task_status_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(taskId, status);
        editor.apply();
    }
    private void editTask(String taskId) {
        Intent intent = new Intent(mContext, EditTaskActivity.class);
        intent = intent.putExtra("taskId",taskId);// Truyền đối tượng Task thay vì chỉ truyền ID
        mContext.startActivity(intent);
    }

    private Task getTaskById(String taskId) {
        for (Task task : tasks) {
            if (task.getId().equals(taskId)) {
                return task;
            }
        }
        return null;
    }
    public void displayUserInfo(String emailParti,TextView tvUserName, CircleImageView circleImageView) {
        DatabaseReference userRef = DatabaseFirebaseManager.getInstance().getDatabaseReference().child("users").child(emailParti);

        // Sử dụng ValueEventListener để lấy dữ liệu từ Firebase
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Kiểm tra xem dữ liệu có tồn tại hay không
                if (dataSnapshot.exists()) {
                    // Lấy dữ liệu từ DataSnapshot và hiển thị nó trong TextView
                    String userName = dataSnapshot.child("userName").getValue(String.class);
                    String position = dataSnapshot.child("position").getValue(String.class);
                    dbFBManager.loadImageFromFirebase(emailParti, activityDetail, circleImageView);
                    // Hiển thị dữ liệu trong TextView
                    tvUserName.setText(userName);
                } else {
                    // Xử lý trường hợp không có dữ liệu

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }



    public String getCurrentUserEmail() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", null); // Trả về null nếu không tìm thấy email
    }
}
