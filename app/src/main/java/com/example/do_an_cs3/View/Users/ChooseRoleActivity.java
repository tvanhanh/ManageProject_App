package com.example.do_an_cs3.View.Users;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.do_an_cs3.Database.DatabaseFirebaseManager;

import com.example.do_an_cs3.R;
import com.example.do_an_cs3.View.AddInfoCompanyActivity;
import com.example.do_an_cs3.View.MainActivity;
import com.google.firebase.database.DatabaseReference;

public class ChooseRoleActivity extends AppCompatActivity {

    private LinearLayout lnLeader;
    private LinearLayout lnIdOrg;
    private LinearLayout lnQR;
    private LinearLayout lnPersonally;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choose_role);
       // dbManager = new DatabaseManager(this);
        lnLeader = findViewById(R.id.lnLeader);
        lnLeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = getCurrentUserEmail();
                if (userEmail != null) {
                   // dbManager.updateUserRole(userEmail, "Leader", "Phòng Lãnh Đạo");
                    DatabaseReference databaseReference = DatabaseFirebaseManager.getInstance().getDatabaseReference();
                    String encodedEmail = userEmail.replace(".", ",");

                    // Reference to the specific user's role node
                    DatabaseReference userRoleRef = databaseReference.child("users").child(encodedEmail).child("role");
                    // Update the role
                    userRoleRef.setValue("Lãnh đạo");
                    Intent intent = new Intent(ChooseRoleActivity.this, AddInfoCompanyActivity.class);
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
                // Tạo một AlertDialog.Builder
                AlertDialog.Builder builder = new AlertDialog.Builder(ChooseRoleActivity.this);
                builder.setTitle("Nhập ID dự án");

                // Thiết lập ô nhập liệu
                final EditText input = new EditText(ChooseRoleActivity.this);
                builder.setView(input);

                // Thiết lập nút xác nhận
                builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Xử lý khi người dùng nhấn nút Xác nhận
                        String projectId = input.getText().toString();
                        // Thực hiện các hành động cần thiết với projectId ở đây
                    }
                });

                // Thiết lập nút hủy
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Đóng dialog khi người dùng nhấn nút Hủy
                        dialog.cancel();
                    }
                });

                // Hiển thị AlertDialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
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
                String userEmail = getCurrentUserEmail();
                if (userEmail != null) {
                   // dbManager.updateUserRole(userEmail, "Personally", null);
                    DatabaseReference databaseReference = DatabaseFirebaseManager.getInstance().getDatabaseReference();
                    String encodedEmail = userEmail.replace(".", ",");

                    // Reference to the specific user's role node
                    DatabaseReference userRoleRef = databaseReference.child("users").child(encodedEmail).child("role");
                    // Update the role
                    userRoleRef.setValue("Nhân viên");
                    Intent intent = new Intent(ChooseRoleActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Xử lý trường hợp email bị null, có thể là thông báo lỗi hoặc đăng xuất người dùng
                    Toast.makeText(ChooseRoleActivity.this, "Error: User email not found.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private String getCurrentUserEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", null); // Trả về null nếu không tìm thấy email
    }
}

