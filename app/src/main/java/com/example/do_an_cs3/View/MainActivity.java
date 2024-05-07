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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.do_an_cs3.Ardapter.DeparmentAdapter;
import com.example.do_an_cs3.Ardapter.ViewPagerAdapter;
import com.example.do_an_cs3.Model.Deparments;
import com.example.do_an_cs3.R;
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


            bottomNavigationView = findViewById(R.id.bottomnavigation);
           // bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
               bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        int itemId = item.getItemId();
                        if (itemId == R.id.home) {
                            Intent homeIntent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(homeIntent);
                            return true;
                        } else if (itemId == R.id.job) {
                            Intent jobIntent = new Intent(MainActivity.this, JobActivity.class);
                            startActivity(jobIntent);
                            return true;
                        } else if (itemId == R.id.add_job) {
                            Intent addJobIntent = new Intent(MainActivity.this, AddJobActivity.class);
                            startActivity(addJobIntent);
                            return true;
                        } else if (itemId == R.id.personnal) {
                            Intent personalIntent = new Intent(MainActivity.this, PersonnalActivity.class);
                            startActivity(personalIntent);
                            return true;
                        } else if (itemId == R.id.setting) {
                            Intent settingIntent = new Intent(MainActivity.this, SettingActivity.class);
                            startActivity(settingIntent);
                            return true;
                        }
                        return false;
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

