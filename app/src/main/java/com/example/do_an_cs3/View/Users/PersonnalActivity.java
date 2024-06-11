package com.example.do_an_cs3.View.Users;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.viewpager.widget.ViewPager;

import com.example.do_an_cs3.Adapter.ViewPagerAdapterDepartment;
import com.example.do_an_cs3.R;
import com.example.do_an_cs3.View.Project.AddProjectActivity;
import com.example.do_an_cs3.View.Project.ProjectActivity;
import com.example.do_an_cs3.View.MainActivity;
import com.example.do_an_cs3.View.SettingActivity;
import com.example.do_an_cs3.databinding.ActivityPersonnalBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import javax.annotation.Nullable;

public class PersonnalActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private TabLayout mtablayout;
    private ViewPager mviewPager;
    private Button buttoncomback;
    private int YOUR_REQUEST_CODE =100;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnal);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavigation);
        MenuItem perMenuItem = bottomNavigationView.getMenu().findItem(R.id.personnal);
        perMenuItem.setChecked(true);

        mtablayout = findViewById(R.id.tabLayout);
        mviewPager = findViewById(R.id.viewpager);

        ViewPagerAdapterDepartment viewPagerAdapter = new ViewPagerAdapterDepartment(getSupportFragmentManager(), FragmentStatePagerAdapter .BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,PersonnalActivity.this);
        mviewPager.setAdapter(viewPagerAdapter);
        mtablayout.setupWithViewPager(mviewPager);


        MenuItem homeMenuItem = bottomNavigationView.getMenu().findItem(R.id.home);

        // Thiết lập nghe sự kiện khi menu "home" được chọn
        homeMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Nếu đang ở trong JobActivity, chuyển đến MainActivity
                if (PersonnalActivity.this instanceof PersonnalActivity) {
                    Intent homeIntent = new Intent(PersonnalActivity.this, MainActivity.class);
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
                if (PersonnalActivity.this instanceof PersonnalActivity) {
                    Intent jobIntent = new Intent(PersonnalActivity.this, ProjectActivity.class);
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
                if (PersonnalActivity.this instanceof PersonnalActivity) {
                    Intent addjobIntent = new Intent(PersonnalActivity.this, AddProjectActivity.class);
                    startActivity(addjobIntent);
                }
                return true;
            }
        });
        MenuItem settingMenuItem = bottomNavigationView.getMenu().findItem(R.id.setting);
        // Thiết lập nghe sự kiện khi menu "home" được chọn
        settingMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Nếu đang ở trong JobActivity, chuyển đến AddJobActivity
                if (PersonnalActivity.this instanceof PersonnalActivity) {
                    Intent perIntent = new Intent(PersonnalActivity.this, SettingActivity.class);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Kiểm tra xem kết quả trả về có phải từ AddDeparmentsActivity không
        if (requestCode == YOUR_REQUEST_CODE && resultCode == RESULT_OK) {
            // Nếu là từ AddDeparmentsActivity, chuyển đến ListDepartmentFragment
            mviewPager.setCurrentItem(1); // 1 là index của ListDepartmentFragment
        }
    }

}