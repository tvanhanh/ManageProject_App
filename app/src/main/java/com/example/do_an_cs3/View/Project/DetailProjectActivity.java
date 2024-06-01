package com.example.do_an_cs3.View.Project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_cs3.Adapter.TaskAdapter;
import com.example.do_an_cs3.Adapter.UserFollowAdapter;
import com.example.do_an_cs3.Database.DatabaseManager;
import com.example.do_an_cs3.Model.Project;
import com.example.do_an_cs3.Model.Task;
import com.example.do_an_cs3.Model.User;
import com.example.do_an_cs3.R;

import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.do_an_cs3.Task.AddTaskActivity;
import com.example.do_an_cs3.View.MainActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailProjectActivity extends AppCompatActivity {
    private RecyclerView rcv_userFollow;
    private RecyclerView rcv_task;
    private UserFollowAdapter userFollowAdapter;
    private TaskAdapter taskAdapter;
    private DatabaseManager dbManager;

    private Button btnAddTask;
    private Button btnBack;
    private TextView userNameDetail;
    private List<Task> taskList;

    private int idProject;
    private TextView emailDetail;
    private CircleImageView circleImageView;
    private CircleImageView circleImageViewWork;
    private TextView tvNameProjet, tvDeadline, tvTimeCreation, tvView, tvStatus, tvConText, tvRole, tvUserNameDetailWord;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_project);
        dbManager = new DatabaseManager(DetailProjectActivity.this);


        Button btnViewMore = findViewById(R.id.btnViewMore);
        btnAddTask = findViewById(R.id.addTask);
        btnBack = findViewById(R.id.btnBack);
        emailDetail = findViewById(R.id.tvEmailDetail);
        userNameDetail = findViewById(R.id.tvUserNameDetail);

        rcv_userFollow = findViewById(R.id.rcv_userFollow);
        rcv_task = findViewById(R.id.rcv_task);

        tvNameProjet = findViewById(R.id.NameProrjectDetail);
        tvStatus = findViewById(R.id.tvStatusDetail);
        tvDeadline = findViewById(R.id.tvDeadlineDetail);
        tvTimeCreation = findViewById(R.id.TimeCreationProjectDetail);
        tvConText = findViewById(R.id.tvprojectContext);
        tvView = findViewById(R.id.tvViews);
        circleImageView =findViewById(R.id.circleImageView);
        circleImageViewWork =findViewById(R.id.circleImageViewWork);
        tvRole = findViewById(R.id.PossitonAndEmail);
        tvUserNameDetailWord = findViewById(R.id.userNameNguoiThucHien);
        emailDetail.setText(getCurrentUserEmail());
        Intent intent = getIntent();
        idProject = intent.getIntExtra("idProject", -1);
        User userdetail = dbManager.getUserInfo(getCurrentUserEmail());
        if (userdetail != null) {
            userNameDetail.setText(userdetail.getUserName());
        }
        // Khởi tạo taskList và taskAdapter ngay cả khi chưa có task
        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList, this);
        rcv_task.setAdapter(taskAdapter);
        Project project = new Project();
        project =  dbManager.getInfoProject(idProject);
        if(project != null)
        {
            tvNameProjet.setText(project.getName());
            tvStatus.setText(project.getStatus());
            tvDeadline.setText(project.getDeadline());
            tvTimeCreation.setText("Ngày tạo: "+project.getCreationTime());
            String view =  String.valueOf( project.getViews() );
            tvView.setText("Lượt xem: " + view);
            tvConText.setText(project.getDescription());
        }



        btnViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog();
            }
        });

        LinearLayoutManager linearLayoutManageruserFollow = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rcv_userFollow.setLayoutManager(linearLayoutManageruserFollow);

        List<User> user = createDummyData();
        userFollowAdapter = new UserFollowAdapter(user);
        rcv_userFollow.setAdapter(userFollowAdapter);

        LinearLayoutManager linearLayoutManagerTask = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcv_task.setLayoutManager(linearLayoutManagerTask);



        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailProjectActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailProjectActivity.this, AddTaskActivity.class);
                intent.putExtra("idProject", idProject);
                startActivity(intent);
            }
        });
        displayUserInfo();
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

    private String getCurrentUserEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", null); // Trả về null nếu không tìm thấy email
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Tải lại danh sách task khi Activity được hiển thị lại
        loadTasks();
    }

    private void loadTasks() {
        taskList.clear();
        taskList.addAll(dbManager.getAllTask(idProject));
        taskAdapter.notifyDataSetChanged();
    }
    public void displayUserInfo() {
        User user = dbManager.getUserInfo(getCurrentUserEmail());
        if (user != null) {
            tvUserNameDetailWord.setText(user.getUserName());
            emailDetail.setText(getCurrentUserEmail());
            tvRole.setText(user.getRole()+" - " + getCurrentUserEmail());
            if (user.getAvatar() != null) {
                byte[] avatarBytes = Base64.decode(user.getAvatar(), Base64.DEFAULT);
                Bitmap avatarBitmap = BitmapFactory.decodeByteArray(avatarBytes, 0, avatarBytes.length);
                circleImageView.setImageBitmap(avatarBitmap);
                circleImageViewWork.setImageBitmap(avatarBitmap);
            }
        }
    }
}
