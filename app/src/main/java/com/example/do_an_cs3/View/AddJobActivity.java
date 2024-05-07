package com.example.do_an_cs3.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.do_an_cs3.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AddJobActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addjob);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavigation);
        MenuItem jobaddMenuItem = bottomNavigationView.getMenu().findItem(R.id.add_job);
        jobaddMenuItem.setChecked(true);

        // Thiết lập nghe sự kiện cho BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                Intent homeIntent = new Intent(AddJobActivity.this, MainActivity.class);
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
                if (AddJobActivity.this instanceof AddJobActivity) {
                    Intent homeIntent = new Intent(AddJobActivity.this, MainActivity.class);
                    startActivity(homeIntent);
                }
                return true;
            }
        });
        MenuItem settingMenuItem = bottomNavigationView.getMenu().findItem(R.id.setting);
        // Thiết lập nghe sự kiện khi menu "home" được chọn
        settingMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Nếu đang ở trong JobActivity, chuyển đến settingActivity
                if (AddJobActivity.this instanceof AddJobActivity) {
                    Intent jobIntent = new Intent(AddJobActivity.this, SettingActivity.class);
                    startActivity(jobIntent);
                }
                return true;
            }
        });
        MenuItem jobMenuItem = bottomNavigationView.getMenu().findItem(R.id.job);
        // Thiết lập nghe sự kiện khi menu "home" được chọn
        jobMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Nếu đang ở trong JobActivity, chuyển đến AddJobActivity
                if (AddJobActivity.this instanceof AddJobActivity) {
                    Intent jobIntent = new Intent(AddJobActivity.this, JobActivity.class);
                    startActivity(jobIntent);
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
                if (AddJobActivity.this instanceof AddJobActivity) {
                    Intent perIntent = new Intent(AddJobActivity.this, PersonnalActivity.class);
                    startActivity(perIntent);
                }
                return true;
            }
        });
    }

}


