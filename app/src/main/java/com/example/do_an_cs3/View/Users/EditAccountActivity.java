package com.example.do_an_cs3.View.Users;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.do_an_cs3.Model.User;
import com.example.do_an_cs3.R;
import com.example.do_an_cs3.View.MainActivity;
import com.example.do_an_cs3.View.SettingActivity;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditAccountActivity extends AppCompatActivity {
    private Button buttonHome;
    private Button buttonComback;
    private CircleImageView circleImageView;
    private EditText editTextName;
    private EditText editTextPhone;
    private EditText editTextAddress;
    private EditText editTextRole;
    private String avatarUrl;
    private EditText editTextDepartment;
    private EditText editTextReferralCode;
   // private DatabaseManager dbManager;
    private Button update;
    private CircleImageView circleImageAvatar;
    private String avatarFilePath;
    private ImageView imageView;
    private  AddProfileActivity addProfileActivity;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PERMISSION_REQUEST_CODE = 100;



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
        addProfileActivity = new AddProfileActivity();
      //  dbManager = new DatabaseManager(this);

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



        update = findViewById(R.id.buttonUpdate);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String name = editTextName.getText().toString();
//                String phone = editTextPhone.getText().toString();
//                String address = editTextAddress.getText().toString();
//                String role = editTextRole.getText().toString();
//                String department = editTextDepartment.getText().toString();
//                String referralCode = editTextReferralCode.getText().toString();
//                String email = getCurrentUserEmail();
//                byte[] avatarData = dbManager.getBytesFromImage(Uri.parse(avatarUrl));
//
//                boolean isSuccess = dbManager.updateUserInfo(email, name, phone, address, role, department, referralCode,avatarData);
//                Log.d("AvatarData", "Length: " + (avatarData != null ? avatarData.length : 0));
//
//                if(isSuccess){
//                    Intent intent = new Intent(EditAccountActivity.this, SettingActivity.class);
//
//                    startActivity(intent);
//                    Toast.makeText(EditAccountActivity.this,"Thông tin đã được câp nhật",Toast.LENGTH_SHORT).show();
//
//                }
//                else {
//                    Toast.makeText(EditAccountActivity.this,"Câp nhật thất bại",Toast.LENGTH_SHORT).show();
//                }
           }
        });

        circleImageView .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

displayUserInfo();
    }
    void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Lấy URI của hình ảnh đã chọn
            Uri uri = data.getData();
            try {
                // Hiển thị hình ảnh đã chọn trong circleImageView
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                circleImageView.setImageBitmap(bitmap);
                // Lưu đường dẫn của hình ảnh đã chọn
                avatarUrl = uri.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    private void displayUserInfo() {
//        User user = dbManager.getUserInfo(getCurrentUserEmail());
//        if (user != null) {
//            editTextName.setText(user.getUserName());
//            editTextPhone.setText(user.getPhoneNumber());
//            editTextAddress.setText(user.getAddress());
//            editTextRole.setText(user.getRole());
//            editTextDepartment.setText(String.valueOf(user.getDeparment())); // Chuyển đổi department_id thành String
//            editTextReferralCode.setText(user.getReferralCode());
//
//            if (user.getAvatar() != null) {
//                byte[] avatarBytes = Base64.decode(user.getAvatar(), Base64.DEFAULT);
//                Bitmap avatarBitmap = BitmapFactory.decodeByteArray(avatarBytes, 0, avatarBytes.length);
//                circleImageView.setImageBitmap(avatarBitmap);
//            }
//        }
    }

    private String getCurrentUserEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", null);
    }


}
