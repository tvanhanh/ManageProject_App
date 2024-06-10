package com.example.do_an_cs3.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_cs3.Database.DatabaseFirebaseManager;
import com.example.do_an_cs3.Model.Project;
import com.example.do_an_cs3.View.back_end.View_fragment.FragmentHome.UpdateNewFragment;
import com.example.do_an_cs3.View.back_end.View_fragment.FragmentJob.NewjobFragment;
import com.example.do_an_cs3.ViewHolder.ProjectViewHolder;
import com.example.do_an_cs3.R;
import com.example.do_an_cs3.View.Project.DetailProjectActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectViewHolder> {
    private List<Project> projectList;
    private Context mContext;
    private ProjectViewHolder currentViewHolder;
    private View view;
    private DatabaseFirebaseManager dbFBManager;
    private UpdateNewFragment activity;



    public ProjectAdapter(List<Project> projectList, Context mContext, UpdateNewFragment activity) {
        this.projectList = projectList;
        this.mContext = mContext;
        this.activity =  activity;

    }

    public ProjectAdapter() {

    }


    // lấy id project
    public interface OnItemClickListener {
        void onItemClick(Project project);
    }

    public ProjectAdapter(List<Project> projectList) {
        this.projectList = projectList;
    }

    public ProjectAdapter(List<Project> projects, Context context, NewjobFragment newjobFragment) {
    }

    public ProjectAdapter(Context context) {
        this.mContext = context;
    }

    public void setProjects(List<Project> projects) {
        this.projectList = projects;
        notifyDataSetChanged(); // Thông báo cho RecyclerView cập nhật giao diện
    }



    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project, parent, false);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        //dbManager = new DatabaseManager(mContext);
        dbFBManager = new DatabaseFirebaseManager(mContext);
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
        String userEmail = getCurrentUserEmail();
        String encodedEmail = userEmail.replace(".", ",");
        DatabaseReference userRef = DatabaseFirebaseManager.getInstance().getDatabaseReference().child("users").child(encodedEmail);

        // Sử dụng ValueEventListener để lấy dữ liệu từ Firebase
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Kiểm tra xem dữ liệu có tồn tại hay không
                if (dataSnapshot.exists()) {
                    // Lấy dữ liệu từ DataSnapshot và hiển thị nó trong TextView
                    String userName = dataSnapshot.child("username").getValue(String.class);
                    String position = dataSnapshot.child("position").getValue(String.class);
                    dbFBManager.loadImageFromFirebase(encodedEmail, activity.getActivity(), circleImageView);
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

