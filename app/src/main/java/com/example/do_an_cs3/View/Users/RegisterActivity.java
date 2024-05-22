package com.example.do_an_cs3.View.Users;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.do_an_cs3.Database.DatabaseManager;
import com.example.do_an_cs3.R;

public class RegisterActivity extends AppCompatActivity {
    private TextView textLogin;
    private Button registerButton;
    private EditText addTextEmail;
    private EditText addTextPassword;

    private DatabaseManager dbManager;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbManager = new DatabaseManager(this);
        addTextEmail = findViewById(R.id.editTextEmail);
        addTextPassword = findViewById(R.id.editTextPassword);



        textLogin = findViewById(R.id.textLogin);
        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        registerButton = findViewById(R.id.btnRegister  );
        registerButton.setOnClickListener(v -> {
            String email = addTextEmail.getText().toString();
            String password = addTextPassword.getText().toString();
            if( email.isEmpty()||password.isEmpty())

            {
                Toast.makeText(this, "Please check the information again ", Toast.LENGTH_SHORT).show();
            } else

            {
                String nameEmail = addTextEmail.getText().toString();
                long insertedId = dbManager.addUser(email,password);
                if (insertedId == -1) {
                    Toast.makeText(this, "Is not valid Email ", +  Toast.LENGTH_SHORT).show();
                } else
                if (insertedId == -2){
                    Toast.makeText(this, "Password at least 6 digits ", +  Toast.LENGTH_SHORT).show();
                } else
                if (insertedId == -3){
                    Toast.makeText(this, "Email already exists", +  Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "Register successfully " + nameEmail, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
//                startActivity(intent);
//            }
              });

        }


}