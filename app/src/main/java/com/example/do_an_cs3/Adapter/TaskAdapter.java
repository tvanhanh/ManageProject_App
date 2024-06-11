package com.example.do_an_cs3.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_cs3.Model.Task;
import com.example.do_an_cs3.Model.User;
import com.example.do_an_cs3.R;
import com.example.do_an_cs3.View.Project.DetailProjectActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private Context mContext;
    private TaskViewHolder currentViewHolder;

    private User currentUser;
    private int selectedPosition = -1;

    public TaskAdapter(List<Task> tasks, Context mContext) {
        this.taskList = tasks;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }
    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        currentViewHolder = holder;

        if (task != null) {
            holder.taskName.setText(task.getName());
            holder.content.setText(task.getDescription());
            holder.options.setText("cc");
            holder.options.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopupMenu(v, task.getId(),task.getName());
                }
            });

            holder.deadline.setText(task.getDeadline());
            holder.timeUpdate.setText(task.getStatus());
          //  holder.namePersonUpdate.setText(currentUser.getUserName());
            //holder.taskStatus.setChecked("Hoàn thành".equals(task.getStatus()));
//            if ("Hoàn thành".equals(task.getStatus()) && task.getTimeComplete() != null) {
//                holder.timeUpdate.setText("Xong vào: " + task.getTimeComplete());
//            } else {
//                holder.timeUpdate.setText(null);
//            }

            Log.d("TaskAdapter", "Task Name: " + task.getName());
            Log.d("TaskAdapter", "Task Description: " + task.getDescription());
            Log.d("TaskAdapter", "Task Deadline: " + task.getDeadline());


            holder.taskStatus.setOnCheckedChangeListener((buttonView, isChecked) -> {
                String status = isChecked ? "Hoàn thành" : "Chưa hoàn thành";
                task.setStatus(status);

//                if ("Hoàn thành".equals(status)) {
//                    Date currentUpdate = new Date();
//                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
//                    String formattedDate = dateFormat.format(currentUpdate);
//                    task.setTimeComplete(formattedDate);
//                    holder.timeUpdate.setText("Xong vào: " + task.getTimeComplete());
//                } else {
//                    task.setTimeComplete(null);
//                    holder.timeUpdate.setText("vài giây trước");
//
//                }
            });
        } else {
            Log.e("TaskAdapter", "Task is null at position " + position);
        }

    }

    @Override
    public int getItemCount() {
        return taskList != null ? taskList.size() : 0;
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView taskName;
        private TextView content;
        private TextView deadline;
        private TextView namePersonUpdate;
        private CheckBox taskStatus;
        private TextView timeUpdate;
        private TextView options;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.NameTaskNewUpdate);
            content = itemView.findViewById(R.id.contentTask);
            deadline = itemView.findViewById(R.id.tvDealine);
            namePersonUpdate = itemView.findViewById(R.id.NamePersonOfTaskUpdate);
            timeUpdate = itemView.findViewById(R.id.timeUpdateTask);
            taskStatus = itemView.findViewById(R.id.statusTask);
            options = itemView.findViewById(R.id.options);


        }
    }
    private void showPopupMenu(View view, String taskId, String taskName) {
        PopupMenu popupMenu = new PopupMenu(mContext, view);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.action_edit) {
                    //    showUpdateDialog(departmentId);
                    //  Toast.makeText(mContext, "Sửa: " + departmentId, Toast.LENGTH_SHORT).show();
                    return true;
                } else if (itemId == R.id.action_delete) {
                    // Gọi hàm xóa phòng ban khi người dùng chọn tuỳ chọn "Xóa" từ menu popup
                    deleteDepartment(taskId, taskName);
                    return true;
                } else if (itemId == R.id.comeplete) {

                   DetailProjectActivity.updateTaskStatus(mContext, taskId);
                    return true;
                } else {
                    return false;
                }

            }
        });
        popupMenu.show();
    }
    private void deleteDepartment(String taskId, String taskName) {
        DatabaseReference departmentRef = FirebaseDatabase.getInstance().getReference("tasks").child(taskId);
        departmentRef.removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Xóa thành công, cập nhật danh sách và thông báo
                Toast.makeText(mContext, "Xóa phòng ban" + taskName + "thành công ", Toast.LENGTH_SHORT).show();
                updateDepartmentList(taskId);
            } else {
                // Xảy ra lỗi, thông báo lỗi
                Toast.makeText(mContext, "Xóa phòng ban thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateDepartmentList(String deletedDepartmentId) {
        // Tạo một danh sách tạm thời để lưu trữ các phòng ban mới sau khi xóa
        List<Task> updatedList = new ArrayList<>();
        // Lặp qua danh sách phòng ban hiện tại
        for (Task task : taskList) {
            // Nếu ID của phòng ban hiện tại không trùng với ID của phòng ban vừa xóa, thêm phòng ban vào danh sách tạm thời
            if (!task.getId().equals(deletedDepartmentId)) {
                updatedList.add(task);
            }
        }
        // Cập nhật danh sách phòng ban với danh sách mới đã được cập nhật
        taskList = updatedList;
        // Thông báo cho Adapter biết rằng dữ liệu đã thay đổi
        notifyDataSetChanged();
    }

    public String getCurrentUserEmail() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", null);
    }
}
