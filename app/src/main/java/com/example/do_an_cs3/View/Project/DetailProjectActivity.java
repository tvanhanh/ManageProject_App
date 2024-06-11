package com.example.do_an_cs3.View.Project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_cs3.Adapter.TaskAdapter;
import com.example.do_an_cs3.Adapter.UserFollowAdapter;

import com.example.do_an_cs3.Adapter.UserWorkInProjectAdapter;
import com.example.do_an_cs3.Database.DatabaseFirebaseManager;
import com.example.do_an_cs3.Model.Project;
import com.example.do_an_cs3.Model.Task;
import com.example.do_an_cs3.Model.User;
import com.example.do_an_cs3.R;

import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.do_an_cs3.Task.AddTaskActivity;
import com.example.do_an_cs3.View.MainActivity;
import com.example.do_an_cs3.View.Users.PersonnalActivity;
import com.example.do_an_cs3.View.back_end.View_fragment.FragmentHome.UpdateNewFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailProjectActivity extends AppCompatActivity {
    private RecyclerView rcv_userFollow;
    private RecyclerView rcv_task;
    private UserFollowAdapter userFollowAdapter;
    private TaskAdapter taskAdapter;

    private RecyclerView rcv_user_work;
    private UserWorkInProjectAdapter userWorkInProjectAdapter;

    private UpdateNewFragment updateNewFragment;

    private Button btnAddTask;
    private Button btnBack;
    private Button btnAddStaff;
    private TextView userNameDetail;
    private List<Task> taskList;
    private List<User> userWorkList;
    private DatabaseFirebaseManager dbManager;

    private String idProject;

    public DetailProjectActivity() {
    }

    public String getIdProject() {
        return idProject;
    }



    private String nameProject;
    private TextView emailDetail;
    private CircleImageView circleImageView;
    private CircleImageView circleImageViewWork;
    private TextView tvNameProjet, tvDeadline, tvTimeCreation, tvView, tvStatus, tvConText, tvRole, tvUserNameDetailWord;
    private Button btnDelete, btnEdit, btnHistory;
    private LinearLayout lnShare, lnXacNhanHoanThanh, lnPause, lnTuChoi;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://manageproject-7a9ac-default-rtdb.firebaseio.com/");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_project);
        //dbManager = new DatabaseManager(DetailProjectActivity.this);
        updateNewFragment = new UpdateNewFragment();

        dbManager = new DatabaseFirebaseManager();
        Button btnViewMore = findViewById(R.id.btnViewMore);
        btnAddTask = findViewById(R.id.addTask);
        btnBack = findViewById(R.id.btnBack);

        rcv_user_work = findViewById(R.id.rcv_staff_work);
        rcv_userFollow = findViewById(R.id.rcv_userFollow);
        rcv_task = findViewById(R.id.rcv_task);
        //Thông tin project
        tvNameProjet = findViewById(R.id.NameProrjectDetail);
        tvStatus = findViewById(R.id.tvStatusDetail);
        tvDeadline = findViewById(R.id.tvDeadlineDetail);
        tvTimeCreation = findViewById(R.id.TimeCreationProjectDetail);
        tvConText = findViewById(R.id.tvprojectContext);
        tvView = findViewById(R.id.tvViews);

        //thông tin user
        circleImageView =findViewById(R.id.circleImageView);
        circleImageViewWork =findViewById(R.id.circleImageViewWork);
        tvRole = findViewById(R.id.PossitonAndEmail);
        tvUserNameDetailWord = findViewById(R.id.userNameWork);
        emailDetail = findViewById(R.id.tvEmailDetail);
        userNameDetail = findViewById(R.id.tvUserNameDetail);


        emailDetail.setText(getCurrentUserEmail());
        Intent intent = getIntent();
        idProject = intent.getStringExtra("idProject");
        nameProject = intent.getStringExtra("projectName");
        User userdetail = null;
                //dbManager.getUserInfo(getCurrentUserEmail());
        if (userdetail != null) {
            userNameDetail.setText(userdetail.getUserName());
        }
        // Khởi tạo taskList và taskAdapter ngay cả khi chưa có task
        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList, this);
        rcv_task.setAdapter(taskAdapter);
        updateInfoProject();
        userWorkList = new ArrayList<>();
        userWorkInProjectAdapter = new UserWorkInProjectAdapter(userWorkList,this);
        rcv_user_work.setAdapter(userWorkInProjectAdapter);
        getUsersWorkInProject(idProject);
        btnViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog();
            }
        });
        LinearLayoutManager linearLayoutManagerUserWork = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcv_user_work.setLayoutManager(linearLayoutManagerUserWork);
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
        btnAddStaff = findViewById(R.id.btn_add_staff_work);
        btnAddStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailProjectActivity.this, PersonnalActivity.class);
                intent.putExtra("idProject", idProject);
                startActivity(intent);
            }
        });
