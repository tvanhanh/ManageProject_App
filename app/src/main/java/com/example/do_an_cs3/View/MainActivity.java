package com.example.do_an_cs3.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.do_an_cs3.Adapter.DepartmentAdapter;
import com.example.do_an_cs3.Adapter.ViewPagerAdapterHome;

import com.example.do_an_cs3.Database.DatabaseFirebaseManager;

import com.example.do_an_cs3.LoadingDialogFragment;
import com.example.do_an_cs3.Model.Deparments;
import com.example.do_an_cs3.Model.Project;
import com.example.do_an_cs3.R;
import com.example.do_an_cs3.View.Project.AddProjectActivity;
import com.example.do_an_cs3.View.Project.ProjectActivity;
import com.example.do_an_cs3.View.Project.SearchProjectActivity;
import com.example.do_an_cs3.View.Users.PersonnalActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private TabLayout mTablayout;
    private ViewPager mViewPager;
    private String encodedEmail;
    public int daDaThucHien;
    private Button btnSearch;
    private BottomNavigationView bottomNavigationView;
    private RecyclerView rcv_deparment;
    private DepartmentAdapter deparmentAdapter;
    private Button WarningButton;
    private CircleImageView circleImageView;
    private TextView tvUserName;
    private TextView tvPosision;
    private TextView tvTotalProject;

    private LoadingDialogFragment loadingDialog;

    private DatabaseReference userRef;
    private int totalProjects = 0;


    public MainActivity() {
    }


    private DatabaseFirebaseManager dbFBManager;
    private AnyChartView anyChartView;
    private static final int ADD_PROJECT_REQUEST_CODE = 1;
    private String lastLoggedInEmail;
    private String[] statusTask = {"Dự án mới", "Đang thực hiện", "Chờ duyệt", "Hoàn thành", "Tạm dừng"};
    //private int[] quantityProjects = new int[statusTask.length];
    private int[] quantityTasks = new int[statusTask.length];

    public String getCurrentUserEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", null);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anyChartView = findViewById(R.id.anyChartView);
        dbFBManager = new DatabaseFirebaseManager();
        mTablayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.viewpager_2);

        //thống kê
        for (int i = 0; i < 5; i++) {
            //quantityTasks[i]=dbManager.getTotalOngoingProjects(getCurrentUserEmail(),statusTask[i]);
        }
        ViewPagerAdapterHome viewPagerAdapter = new ViewPagerAdapterHome(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(viewPagerAdapter);
        mTablayout.setupWithViewPager(mViewPager);

        tvUserName = findViewById(R.id.userName);
        tvPosision = findViewById(R.id.position);
        circleImageView = findViewById(R.id.circleImageViewMain);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavigation);
        MenuItem homeMenuItem = bottomNavigationView.getMenu().findItem(R.id.home);
        homeMenuItem.setChecked(true);

        MenuItem jobMenuItem = bottomNavigationView.getMenu().findItem(R.id.job);
        jobMenuItem.setOnMenuItemClickListener(item -> {
            Intent jobIntent = new Intent(MainActivity.this, ProjectActivity.class);
            startActivity(jobIntent);
            return true;
        });

        MenuItem settingMenuItem = bottomNavigationView.getMenu().findItem(R.id.setting);
        settingMenuItem.setOnMenuItemClickListener(item -> {
            Intent settingIntent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(settingIntent);
            return true;
        });

        MenuItem addJobMenuItem = bottomNavigationView.getMenu().findItem(R.id.add_job);
        addJobMenuItem.setOnMenuItemClickListener(item -> {
            Intent addJobIntent = new Intent(MainActivity.this, AddProjectActivity.class);
            startActivity(addJobIntent);
            return true;
        });

        MenuItem perMenuItem = bottomNavigationView.getMenu().findItem(R.id.personnal);
        perMenuItem.setOnMenuItemClickListener(item -> {
            Intent perIntent = new Intent(MainActivity.this, PersonnalActivity.class);
            startActivity(perIntent);
            return true;
        });
        btnSearch = findViewById(R.id.buttonSeacrh);
        btnSearch.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchProjectActivity.class);
            startActivity(intent);
        });

        rcv_deparment = findViewById(R.id.rcv_deparment);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rcv_deparment.setLayoutManager(linearLayoutManager);

        List<Deparments> deparmentsList = createDummyData();
        deparmentAdapter = new DepartmentAdapter(deparmentsList, this);

        rcv_deparment.setAdapter(deparmentAdapter);

        WarningButton = findViewById(R.id.buttonWarning);
        WarningButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WarningActivity.class);
            startActivity(intent);
        });
        setupChartView();
        tvTotalProject = findViewById(R.id.tvTotalProject);

        displayUserInfo();
    }
    private void setupChartView() {
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();
        String currentUserEmail = getCurrentUserEmail();

        // Sử dụng AtomicInteger để đếm số lượng lời gọi thành công
        AtomicInteger successCount = new AtomicInteger(0);

        // Vòng lặp để lấy dữ liệu từ Firebase
        for (String status : statusTask) {
            dbFBManager.getProjectsByEmail(currentUserEmail, new DatabaseFirebaseManager.GetProjectsByEmailListener() {
                @Override
                public void onGetProjectsByEmailSuccess(List<String> projectIds) {
                    dbFBManager.getTotalProjectsWithStatus(projectIds, status, new DatabaseFirebaseManager.GetTotalProjectsWithStatusListener() {
                        @Override
                        public void onGetTotalProjectsWithStatusSuccess(int count) {
                            totalProjects = totalProjects + count;
                            // Thêm DataEntry vào danh sách
                            String countchuThich = String.valueOf(count);
                            dataEntries.add(new ValueDataEntry(countchuThich + " " + status, count));

                            // Tăng số lượng lời gọi thành công
                            successCount.incrementAndGet();

                            // Nếu đã hoàn thành lấy dữ liệu cho tất cả status, cập nhật biểu đồ
                            if (successCount.get() == statusTask.length) {
                                // Cài đặt dữ liệu cho biểu đồ
                                pie.data(dataEntries);
                                pie.legend()
                                        .position("right")
                                        .itemsLayout("vertical")
                                        .align("center");
                                pie.background().fill("#E8DFCA"); // Đổi màu nền thành màu trắng (#FFFFFF)
                                anyChartView.setChart(pie);
                                runOnUiThread(() -> {
                                    String stotalProjects = String.valueOf(totalProjects);
                                    tvTotalProject.setText("Tổng dự án là : " + stotalProjects);
                                });
                            }
                        }
                        @Override
                        public void onGetTotalProjectsWithStatusFailure(String error) {
                            Log.e("MainActivity", "Failed to get total projects with status " + status + ": " + error);
                        }
                    });
                }

                @Override
                public void onGetProjectsByEmailFailure(String errorMessage) {
                    Log.e("MainActivity", "Failed to get projects by email: " + errorMessage);
                }
            });
        }
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
                        String position = dataSnapshot.child("role").getValue(String.class);
                        dbFBManager.loadImageFromFirebase(encodedEmail, MainActivity.this, circleImageView);
                        // Hiển thị dữ liệu trong TextView
                        tvUserName.setText(userName);
                        tvPosision.setText(position);
                    } else {
                        // Xử lý trường hợp không có dữ liệu
                        Toast.makeText(MainActivity.this, "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Xử lý khi có lỗi xảy ra trong quá trình đọc dữ liệu từ Firebase
                    Log.e("FirebaseDatabase", "Failed to read user data", databaseError.toException());
                    Toast.makeText(MainActivity.this, "Đã xảy ra lỗi khi đọc dữ liệu từ Firebase", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(MainActivity.this, "Không tìm thấy email người dùng", Toast.LENGTH_SHORT).show();
        }
    }



    private List<Deparments> createDummyData() {
        List<Deparments> dummyData = new ArrayList<>();
//        dummyData.add(new Deparments("Department 1", "50%", "10%"));
//        dummyData.add(new Deparments("Department 2", "60%", "20%"));
//        dummyData.add(new Deparments("Department 3", "70%", "30%"));
        return dummyData;
    }


}