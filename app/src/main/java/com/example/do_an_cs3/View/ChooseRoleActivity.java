package com.example.do_an_cs3.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.do_an_cs3.Database.DatabaseManager;
import com.example.do_an_cs3.R;

public class ChooseRoleActivity extends AppCompatActivity {

    private LinearLayout lnLeader;
    private LinearLayout lnIdOrg;
    private LinearLayout lnQR;
    private LinearLayout lnPersonally;

    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choose_role);
        dbManager = new DatabaseManager(this);
        lnLeader = findViewById(R.id.lnLeader);
        lnLeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = getCurrentUserEmail();
                if (userEmail != null) {
                    dbManager.updateUserRole(userEmail, "Leader");
                    Intent intent = new Intent(ChooseRoleActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Xử lý trường hợp email bị null, có thể là thông báo lỗi hoặc đăng xuất người dùng
                    Toast.makeText(ChooseRoleActivity.this, "Error: User email not found.", Toast.LENGTH_SHORT).show();
                }

            }

        });

        lnIdOrg = findViewById(R.id.lnIdOrg);
        lnIdOrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseRoleActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        lnQR = findViewById(R.id.lnQRcode);
        lnQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseRoleActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        lnPersonally = findViewById(R.id.lnPersonally);
        lnPersonally.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseRoleActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private String getCurrentUserEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", null); // Trả về null nếu không tìm thấy email
    }
}

