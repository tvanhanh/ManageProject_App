package com.example.do_an_cs3.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.do_an_cs3.Adapter.DeparmentAdapter;
import com.example.do_an_cs3.Adapter.ViewPagerAdapter;
import com.example.do_an_cs3.Model.Deparments;
import com.example.do_an_cs3.R;
import com.example.do_an_cs3.View.Job.AddJobActivity;
import com.example.do_an_cs3.View.Job.JobActivity;
import com.example.do_an_cs3.View.Users.PersonnalActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
        private TabLayout mTablayout;
        private ViewPager mViewPager;
        private BottomNavigationView bottomNavigationView;
        private RecyclerView rcv_deparment;
        private DeparmentAdapter deparmentAdapter;
        private Button WarningButton;
       // private Piechar pieChart;

        @SuppressLint("MissingInflatedId")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            mTablayout = findViewById(R.id.tab_layout);
            mViewPager = findViewById(R.id.viewpager_2);

            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            mViewPager.setAdapter(viewPagerAdapter);
            mTablayout.setupWithViewPager(mViewPager);


            BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavigation);
            MenuItem homeMenuItem = bottomNavigationView.getMenu().findItem(R.id.home);
            homeMenuItem.setChecked(true);


            // Tìm và gán MenuItem tương ứng với "job"
            MenuItem JobMenuItem = bottomNavigationView.getMenu().findItem(R.id.job);

            // Thiết lập nghe sự kiện khi menu "home" được chọn
            JobMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    // Nếu đang ở trong JobActivity, chuyển đến MainActivity
                    if (MainActivity.this instanceof MainActivity) {
                        Intent jobIntent = new Intent(MainActivity.this, JobActivity.class);
                        startActivity(jobIntent);
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
                    if (MainActivity.this instanceof MainActivity) {
                        Intent jobIntent = new Intent(MainActivity.this, SettingActivity.class);
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
                    if (MainActivity.this instanceof MainActivity) {
                        Intent addjobIntent = new Intent(MainActivity.this, AddJobActivity.class);
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
                    if (MainActivity.this instanceof MainActivity) {
                        Intent perIntent = new Intent(MainActivity.this, PersonnalActivity.class);
                        startActivity(perIntent);
                    }
                    return true;
                }
            });

            rcv_deparment = findViewById(R.id.rcv_deparment);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
            rcv_deparment.setLayoutManager(linearLayoutManager);

            // Khởi tạo dữ liệu giả định
            List<Deparments> deparmentsList = createDummyData();

            // Khởi tạo và thiết lập adapter
            deparmentAdapter = new DeparmentAdapter(deparmentsList);
            rcv_deparment.setAdapter(deparmentAdapter);

            // button warning
            WarningButton = findViewById(R.id.buttonWarning);
            WarningButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,WarningActivity.class);
                    startActivity(intent);
                }
            });

        }

        // Phương thức giả lập dữ liệu
        private List<Deparments> createDummyData() {
            List<Deparments> dummyData = new ArrayList<>();
            dummyData.add(new Deparments("Department 1", "50%", "10%"));
            dummyData.add(new Deparments("Department 2", "60%", "20%"));
            dummyData.add(new Deparments("Department 3", "70%", "30%"));
            return dummyData;
        }




    }

