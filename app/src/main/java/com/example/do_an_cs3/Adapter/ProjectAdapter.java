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

    public ProjectAdapter(List<Project> projectList, Context mContext) {
        this.projectList = projectList;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project, parent, false);
        return new ProjectViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        dbManager = new DatabaseManager(mContext);
        taskAdapter = new TaskAdapter();
        Project project = projectList.get(position);
        if(project != null){
            holder.tvProjectCreationTime.setText(project.getCreationTime());
            displayUserInfo(holder.tvNamePerson,holder.circleImageView);
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
                    intent.putExtra("idProject", project.getId());
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
