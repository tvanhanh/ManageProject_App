package com.example.do_an_cs3.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.do_an_cs3.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingActivity extends AppCompatActivity {
    private TextView editTextAccount;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavigation);
        MenuItem settingMenuItem = bottomNavigationView.getMenu().findItem(R.id.setting);
        settingMenuItem.setChecked(true);


        // sử tài khoản
        editTextAccount = findViewById(R.id.editaccount);
        editTextAccount.setOnClickListener(v -> {
            Intent intent = new Intent(SettingActivity.this,EditAccountActivity.class);
            startActivity(intent);
        });

        // Thiết lập nghe sự kiện cho BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                Intent homeIntent = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(homeIntent);
                return true;
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
                    Intent jobIntent = new Intent(SettingActivity.this, JobActivity.class);
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
                    Intent addjobIntent = new Intent(SettingActivity.this, AddJobActivity.class);
                    startActivity(addjobIntent);
                }
                return true;
            }
        });
        MenuItem perMenuItem = bottomNavigationView.getMenu().findItem(R.id.personnal);
        // Thiết lập nghe sự kiện khi menu "home" được chọn
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
    }

    }
