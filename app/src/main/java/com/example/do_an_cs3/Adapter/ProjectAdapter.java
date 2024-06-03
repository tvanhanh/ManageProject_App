package com.example.do_an_cs3.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_cs3.Database.DatabaseManager;
import com.example.do_an_cs3.Model.Project;
import com.example.do_an_cs3.Model.User;
import com.example.do_an_cs3.View.MainActivity;
import com.example.do_an_cs3.ViewHolder.ProjectViewHolder;
import com.example.do_an_cs3.R;
import com.example.do_an_cs3.View.Project.DetailProjectActivity;

import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectViewHolder> {
    private List<Project> projectList;
    private Context mContext;
    private DatabaseManager dbManager;
    private  TaskAdapter taskAdapter;
    private ProjectViewHolder currentViewHolder;
    private View view;

    public ProjectAdapter(List<Project> projectList, Context mContext) {
        this.projectList = projectList;
        this.mContext = mContext;
    }
    public ProjectAdapter(List<Project> projectList, Context mContext, TaskAdapter taskAdapter) {
        this.projectList = projectList;
        this.mContext = mContext;
        this.taskAdapter = taskAdapter;
    }

    public ProjectAdapter() {

    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project, parent, false);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        dbManager = new DatabaseManager(mContext);
        Project project = projectList.get(position);
        currentViewHolder = holder;
        if(project != null){
            holder.tvProjectCreationTime.setText(project.getCreationTime());
            displayUserInfo(holder.tvNamePersonCreation,holder.circleImageView);
            holder.tvProjectName.setText(project.getName());
            holder.tvProjectDeadline.setText(project.getDeadline());
            holder.tvProjectStatus.setText(project.getStatus());
            holder.lnTask.setVisibility(View.GONE);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int clickedPosition = holder.getAdapterPosition(); // Lấy vị trí của mục được nhấp vào
                    if (clickedPosition != RecyclerView.NO_POSITION) {
                        Project clickedProject = projectList.get(clickedPosition); // Lấy dự án tương ứng với vị trí được nhấp vào
                        Intent intent = new Intent(mContext, DetailProjectActivity.class);
                        intent.putExtra("idProject", clickedProject.getId());
                        intent.putExtra("projectCreationTime", clickedProject.getCreationTime());
                        intent.putExtra("projectEmail", clickedProject.getEmail());
                        intent.putExtra("projectName", clickedProject.getName());
                        intent.putExtra("projectDeadline", clickedProject.getDeadline());
                        intent.putExtra("projectStatus", clickedProject.getStatus());

                        // Gọi phương thức để truyền vị trí của mục được chọn
                        TaskAdapter taskAdapter = new TaskAdapter();
                        taskAdapter.setSelectedPosition(clickedPosition);

                        mContext.startActivity(intent);
                    }
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

    public void setTaskNewComplete(int position, String status, String name, String person, String time) {
        // Kiểm tra xem currentViewHolder đã được khởi tạo chưa
        if (currentViewHolder != null && position == currentViewHolder.getAdapterPosition()) {
            if (status.equals("Hoàn thành")) {
                currentViewHolder.checkBoxTask.setChecked(true);
            } else {
                currentViewHolder.checkBoxTask.setChecked(false);
            }
            currentViewHolder.tvNameTaskNewUpdate.setText(name);
            currentViewHolder.tvNamePersonOfTaskUpdate.setText(person);
            currentViewHolder.timeUpdateTask.setText(time);
        }
    }
    public void displayUserInfo(TextView tvUserName, CircleImageView circleImageView) {
        User user = dbManager.getUserInfo(getCurrentUserEmail());
        if (user != null) {
            tvUserName.setText(user.getUserName());
            if (user.getAvatar() != null) {
                byte[] avatarBytes = Base64.decode(user.getAvatar(), Base64.DEFAULT);
                Bitmap avatarBitmap = BitmapFactory.decodeByteArray(avatarBytes, 0, avatarBytes.length);
                circleImageView.setImageBitmap(avatarBitmap);
            }
        }
    }
    public String getCurrentUserEmail() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", null); // Trả về null nếu không tìm thấy email
    }

}
