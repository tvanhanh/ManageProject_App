package com.example.do_an_cs3.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.do_an_cs3.Ardapter.ViewPagerAdapterWarning;
import com.example.do_an_cs3.R;
import com.google.android.material.tabs.TabLayout;

public class WarningActivity extends AppCompatActivity {
    private TabLayout mtablayout;
    private ViewPager mviewpager;
    private Button buttonHome;
    private Button buttonBlack;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning);

        mtablayout = findViewById(R.id.tabLayout);
        mviewpager = findViewById(R.id.viewpager);
        ViewPagerAdapterWarning viewPagerAdapter = new ViewPagerAdapterWarning(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mviewpager.setAdapter(viewPagerAdapter);
        mtablayout.setupWithViewPager(mviewpager);

        buttonBlack = findViewById(R.id.comeback);
        buttonHome = findViewById(R.id.buttonHome);

        buttonBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WarningActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}