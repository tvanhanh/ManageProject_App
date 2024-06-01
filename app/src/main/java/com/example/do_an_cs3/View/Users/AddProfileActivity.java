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

import com.example.do_an_cs3.Database.Database;
import com.example.do_an_cs3.R;
import com.example.do_an_cs3.View.MainActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

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
    private Database dbhelper;
    private SQLiteDatabase db;

    public AddProfileActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_infor);

        editTextUserName = findViewById(R.id.editTextNameAdd);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneAdd);
        editTextAddress = findViewById(R.id.editTextAddressAdd);
        circleImageAvatar = findViewById(R.id.circleImageView);
        buttonSave = findViewById(R.id.btnSave);
        dbhelper = new Database(this);
        db = dbhelper.getWritableDatabase();

        checkPermissions();
        initialize();
    }

    public void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                requestManageExternalStoragePermission();
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
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
            saveUserInfo();
            Intent intent = new Intent(AddProfileActivity.this, MainActivity.class);
            startActivity(intent);
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
            avatarFilePath = imageUri.toString();
        }
    }

    public void saveUserInfo() {
        String userName = editTextUserName.getText().toString();
        String phoneNumber = editTextPhoneNumber.getText().toString();
        String address = editTextAddress.getText().toString();
        String userEmail = getCurrentUserEmail();
        saveUserWithAvatar(userEmail, userName, phoneNumber, address, avatarFilePath);
    }

    public void saveUserWithAvatar(String email, String userName, String phoneNumber, String address, String avatarUri) {
        ContentValues values = new ContentValues();
        values.put("username", userName);
        values.put("phone_number", phoneNumber);
        values.put("address", address);

        byte[] avatarData = getBytesFromImage(avatarUri);
        if (avatarData != null) {
            values.put("avatar_url", avatarData);
        }

        int rowsAffected = db.update("Users", values, "email=?", new String[]{email});
        if (rowsAffected > 0) {
            Log.i("DatabaseHelper", "User updated successfully");
        } else {
            Log.e("DatabaseHelper", "Failed to update user");
        }
        db.close();
    }

    private byte[] getBytesFromImage(String uriString) {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;
        try {
            Uri imageUri = Uri.parse(uriString);
            inputStream = getContentResolver().openInputStream(imageUri);
            outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return outputStream.toByteArray();
        } catch (FileNotFoundException e) {
            Log.e("DatabaseManager", "File not found", e);
        } catch (IOException e) {
            Log.e("DatabaseManager", "Failed to read file", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                Log.e("DatabaseManager", "Failed to close streams", e);
            }
        }
        return null;
    }

    public String getCurrentUserEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", null);
    }
}

