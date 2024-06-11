package com.example.do_an_cs3.View;


import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;

import com.example.do_an_cs3.Database.DatabaseFirebaseManager;
import com.example.do_an_cs3.Invite.DetailInviteActivity;
import com.example.do_an_cs3.LoadingDialogFragment;
import com.example.do_an_cs3.Model.User;
import com.example.do_an_cs3.R;
import com.example.do_an_cs3.View.MainActivity;
import com.example.do_an_cs3.View.Project.AddProjectActivity;
import com.example.do_an_cs3.View.Users.PersonnalActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddInfoCompanyActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private Button buttonSave;
    private EditText editTextName;
    private EditText editTextPhoneNumber;
    private EditText editTextAddress;
    private EditText editTextField;
    private CircleImageView circleImageAvatar;
    private String avatarFilePath;

    private SQLiteDatabase db;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private LoadingDialogFragment loadingDialog;
    private DatabaseFirebaseManager dbManager;

    public AddInfoCompanyActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info_company);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        editTextName = findViewById(R.id.editTextName);
        editTextPhoneNumber = findViewById(R.id.editTextPhone);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextField = findViewById(R.id.editTextField);
        circleImageAvatar = findViewById(R.id.circleImageView);
        buttonSave = findViewById(R.id.btnSave);
        dbManager = new DatabaseFirebaseManager();
        checkPermissions();
        initialize();
    }

    public void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                requestManageExternalStoragePermission();
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestStoragePermission();
            }
        }
    }


    private void requestManageExternalStoragePermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
        startActivityForResult(intent, PERMISSION_REQUEST_CODE);
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE) ||
                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Quyền truy cập bộ nhớ")
                    .setMessage("Ứng dụng cần quyền truy cập vào bộ nhớ để chọn ảnh. Vui lòng cấp quyền truy cập.")
                    .setPositiveButton("Đồng ý", (dialog, which) -> ActivityCompat.requestPermissions(AddInfoCompanyActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            PERMISSION_REQUEST_CODE))
                    .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                    .create()
                    .show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initialize();
            } else {
                Toast.makeText(this, "Quyền truy cập bộ nhớ bị từ chối", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void initialize() {
        circleImageAvatar.setOnClickListener(v -> openImageChooser());
        buttonSave.setOnClickListener(v -> {
            saveInforCompany();
        });
    }

    void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            circleImageAvatar.setImageURI(imageUri);
            uploadImageToFirebase(imageUri);
        }
    }

    private String imageUrl;

    public void uploadImageToFirebase(Uri imageUri) {
        // Tạo một tham chiếu đến node trong Firebase Storage
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("avatars/" + UUID.randomUUID().toString());

        // Tải ảnh lên Firebase Storage
        storageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // Lấy URL của ảnh đã tải lên
                    storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        imageUrl = uri.toString();
                        Toast.makeText(this, "Ảnh đã được tải lên thành công. Hãy nhấn lưu để hoàn tất.", Toast.LENGTH_SHORT).show();
                    });
                })
                .addOnFailureListener(e -> {
                    // Xử lý khi có lỗi xảy ra
                    Toast.makeText(this, "Lỗi khi tải ảnh lên Firebase Storage: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    public void saveInforCompany() {
        String companyName = editTextName.getText().toString();
        String phoneNumber = editTextPhoneNumber.getText().toString();
        String address = editTextAddress.getText().toString();
        String field = editTextField.getText().toString();
        String email = getCurrentUserEmail();

        // Tạo một đối tượng User mới và cập nhật trường avatarUr
        if((companyName.isEmpty() || phoneNumber.isEmpty() || address.isEmpty() || imageUrl == null || field.isEmpty())){
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }else{
            FragmentManager fragmentManager = getSupportFragmentManager();
            loadingDialog = LoadingDialogFragment.newInstance();
            loadingDialog.show(fragmentManager, "loading");
            DatabaseReference companyRef = DatabaseFirebaseManager.getInstance().getDatabaseReference().child("companys");
            DatabaseReference newCompanyRef = companyRef.push();
            String companyId = newCompanyRef.getKey();
            newCompanyRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.child("companys").hasChild(companyId)
                        //  && snapshot.child("projectsprojectsParticipant").hasChild(projectParticipantId)
                    ) {
                        Toast.makeText(AddInfoCompanyActivity.this, "Projects already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        dbManager.saveJoin(email, companyId, AddInfoCompanyActivity.this, new DatabaseFirebaseManager.SaveJoinListener() {
                            @Override
                            public void onSaveJoinSuccess() {

                            }
                            @Override
                            public void onSaveJoinFailure(String errorMessage) {
                                Log.e("SaveJoin", "Error: " + errorMessage);
                            }
                });
                        newCompanyRef.child("companyId").setValue(companyId);
                        newCompanyRef.child("companyName").setValue(companyName);
                        newCompanyRef.child("phoneNumber").setValue(phoneNumber);
                        newCompanyRef.child("address").setValue(address);
                        newCompanyRef.child("field").setValue(field);
                        newCompanyRef.child("logoImg").setValue(imageUrl);
                        newCompanyRef.child("email").setValue(email);
                        Toast.makeText(AddInfoCompanyActivity.this, "Đã thêm thông tin thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddInfoCompanyActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("Firebase", "Database error: " + error.getMessage());
                }
            });
        }
    }

    // Gọi phương thức này khi nhấn nút lưu

    public String getCurrentUserEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", null);
    }
}


