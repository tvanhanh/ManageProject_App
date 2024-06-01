package com.example.do_an_cs3.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
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
import com.example.do_an_cs3.Database.Database;
import com.example.do_an_cs3.Database.DatabaseManager;
import com.example.do_an_cs3.Model.Deparments;
import com.example.do_an_cs3.Model.User;
import com.example.do_an_cs3.R;
import com.example.do_an_cs3.Task.AddTaskActivity;
import com.example.do_an_cs3.View.Project.AddProjectActivity;
import com.example.do_an_cs3.View.Project.DetailProjectActivity;
import com.example.do_an_cs3.View.Project.ProjectActivity;
import com.example.do_an_cs3.View.Users.PersonnalActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private TabLayout mTablayout;
    private ViewPager mViewPager;
    public int daDaThucHien;
    private BottomNavigationView bottomNavigationView;
    private RecyclerView rcv_deparment;
    private DepartmentAdapter deparmentAdapter;
    private Button WarningButton;
    private CircleImageView circleImageView;
    private TextView tvUserName;
    private TextView tvPosision;

    public MainActivity() {
    }

    private Database dbhelper;
    private DatabaseManager dbManager;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anyChartView = findViewById(R.id.anyChartView);

        dbhelper = new Database(this);
        dbManager = new DatabaseManager(this);
        mTablayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.viewpager_2);

        //thống kê
        for (int i = 0; i <5 ;i++){
            quantityTasks[i]=dbManager.getTotalOngoingProjects(getCurrentUserEmail(),statusTask[i]);
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

        rcv_deparment = findViewById(R.id.rcv_deparment);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rcv_deparment.setLayoutManager(linearLayoutManager);

        List<Deparments> deparmentsList = createDummyData();
        deparmentAdapter = new DepartmentAdapter(deparmentsList);
        rcv_deparment.setAdapter(deparmentAdapter);

        WarningButton = findViewById(R.id.buttonWarning);
        WarningButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WarningActivity.class);
            startActivity(intent);
        });
        setupChartView();
        displayUserInfo();
    }
    private void setupChartView() {
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();

        for (int i = 0; i < statusTask.length; i++) {
            String quatityTask = String.valueOf(quantityTasks[i]);
            dataEntries.add(new ValueDataEntry( quatityTask +" "+ statusTask[i], quantityTasks[i]));
        }
        pie.data(dataEntries);
        pie.legend()
                .position("right")
                .itemsLayout("vertical")
                .align("center");
        pie.background().fill("#E8DFCA"); // Đổi màu nền thành màu trắng (#FFFFFF)
        anyChartView.setChart(pie);
    }
//    private void setupChartView() {
//        Pie pie = AnyChart.pie();
//        List<DataEntry> dataEntries = new ArrayList<>();
//        String currentUserEmail = getCurrentUserEmail();
//        for (String status : statusTask) {
//            int totalProjectsByStatus = dbManager.getTotalOngoingProjects(currentUserEmail, status);
//            dataEntries.add(new ValueDataEntry(status, totalProjectsByStatus));
////        }
////        for (int i = 0; i < statusTask.length; i++) {
////            quantityProjects[i] = dbManager.getTotalOngoingProjects(email, statusTask[i]);
//        }
////        for (int i = 0; i < statusTask.length; i++) {
////            String quatityTask = String.valueOf(quantityTasks[i]);
////            dataEntries.add(new ValueDataEntry( quatityTask +" "+ statusTask[i], quantityTasks[i]));
////        }
//        pie.data(dataEntries);
//        pie.legend()
//                .position("right")
//                .itemsLayout("vertical")
//                .align("center");
//        pie.background().fill("#E8DFCA"); // Đổi màu nền thành màu trắng (#FFFFFF)
//        anyChartView.setChart(pie);
//    }


    public void displayUserInfo() {
        User user = dbManager.getUserInfo(getCurrentUserEmail());
        if (user != null) {
            tvUserName.setText(user.getUserName());
            tvPosision.setText(user.getRole());
            if (user.getAvatar() != null) {
                byte[] avatarBytes = Base64.decode(user.getAvatar(), Base64.DEFAULT);
                Bitmap avatarBitmap = BitmapFactory.decodeByteArray(avatarBytes, 0, avatarBytes.length);
                circleImageView.setImageBitmap(avatarBitmap);
            }
        }
    }

    private List<Deparments> createDummyData() {
        List<Deparments> dummyData = new ArrayList<>();
        dummyData.add(new Deparments("Department 1", "50%", "10%"));
        dummyData.add(new Deparments("Department 2", "60%", "20%"));
        dummyData.add(new Deparments("Department 3", "70%", "30%"));
        return dummyData;
    }

//    @SuppressLint("Range")
//    private User getUserInfo() {
//        User user = new User();
//        SQLiteDatabase db = null;
//        Cursor cursor = null;
//        try {
//            db = dbhelper.getReadableDatabase();
//            cursor = db.rawQuery("SELECT * FROM Users", null);
//            if (cursor.moveToFirst()) {
//                String userName = cursor.getString(cursor.getColumnIndex("username"));
//                String phoneNumber = cursor.getString(cursor.getColumnIndex("phone_number"));
//                String address = cursor.getString(cursor.getColumnIndex("address"));
//                String referralCode = cursor.getString(cursor.getColumnIndex("referral_code"));
//                byte[] avatarBytes = cursor.getBlob(cursor.getColumnIndex("avatar_url"));
//                int department = cursor.getInt(cursor.getColumnIndex("department_id"));
//                String role = cursor.getString(cursor.getColumnIndex("role"));
//
//                String avatar = (avatarBytes != null) ? Base64.encodeToString(avatarBytes, Base64.DEFAULT) : null;
//
//                user = new User(userName, phoneNumber, address, referralCode, avatar, department, role);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//            if (db != null) {
//                db.close();
//            }
//        }
//        return user;
//    }
    // thống kê

}
