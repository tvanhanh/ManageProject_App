package com.example.do_an_cs3.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
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
import com.example.do_an_cs3.View.Project.DetailProjectActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasks;
    private Context mContext;
    private DatabaseManager dbManager;
    private User currentUser;
    private ProjectAdapter projectAdapter;
    private int selectedPosition = -1;

    // Phương thức để đặt vị trí của mục được chọn từ ProjectAdapter
    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged(); // Cập nhật lại RecyclerView
    }

    public TaskAdapter(List<Task> tasks, Context mContext) {
        this.tasks = tasks;
        this.mContext = mContext;
        this.dbManager = new DatabaseManager(mContext);
        this.currentUser = dbManager.getUserInfo(getCurrentUserEmail());
        this.projectAdapter = new ProjectAdapter();
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

        if (task != null && currentUser != null) {
            holder.taskName.setText(task.getTaskName());
            holder.content.setText(task.getTaskDescription());
            holder.namePersonUpdate.setText(currentUser.getUserName());
            holder.deadline.setText(task.getTaskDeadline());

            // Log giá trị của task.getId() và task.getTaskStatus()
            Log.d("DEBUG", "Task ID: " + task.getId());
            Log.d("DEBUG", "Task Status: " + task.getTaskStatus());

            // Gỡ bỏ sự kiện lắng nghe trước khi gắn kết sự kiện mới
            holder.taskStatus.setOnCheckedChangeListener(null);

            // Đặt trạng thái của CheckBox dựa trên task.getTaskStatus()
            holder.taskStatus.setChecked("Hoàn thành".equals(task.getTaskStatus()));

            // Hiển thị thời gian hoàn thành nếu task đã hoàn thành
            if ("Hoàn thành".equals(task.getTaskStatus()) && task.getTimeComplete() != null) {
                holder.timeUpdate.setText("Xong vào: " + task.getTimeComplete());
            } else {
                holder.timeUpdate.setText(null);
            }

            // Gắn kết sự kiện OnCheckedChangeListener với CheckBox
            holder.taskStatus.setOnCheckedChangeListener((buttonView, isChecked) -> {
                String status = isChecked ? "Hoàn thành" : "Chưa hoàn thành";
                // Log thông tin trạng thái được cập nhật
                Log.d("DEBUG", "Updating status to: " + status);
                // Thực hiện cập nhật trạng thái trong cơ sở dữ liệu
                boolean success = dbManager.updateStatusTask(status, task.getId());

                if (success) {
                    // Cập nhật trạng thái của task nếu cập nhật trong cơ sở dữ liệu thành công
                    Log.d("DEBUG", "Trạng thái success " + success);
                    task.setTaskStatus(status);

                    if ("Hoàn thành".equals(status)) {
                        Date currentUpdate = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
                        String formattedDate = dateFormat.format(currentUpdate);
                        dbManager.updateTimeCompleteTask(formattedDate, task.getId());
                        task.setTimeComplete(formattedDate); // Cập nhật trong đối tượng Task
                        holder.timeUpdate.setText("Xong vào: " + task.getTimeComplete());

                            projectAdapter.setTaskNewComplete(selectedPosition,task.getTaskStatus(),task.getTaskName(),task.getUsername(),task.getTimeComplete());

                    } else {
                        task.setTimeComplete(null); // Xóa thời gian hoàn thành nếu chưa hoàn thành
                        holder.timeUpdate.setText(null);
                    }
                } else {
                    Log.e("ERROR", "Failed to update status");
                }
            });
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
    public Task getTask(int position) {
        if (position >= 0 && position < tasks.size()) {
            return tasks.get(position);
        }
        return null;
    }
    public String getCurrentUserEmail() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", null); // Trả về null nếu không tìm thấy email
    }
}
