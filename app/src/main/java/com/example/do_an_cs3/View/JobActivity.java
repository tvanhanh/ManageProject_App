package com.example.do_an_cs3.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.do_an_cs3.Adapter.ViewPagerAdapterJob;
import com.example.do_an_cs3.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class JobActivity extends AppCompatActivity {
    private TabLayout ntabLayout;
    private ViewPager nviewpager;
    private Button buttoncomback;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        ntabLayout = findViewById(R.id.tabLayout);
        nviewpager = findViewById(R.id.viewpager);
        ViewPagerAdapterJob viewPagerAdapter = new ViewPagerAdapterJob(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        nviewpager.setAdapter(viewPagerAdapter);
        ntabLayout.setupWithViewPager(nviewpager);




        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavigation);
        MenuItem jobMenuItem = bottomNavigationView.getMenu().findItem(R.id.job);
        jobMenuItem.setChecked(true);

        // Thiết lập nghe sự kiện cho BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                Intent homeIntent = new Intent(JobActivity.this, MainActivity.class);
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
                if (JobActivity.this instanceof JobActivity) {
                    Intent homeIntent = new Intent(JobActivity.this, MainActivity.class);
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
                if (JobActivity.this instanceof JobActivity) {
                    Intent jobIntent = new Intent(JobActivity.this, SettingActivity.class);
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
                if (JobActivity.this instanceof JobActivity) {
                    Intent addjobIntent = new Intent(JobActivity.this, AddJobActivity.class);
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
                if (JobActivity.this instanceof JobActivity) {
                    Intent perIntent = new Intent(JobActivity.this, PersonnalActivity.class);
                    startActivity(perIntent);
                }
                return true;
            }
        });

        buttoncomback = findViewById(R.id.buttonComback);
        buttoncomback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}