//        btnAddTask.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(DetailProjectActivity.this, AddTaskActivity.class);
//                intent.putExtra("idProject", idProject);
//                startActivity(intent);
//            }
//        });
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

    @SuppressLint("WrongViewCast")
    private void showBottomSheetDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog, null);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

        // Xử lý sự kiện cho các nút trong BottomSheetDialog
        lnShare = bottomSheetView.findViewById(R.id.lnShare);
        lnXacNhanHoanThanh = bottomSheetView.findViewById(R.id.lnXacNhanHoanThanh);
        lnPause = bottomSheetView.findViewById(R.id.lnPause);
        lnTuChoi = bottomSheetView.findViewById(R.id.lnTuChoi);
        btnDelete = bottomSheetView.findViewById(R.id.btnDeleteProject);
        btnEdit = bottomSheetView.findViewById(R.id.btnEditExtend);
        btnHistory = bottomSheetView.findViewById(R.id.btnHistory);

        // Thiết lập các sự kiện click cho các nút
        lnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý chia sẻ công việc
                bottomSheetDialog.dismiss();
            }
        });

        lnXacNhanHoanThanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dbManager.updateStatusProject("Hoàn thành", idProject);
                Toast.makeText(DetailProjectActivity.this, "Dự án đã hoàn thành", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
                updateInfoProject();
            }
        });

        lnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dbManager.updateStatusProject("Tạm dừng", idProject);
                Toast.makeText(DetailProjectActivity.this, "Đã tạm dừng dự án", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
                updateInfoProject();

            }
        });

        lnTuChoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Từ chối công việc
                bottomSheetDialog.dismiss();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailProjectActivity.this);
                builder.setMessage("Bạn có chắc chắn muốn xóa dự án này không?")
                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Xử lý khi người dùng chọn xóa
                                if (true) {
                                   // updateNewFragment.updateRecyclerView();
                                    Intent intent = new Intent(DetailProjectActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(DetailProjectActivity.this, "Xóa dự án " + nameProject + " thành công", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(DetailProjectActivity.this, "Xóa dự án " + nameProject + " không thành công", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Không xóa", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Xử lý khi người dùng chọn không xóa
                                dialog.dismiss();
                            }
                        });
                // Tạo và hiển thị AlertDialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                // Đóng bottomSheetDialog
                bottomSheetDialog.dismiss();
            }
        });


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sửa hoặc gia hạn công việc
                Intent intent = new Intent(DetailProjectActivity.this,EditProjectActivity.class);
                intent.putExtra("idProject", idProject);
                startActivity(intent);
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
     //   loadTasks();
    }

//    private void loadTasks() {
//        taskList.clear();
//        taskList.addAll(dbManager.getAllTask(idProject));
//        taskAdapter.notifyDataSetChanged();
//    }
    public void displayUserInfo() {
//        User user = dbManager.getUserInfo(getCurrentUserEmail());
//        if (user != null) {
//            tvUserNameDetailWord.setText(user.getUserName());
//            emailDetail.setText(getCurrentUserEmail());
//            tvRole.setText(user.getRole()+" - " + getCurrentUserEmail());
//            if (user.getAvatar() != null) {
//                byte[] avatarBytes = Base64.decode(user.getAvatar(), Base64.DEFAULT);
//                Bitmap avatarBitmap = BitmapFactory.decodeByteArray(avatarBytes, 0, avatarBytes.length);
//                circleImageView.setImageBitmap(avatarBitmap);
//                circleImageViewWork.setImageBitmap(avatarBitmap);
    //        }
    //    }
    }
    public  void updateInfoProject(){
 //       Project project =  dbManager.getInfoProject(idProject);
//        if(project != null)
//        {
//            tvNameProjet.setText(project.getName());
//            tvStatus.setText(project.getStatus());
//            tvDeadline.setText(project.getDeadline());
//            tvTimeCreation.setText("Ngày tạo: "+project.getCreationTime());
//            String view =  String.valueOf( project.getViews() );
//            tvView.setText( view);
//            tvConText.setText(project.getDescription());
//        }
}
    public void getUsersWorkInProject(String projectId) {
        dbManager.getUsersByProjectId(projectId, new DatabaseFirebaseManager.GetUsersByProjectIdListener() {
            @Override
            public void onGetUsersByProjectIdSuccess(List<String> userIds) {
                getUsers(userIds);
            }

            @Override
            public void onGetUsersByProjectIdFailure(String errorMessage) {
                // Xử lý khi không thể lấy được danh sách user làm việc trong dự án
                Toast.makeText(DetailProjectActivity.this, "Failed to get users working in project: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getUsers(List<String> userIds) {
        dbManager.getUsers(userIds, new DatabaseFirebaseManager.GetUsersListener() {
            @Override
            public void onGetUsersSuccess(List<User> users) {
                // Hiển thị danh sách người dùng làm việc trong dự án
                displayUsersWork(users);
            }

            @Override
            public void onGetUsersFailure(String errorMessage) {
                // Xử lý khi không thể lấy được thông tin của các người dùng
                Toast.makeText(DetailProjectActivity.this, "Failed to get users: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void displayUsersWork(List<User> users) {
        userWorkList.clear();
        userWorkList.addAll(users);
        userWorkInProjectAdapter.notifyDataSetChanged();
    }


}
