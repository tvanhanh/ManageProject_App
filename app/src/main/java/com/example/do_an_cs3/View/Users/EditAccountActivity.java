package com.example.do_an_cs3.View.Users;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.appcompat.app.AppCompatActivity;

import com.example.do_an_cs3.Database.DatabaseFirebaseManager;
import com.example.do_an_cs3.Model.User;
import com.example.do_an_cs3.R;
import com.example.do_an_cs3.View.MainActivity;
import com.example.do_an_cs3.View.SettingActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.checkerframework.checker.units.qual.A;

import java.io.ByteArrayOutputStream;
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
    private Button update;
    private String encodedEmail;
    private ImageView imageView;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private DatabaseReference userRef;
    private DatabaseFirebaseManager dbFBManager;
    public EditAccountActivity(){

    }
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
        update = findViewById(R.id.buttonUpdate);
        dbFBManager = new DatabaseFirebaseManager();
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

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // updateUserInfo();
            }
        });

        circleImageView.setOnClickListener(new View.OnClickListener() {
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
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                circleImageView.setImageBitmap(bitmap);
                avatarUrl = uri.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void displayUserInfo() {
        String userEmail = getCurrentUserEmail();
        if (userEmail != null) {
            encodedEmail = userEmail.replace(".", ",");
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            userRef = databaseReference.child("users").child(encodedEmail);

            // Sử dụng ValueEventListener để lấy dữ liệu từ Firebase
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Kiểm tra xem dữ liệu có tồn tại hay không
                    if (dataSnapshot.exists()) {
                        // Lấy dữ liệu từ DataSnapshot và hiển thị nó trong TextView
                        String userName = dataSnapshot.child("userName").getValue(String.class);
                        String phoneNumber = dataSnapshot.child("phone").getValue(String.class);
                        String address =dataSnapshot.child("address").getValue(String.class);
                        String role =dataSnapshot.child("role").getValue(String.class);
                        String department =dataSnapshot.child("department").getValue(String.class);
                        String code =dataSnapshot.child("code").getValue(String.class);
                        dbFBManager.loadImageFromFirebase(encodedEmail, EditAccountActivity.this, circleImageView);
                        // Hiển thị dữ liệu trong TextView
                        editTextName.setText(userName);
                        editTextPhone.setText(phoneNumber);
                        editTextAddress.setText(address);
                        editTextRole.setText(role);
                        editTextDepartment.setText(department);

                    } else {
                        // Xử lý trường hợp không có dữ liệu
                        Toast.makeText(EditAccountActivity.this, "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Xử lý khi có lỗi xảy ra trong quá trình đọc dữ liệu từ Firebase
                    Log.e("FirebaseDatabase", "Failed to read user data", databaseError.toException());
                    Toast.makeText(EditAccountActivity.this, "Đã xảy ra lỗi khi đọc dữ liệu từ Firebase", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(EditAccountActivity.this, "Không tìm thấy email người dùng", Toast.LENGTH_SHORT).show();
        }
    }


//

    private interface AvatarUploadCallback {
        void onSuccess(String avatarBase64);
        void onFailure();
    }


    private void updateUserInDatabase(String email, String name, String phone, String address, String role, String department, String referralCode, String avatarBase64) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        User user = new User(name, phone, address, role, department, referralCode, avatarBase64);
        databaseReference.child(email.replace(".", ",")).setValue(user).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(EditAccountActivity.this, "User info updated successfully.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditAccountActivity.this, SettingActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(EditAccountActivity.this, "Failed to update user info.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getCurrentUserEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", null);
    }
}
