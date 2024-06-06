//package com.example.do_an_cs3.View.Users;
//
//import android.Manifest;
//import android.content.ContentUris;
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Environment;
//import android.provider.DocumentsContract;
//import android.provider.MediaStore;
//import android.provider.Settings;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import com.example.do_an_cs3.Database.Database;
//import com.example.do_an_cs3.Database.DatabaseManager;
//import com.example.do_an_cs3.R;
//import com.example.do_an_cs3.View.MainActivity;
//
//import java.io.ByteArrayOutputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//public class AddProfileActivity extends AppCompatActivity {
//    private static final int PICK_IMAGE_REQUEST = 1;
//    private static final int PERMISSION_REQUEST_CODE = 100;
//    private Button buttonSave;
//    private EditText editTextUserName;
//    private EditText editTextPhoneNumber;
//    private EditText editTextAddress;
//    private CircleImageView circleImageAvatar;
//    private String avatarFilePath;
//    private Database dbhelper;
//    private SQLiteDatabase db;
//
//
//    public AddProfileActivity(){
//
//    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_infor);
//
//        editTextUserName = findViewById(R.id.editTextNameAdd);
//        editTextPhoneNumber = findViewById(R.id.editTextPhoneAdd);
//        editTextAddress = findViewById(R.id.editTextAddressAdd);
//        circleImageAvatar = findViewById(R.id.circleImageView);
//        buttonSave = findViewById(R.id.btnSave);
//        dbhelper = new Database(this);
//        db = dbhelper.getWritableDatabase();
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            if (!Environment.isExternalStorageManager()) {
//                requestManageExternalStoragePermission();
//            } else {
//                initialize();
//            }
//        } else {
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                requestStoragePermission();
//            } else {
//                initialize();
//            }
//        }
//    }
//
//    private void requestManageExternalStoragePermission() {
//        Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
//        startActivityForResult(intent, PERMISSION_REQUEST_CODE);
//    }
//
//    private void requestStoragePermission() {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE) ||
//                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//            new AlertDialog.Builder(this)
//                    .setTitle("Quyền truy cập bộ nhớ")
//                    .setMessage("Ứng dụng cần quyền truy cập vào bộ nhớ để chọn ảnh. Vui lòng cấp quyền truy cập.")
//                    .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            ActivityCompat.requestPermissions(AddProfileActivity.this,
//                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                                    PERMISSION_REQUEST_CODE);
//                        }
//                    })
//                    .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    })
//                    .create()
//                    .show();
//        } else {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    PERMISSION_REQUEST_CODE);
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == PERMISSION_REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                initialize();
//            } else {
//                Toast.makeText(this, "Quyền truy cập bộ nhớ bị từ chối", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    private void initialize() {
//        circleImageAvatar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openImageChooser();
//            }
//        });
//
//        buttonSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveUserInfo();
//                Intent intent = new Intent(AddProfileActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void openImageChooser() {
//        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("image/*");
//        startActivityForResult(intent, PICK_IMAGE_REQUEST);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            Uri imageUri = data.getData();
//            circleImageAvatar.setImageURI(imageUri);
//            avatarFilePath = imageUri.toString();
//        }
//    }
//
//    private void saveUserInfo() {
//        String userName = editTextUserName.getText().toString();
//        String phoneNumber = editTextPhoneNumber.getText().toString();
//        String address = editTextAddress.getText().toString();
//        String userEmail = getCurrentUserEmail();
//        DatabaseManager dbManager = new DatabaseManager(this);
//        saveUserWithAvatar(userEmail, userName, phoneNumber, address, avatarFilePath);
//    }
//
//    public void saveUserWithAvatar(String email, String userName, String phoneNumber, String address, String avatarUri) {
//        ContentValues values = new ContentValues();
//        values.put("username", userName);
//        values.put("phone_number", phoneNumber);
//        values.put("address", address);
//
//        byte[] avatarData = getBytesFromImage(avatarUri);
//        if (avatarData != null) {
//            values.put("avatar_url", avatarData);
//        }
//
//        int rowsAffected = db.update("Users", values, "email=?", new String[]{email});
//        if (rowsAffected > 0) {
//            Log.i("DatabaseHelper", "User updated successfully");
//        } else {
//            Log.e("DatabaseHelper", "Failed to update user");
//        }
//        db.close();
//    }
//
//    private byte[] getBytesFromImage(String uriString) {
//        InputStream inputStream = null;
//        ByteArrayOutputStream outputStream = null;
//        try {
//            Uri imageUri = Uri.parse(uriString);
//            inputStream = getContentResolver().openInputStream(imageUri);
//            outputStream = new ByteArrayOutputStream();
//            byte[] buffer = new byte[1024];
//            int bytesRead;
//            while ((bytesRead = inputStream.read(buffer)) != -1) {
//                outputStream.write(buffer, 0, bytesRead);
//            }
//            return outputStream.toByteArray();
//        } catch (FileNotFoundException e) {
//            Log.e("DatabaseManager", "File not found", e);
//        } catch (IOException e) {
//            Log.e("DatabaseManager", "Failed to read file", e);
//        } finally {
//            try {
//                if (inputStream != null) {
//                    inputStream.close();
//                }
//                if (outputStream != null) {
//                    outputStream.close();
//                }
//            } catch (IOException e) {
//                Log.e("DatabaseManager", "Failed to close streams", e);
//            }
//        }
//        return null;
//    }
//
//    public String getCurrentUserEmail() {
//        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
//        return sharedPreferences.getString("user_email", null);
//    }
//}

package com.example.do_an_cs3.View.Users;

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

import com.bumptech.glide.Glide;

import com.example.do_an_cs3.Database.DatabaseFirebaseManager;
import com.example.do_an_cs3.Model.User;
import com.example.do_an_cs3.R;
import com.example.do_an_cs3.View.MainActivity;
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

public class AddProfileActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private Button buttonSave;
    private EditText editTextUserName;
    private EditText editTextPhoneNumber;
    private EditText editTextAddress;
    private CircleImageView circleImageAvatar;
    private String avatarFilePath;

    private SQLiteDatabase db;
    private FirebaseStorage storage;
    private StorageReference storageRef;

    public AddProfileActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_infor);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        editTextUserName = findViewById(R.id.editTextNameAdd);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneAdd);
        editTextAddress = findViewById(R.id.editTextAddressAdd);
        circleImageAvatar = findViewById(R.id.circleImageView);
        buttonSave = findViewById(R.id.btnSave);
