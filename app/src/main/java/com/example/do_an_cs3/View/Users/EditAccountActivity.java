package com.example.do_an_cs3.View.Users;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.do_an_cs3.Database.DatabaseManager;
import com.example.do_an_cs3.Model.User;
import com.example.do_an_cs3.R;
import com.example.do_an_cs3.View.MainActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditAccountActivity extends AppCompatActivity {
    private Button buttonHome;
    private Button buttonComback;
    private CircleImageView circleImageView;
    private EditText editTextName;
    private EditText editTextPhone;
    private EditText editTextAddress;
    private EditText editTextRole;
    private EditText editTextDepartment;
    private EditText editTextReferralCode;
    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        buttonHome = findViewById(R.id.buttonHome);
        buttonComback = findViewById(R.id.comeback);
        circleImageView = findViewById(R.id.circleImageView);
        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextRole = findViewById(R.id.editTextPosition);
        editTextDepartment = findViewById(R.id.editTextDepartment);
        editTextReferralCode = findViewById(R.id.editTextReferral);

        dbManager = new DatabaseManager(this);

        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditAccountActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        buttonComback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        displayUserInfo();
    }

    private void displayUserInfo() {
        User user = dbManager.getUserInfo(getCurrentUserEmail());
        if (user != null) {
            editTextName.setText(user.getUserName());
            editTextPhone.setText(user.getPhoneNumber());
            editTextAddress.setText(user.getAddress());
            editTextRole.setText(user.getRole());
            editTextDepartment.setText(String.valueOf(user.getDeparment())); // Chuyển đổi department_id thành String
            editTextReferralCode.setText(user.getReferralCode());

            if (user.getAvatar() != null) {
                byte[] avatarBytes = Base64.decode(user.getAvatar(), Base64.DEFAULT);
                Bitmap avatarBitmap = BitmapFactory.decodeByteArray(avatarBytes, 0, avatarBytes.length);
                circleImageView.setImageBitmap(avatarBitmap);
            }
        }
    }

    private String getCurrentUserEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", null);
    }
}
