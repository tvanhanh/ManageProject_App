package com.example.do_an_cs3.View.Projects;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.anychart.AnyChartView;
import com.example.do_an_cs3.Adapter.TaskAdapter;
import com.example.do_an_cs3.Adapter.UserFollowAdapter;
import com.example.do_an_cs3.Database.Database;
import com.example.do_an_cs3.Database.DatabaseManager;
import com.example.do_an_cs3.Model.Deparments;
import com.example.do_an_cs3.Model.Task;
import com.example.do_an_cs3.Model.User;
import com.example.do_an_cs3.R;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailProjectActivity extends AppCompatActivity {
    private RecyclerView rcv_userFollow;
    private RecyclerView rcv_task;
    private  RecyclerView.Adapter userFollowAdapter;
    private  RecyclerView.Adapter taskAdapter;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_project);

                Button btnViewMore = findViewById(R.id.btnViewMore);
                btnViewMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showBottomSheetDialog();
                    }
                });
        rcv_userFollow = findViewById(R.id.rcv_userFollow);
        LinearLayoutManager linearLayoutManageruserFollow = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rcv_userFollow.setLayoutManager(linearLayoutManageruserFollow);

        List<User> user = createDummyData();
        userFollowAdapter = new UserFollowAdapter(user);

        rcv_userFollow.setAdapter(userFollowAdapter);

        rcv_task = findViewById(R.id.rcv_task);
        LinearLayoutManager linearLayoutManagerTask = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcv_task.setLayoutManager(linearLayoutManagerTask);

        List<Task> task = null;
        taskAdapter = new TaskAdapter(task);
        rcv_task.setAdapter(taskAdapter);
            }
    private List<User> createDummyData() {
        List<User> dummyData = new ArrayList<>();
        dummyData.add(new User("Hanh"));
        dummyData.add(new User("No"));
        dummyData.add(new User("Ha"));
        dummyData.add(new User("Hanh"));
        dummyData.add(new User("No"));
        dummyData.add(new User("Ha"));
        return dummyData;
    }
    private List<User> createDummyDataTask() {
        List<User> dummyData = new ArrayList<>();
        dummyData.add(new User("Hanh"));
        dummyData.add(new User("No"));
        dummyData.add(new User("Ha"));
        dummyData.add(new User("Hanh"));
        dummyData.add(new User("No"));
        dummyData.add(new User("Ha"));
        return dummyData;
    }
            private void showBottomSheetDialog() {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
                View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog, null);
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

                // Xử lý sự kiện cho các nút trong BottomSheetDialog
                Button btnShareTask = bottomSheetView.findViewById(R.id.btnShareTask);
                Button btnConfirmComplete = bottomSheetView.findViewById(R.id.btnConfirmComplete);
                Button btnPause = bottomSheetView.findViewById(R.id.btnPause);
                Button btnReject = bottomSheetView.findViewById(R.id.btnReject);
                Button btnDeleteTask = bottomSheetView.findViewById(R.id.btnDeleteTask);
                Button btnEditExtend = bottomSheetView.findViewById(R.id.btnEditExtend);
                Button btnHistory = bottomSheetView.findViewById(R.id.btnHistory);

                // Thiết lập các sự kiện click cho các nút
                btnShareTask.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Xử lý chia sẻ công việc
                        bottomSheetDialog.dismiss();
                    }
                });

                btnConfirmComplete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Xác nhận hoàn thành
                        bottomSheetDialog.dismiss();
                    }
                });

                btnPause.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Tạm dừng công việc
                        bottomSheetDialog.dismiss();
                    }
                });

                btnReject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Từ chối công việc
                        bottomSheetDialog.dismiss();
                    }
                });

                btnDeleteTask.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Xóa công việc
                        bottomSheetDialog.dismiss();
                    }
                });

                btnEditExtend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Sửa hoặc gia hạn công việc
                        bottomSheetDialog.dismiss();
                    }
                });

                btnHistory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Xem lịch sử công việc
                        bottomSheetDialog.dismiss();
                    }
                });
            }
}