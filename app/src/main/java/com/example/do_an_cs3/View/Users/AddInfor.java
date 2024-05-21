package com.example.do_an_cs3.View.Users;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.do_an_cs3.Database.DatabaseManager;
import com.example.do_an_cs3.DatabaseHelper;
import com.example.do_an_cs3.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddInfor extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText editTextUserName;
    private EditText editTextPhoneNumber;
    private EditText editTextAddress;
    private CircleImageView circleImageAvatarFilePath;
    private String avatarFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_infor);

        editTextUserName = findViewById(R.id.editTextNameAdd);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneAdd);
        editTextAddress = findViewById(R.id.editTextAddressAdd);
        circleImageAvatarFilePath = findViewById(R.id.circleImageView);
        Button buttonSave = findViewById(R.id.buttonSave);

        circleImageAvatarFilePath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInfo();
            }
        });
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            circleImageAvatarFilePath.setImageURI(imageUri);
            avatarFilePath = getPathFromUri(imageUri);
        }
    }

    private String getPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String path = cursor.getString(columnIndex);
            cursor.close();
            return path;
        }
        return null;
    }

    private void saveUserInfo() {
        String userName = editTextUserName.getText().toString();
        String phoneNumber = editTextPhoneNumber.getText().toString();
        String address = editTextAddress.getText().toString();

        DatabaseManager db = new DatabaseManager(this);
        db.saveUserWithAvatar(userName, phoneNumber, address, avatarFilePath);
    }
}
