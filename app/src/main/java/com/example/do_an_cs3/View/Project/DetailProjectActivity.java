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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_cs3.Adapter.TaskAdapter;
import com.example.do_an_cs3.Adapter.UserFollowAdapter;

import com.example.do_an_cs3.Database.DatabaseFirebaseManager;
import com.example.do_an_cs3.Model.Project;
import com.example.do_an_cs3.Model.Task;
import com.example.do_an_cs3.Model.User;
import com.example.do_an_cs3.R;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.do_an_cs3.Task.AddTaskActivity;
import com.example.do_an_cs3.View.MainActivity;
import com.example.do_an_cs3.View.Users.EditAccountActivity;
import com.example.do_an_cs3.View.back_end.View_fragment.FragmentHome.UpdateNewFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailProjectActivity extends AppCompatActivity {
    private RecyclerView rcv_userFollow;
    private RecyclerView rcv_task;
    private UserFollowAdapter userFollowAdapter;
    private TaskAdapter taskAdapter;

    private UpdateNewFragment updateNewFragment;

    private Button btnAddTask;
    private String encodedEmail;
    private TextView namproject;
    private TextView timeCreationProjectDetail;
    private TextView tvUserNameDetail;
    private Button btnBack;
    private List<Task> taskList;
    private DatabaseReference userRef;

    private String idProject;
    private DatabaseFirebaseManager dbFBManager;
    private String taskId;

    public DetailProjectActivity() {
    }

    public String getIdProject() {
        return idProject;
    }

    public String getStatus() {
        return taskId;
    }


    private String nameProject;
    private TextView emailDetail;
    private CircleImageView circleImageView;
    private CircleImageView circleImageViewWork;
    private TextView tvNameProjet, tvDeadline, tvTimeCreation, tvView, tvStatus, tvConText, tvRole, tvUserNameDetailWord;
    private Button btnDelete, btnEdit, btnHistory;
    private DatabaseReference databaseTasks;
    private LinearLayout lnShare, lnXacNhanHoanThanh, lnPause, lnTuChoi;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://manageproject-7a9ac-default-rtdb.firebaseio.com/");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_project);
        dbFBManager = new DatabaseFirebaseManager();
        updateNewFragment = new UpdateNewFragment();
        Intent intent = getIntent();

        idProject = intent.getStringExtra("idProject");
        if (idProject == null) {
            // Nếu idProject bị null, hiển thị thông báo hoặc thực hiện xử lý phù hợp
            Toast.makeText(this, "Không có ID dự án được truyền", Toast.LENGTH_SHORT).show();
            finish(); // Kết thúc Activity
            return; // Kết thúc phương thức onCreate để tránh tiếp tục thực hiện mã bị lỗi
        }
        databaseTasks = FirebaseDatabase.getInstance().getReference("tasks");

        Button btnViewMore = findViewById(R.id.btnViewMore);
        btnAddTask = findViewById(R.id.addTask);
        btnBack = findViewById(R.id.btnBack);
        namproject = findViewById(R.id.NameProrjectDetail);
        tvUserNameDetail = findViewById(R.id.tvUserNameDetail);
        emailDetail = findViewById(R.id.tvEmailDetail);
        timeCreationProjectDetail = findViewById(R.id.TimeCreationProjectDetail);
        rcv_userFollow = findViewById(R.id.rcv_userFollow);
circleImageView = findViewById(R.id.circleImageView);

        rcv_task = findViewById(R.id.rcv_task);
        LinearLayoutManager leLinearLayoutManager = new LinearLayoutManager(this);
        rcv_task.setLayoutManager(leLinearLayoutManager);
        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList, this);
        rcv_task.setAdapter(taskAdapter);

        if (idProject != null && !idProject.isEmpty()) {
            getListTask(idProject);
        } else {
            // Handle error
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
                finish();
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
                Intent intent = new Intent(DetailProjectActivity.this, EditProjectActivity.class);
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
//
    public void updateInfoProject() {
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

    private void getListTask(String projectId) {
        DatabaseReference databaseTasks = FirebaseDatabase.getInstance().getReference("tasks");
        databaseTasks.orderByChild("projectId").equalTo(projectId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                taskList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Task task = dataSnapshot.getValue(Task.class);
                    if (task != null) {
                        taskList.add(task);
                    }
                }
                taskAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DetailProjectActivity.this, "Lỗi khi tải danh sách task", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void updateTaskStatus(Context context, String taskId) {
        DatabaseReference taskRef = FirebaseDatabase.getInstance().getReference("tasks").child(taskId);
        taskRef.child("status").setValue("Đã hoàn thành").addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(context, "Task updated successfully.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Failed to update task.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void displayUserInfo() {
        String userEmail = getCurrentUserEmail();
        if (userEmail != null) {
            encodedEmail = userEmail.replace(".", ",");
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            userRef = databaseReference.child("users").child(encodedEmail);


            // Sử dụng ValueEventListener để lấy dữ liệu từ Firebase
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Kiểm tra xem dữ liệu có tồn tại hay không
                    if (dataSnapshot.exists()) {
                        // Lấy dữ liệu từ DataSnapshot và hiển thị nó trong TextView
                        String userName = dataSnapshot.child("userName").getValue(String.class);
                        String email = dataSnapshot.child("email").getValue(String.class);
                        dbFBManager.loadImageFromFirebase(encodedEmail, DetailProjectActivity.this, circleImageView);
                        // Hiển thị dữ liệu trong TextView
                        tvUserNameDetail.setText(userName);
                        emailDetail.setText(email);
                    } else {
                        // Xử lý trường hợp không có dữ liệu
                        Toast.makeText(DetailProjectActivity.this, "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Xử lý khi có lỗi xảy ra trong quá trình đọc dữ liệu từ Firebase
                    Log.e("FirebaseDatabase", "Failed to read user data", databaseError.toException());
                    Toast.makeText(DetailProjectActivity.this, "Đã xảy ra lỗi khi đọc dữ liệu từ Firebase", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(DetailProjectActivity.this, "Không tìm thấy email người dùng", Toast.LENGTH_SHORT).show();
        }

    }
}

