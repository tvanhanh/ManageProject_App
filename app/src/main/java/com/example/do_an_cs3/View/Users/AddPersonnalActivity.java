package com.example.do_an_cs3.View.Users;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.do_an_cs3.R;

public class AddPersonnalActivity extends AppCompatActivity {
    private Button buttonThenext;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpersonnal);

        buttonThenext = findViewById(R.id.butonThenext);
        buttonThenext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AddPersonnalActivity.this);
                boolean currentIsListDepartment = sharedPreferences.getBoolean("isListDepartment", false);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isListDepartment", false);

                editor.apply(); // Lưu thay đổi vào SharedPreferences
                Intent intent = new Intent(AddPersonnalActivity.this, PersonnalActivity.class);
                startActivity(intent);
            }
        });

    }
}
