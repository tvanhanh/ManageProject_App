package com.example.do_an_cs3.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.do_an_cs3.Model.User;
import com.example.do_an_cs3.R;
import com.example.do_an_cs3.View.Project.AddProjectActivity;
import com.example.do_an_cs3.View.Project.ProjectActivity;
import com.example.do_an_cs3.View.Users.ChangePassActivity;
import com.example.do_an_cs3.View.Users.EditAccountActivity;
import com.example.do_an_cs3.View.Users.LoginActivity;
import com.example.do_an_cs3.View.Users.PersonnalActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends AppCompatActivity {
    private TextView editTextAccount;
    private TextView changepass;
    private TextView statistic;
    private Button comback;
    private TextView logout;

    private CircleImageView circleImageView;
    private TextView tvUsername, tvEmail, tvRole;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
       // dbManager = new DatabaseManager(SettingActivity.this);
        tvEmail = findViewById(R.id.emailaccount);
        tvUsername = findViewById(R.id.nameaccount);
        tvRole = findViewById(R.id.chucvu);
        circleImageView = findViewById(R.id.accountAvt);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavigation);
        MenuItem settingMenuItem = bottomNavigationView.getMenu().findItem(R.id.setting);
        settingMenuItem.setChecked(true);

        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
// comback
        comback = findViewById(R.id.buttonComback);
        comback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // sửa tài khoản
        editTextAccount = findViewById(R.id.editaccount);
        editTextAccount.setOnClickListener(v -> {
            Intent intent = new Intent(SettingActivity.this, EditAccountActivity.class);
            startActivity(intent);
        });
        //đổi mật khẩu
        changepass = findViewById(R.id.chanepass);
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, ChangePassActivity.class);
                startActivity(intent);
            }
        });
        //báo cáo thống kê
        statistic = findViewById(R.id.statistic);
        statistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this,ReportActivity.class);
                startActivity(intent);
            }
        });

        // Tìm và gán MenuItem tương ứng với "home"
        MenuItem homeMenuItem = bottomNavigationView.getMenu().findItem(R.id.home);
        // Thiết lập nghe sự kiện khi menu "home" được chọn
        homeMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Nếu đang ở trong JobActivity, chuyển đến MainActivity
                if (SettingActivity.this instanceof SettingActivity) {
                    Intent homeIntent = new Intent(SettingActivity.this, MainActivity.class);
                    startActivity(homeIntent);
                }
                return true;
            }
        });
        MenuItem jobMenuItem = bottomNavigationView.getMenu().findItem(R.id.job);
        // Thiết lập nghe sự kiện khi menu "home" được chọn
        jobMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Nếu đang ở trong JobActivity, chuyển đến JobActivity
                if (SettingActivity.this instanceof SettingActivity) {
                    Intent jobIntent = new Intent(SettingActivity.this, ProjectActivity.class);
                    startActivity(jobIntent);
                }
                return true;
            }
        });
        MenuItem addiobMenuItem = bottomNavigationView.getMenu().findItem(R.id.add_job);
        // Thiết lập nghe sự kiện khi menu "home" được chọn
        addiobMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Nếu đang ở trong JobActivity, chuyển đến AddJobActivity
                if (SettingActivity.this instanceof SettingActivity) {
                    Intent addjobIntent = new Intent(SettingActivity.this, AddProjectActivity.class);
                    startActivity(addjobIntent);
                }
                return true;
            }
        });
        MenuItem perMenuItem = bottomNavigationView.getMenu().findItem(R.id.personnal);
        // Thiết lập nghe sự kiện khi menu "personal" được chọn
        perMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Nếu đang ở trong JobActivity, chuyển đến AddJobActivity
                if (SettingActivity.this instanceof SettingActivity) {
                    Intent perIntent = new Intent(SettingActivity.this, PersonnalActivity.class);
                    startActivity(perIntent);
                }
                return true;
            }
        });
        displayUserInfo();
    }
    public void displayUserInfo() {
//        User user = dbManager.getUserInfo(getCurrentUserEmail());
//        if (user != null) {
//            tvUsername.setText(user.getUserName());
//            tvEmail.setText(getCurrentUserEmail());
//            tvRole.setText(user.getRole());
//            if (user.getAvatar() != null) {
//                byte[] avatarBytes = Base64.decode(user.getAvatar(), Base64.DEFAULT);
//                Bitmap avatarBitmap = BitmapFactory.decodeByteArray(avatarBytes, 0, avatarBytes.length);
//                circleImageView.setImageBitmap(avatarBitmap);
//            }
//        }
    }
    public String getCurrentUserEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", null);
    }
    }