//        dbhelper = new Database(this);
//        db = dbhelper.getWritableDatabase();

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
                    .setPositiveButton("Đồng ý", (dialog, which) -> ActivityCompat.requestPermissions(AddProfileActivity.this,
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
            saveUserData();
        });
    }

    void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

//    public void loadImageFromFirebase(String encodedEmail) {
//        // Lấy URL của ảnh từ cơ sở dữ liệu Firebase Realtime Database hoặc Cloud Firestore
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(encodedEmail).child("avatar");
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String imageUrl = dataSnapshot.getValue(String.class);
//                // Sử dụng URL này để tải ảnh về và hiển thị nó
//                Glide.with(AddProfileActivity.this)
//                        .load(imageUrl)
//                        .into(circleImageAvatar);
//            }
//
//    @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // Xử lý khi có lỗi xảy ra
//                Toast.makeText(AddProfileActivity.this, "Lỗi khi tải ảnh từ Firebase: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

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

    public void saveUserData() {
        String userName = editTextUserName.getText().toString();
        String phoneNumber = editTextPhoneNumber.getText().toString();
        String address = editTextAddress.getText().toString();


        // Tạo một đối tượng User mới và cập nhật trường avatarUr
        if((userName.isEmpty() || phoneNumber.isEmpty() || address.isEmpty() || imageUrl == null )){
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }else{
            String userEmail = getCurrentUserEmail();
            String encodedEmail = userEmail.replace(".", ",");

            DatabaseReference userRef = DatabaseFirebaseManager.getInstance().getDatabaseReference().child("users").child(encodedEmail);

            userRef.child("username").setValue(userName).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d("FirebaseDatabase", "Username saved successfully");
                } else {
                    Log.e("FirebaseDatabase", "Failed to save username", task.getException());
                }
            });

            userRef.child("phone").setValue(phoneNumber).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d("FirebaseDatabase", "Phone number saved successfully");
                } else {
                    Log.e("FirebaseDatabase", "Failed to save phone number", task.getException());
                }
            });

            userRef.child("address").setValue(address).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d("FirebaseDatabase", "Address saved successfully");
                } else {
                    Log.e("FirebaseDatabase", "Failed to save address", task.getException());
                }
            });
            userRef.child("avatar").setValue(imageUrl).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                   // loadImageFromFirebase(encodedEmail);
                    Toast.makeText(this, "Thông tin đã được lưu thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("FirebaseDatabase", "Failed to save avatar", task.getException());
                }
            });
            Intent intent = new Intent(AddProfileActivity.this, MainActivity.class);
            startActivity(intent);
    }
    }

    // Gọi phương thức này khi nhấn nút lưu



//
//    public void saveUserInfo() {
//        String userName = editTextUserName.getText().toString();
//        String phoneNumber = editTextPhoneNumber.getText().toString();
//        String address = editTextAddress.getText().toString();
//        String userEmail = getCurrentUserEmail();
//        saveUserWithAvatar(userEmail, userName, phoneNumber, address, avatarFilePath);
//    }

//    public void saveUserWithAvatar(String email, String userName, String phoneNumber, String address, String avatarUri) {
//        ContentValues values = new ContentValues();
//        values.put("username", userName);
//        values.put("phone_number", phoneNumber);
//        values.put("address", address);
//
//        byte[] avatarData = getBytesFromImage(avatarUri);
//        if (avatarData != null) {
//            values.put("avatar_url", avatarData);
//        }
//
//        int rowsAffected = db.update("Users", values, "email=?", new String[]{email});
//        if (rowsAffected > 0) {
//            Log.i("DatabaseHelper", "User updated successfully");
//        } else {
//            Log.e("DatabaseHelper", "Failed to update user");
//        }
//        db.close();
//    }
//
//    private byte[] getBytesFromImage(String uriString) {
//        InputStream inputStream = null;
//        ByteArrayOutputStream outputStream = null;
//        try {
//            Uri imageUri = Uri.parse(uriString);
//            inputStream = getContentResolver().openInputStream(imageUri);
//            outputStream = new ByteArrayOutputStream();
//            byte[] buffer = new byte[1024];
//            int bytesRead;
//            while ((bytesRead = inputStream.read(buffer)) != -1) {
//                outputStream.write(buffer, 0, bytesRead);
//            }
//            return outputStream.toByteArray();
//        } catch (FileNotFoundException e) {
//            Log.e("DatabaseManager", "File not found", e);
//        } catch (IOException e) {
//            Log.e("DatabaseManager", "Failed to read file", e);
//        } finally {
//            try {
//                if (inputStream != null) {
//                    inputStream.close();
//                }
//                if (outputStream != null) {
//                    outputStream.close();
//                }
//            } catch (IOException e) {
//                Log.e("DatabaseManager", "Failed to close streams", e);
//            }
//        }
//        return null;
//   }

    public String getCurrentUserEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", null);
    }
}


