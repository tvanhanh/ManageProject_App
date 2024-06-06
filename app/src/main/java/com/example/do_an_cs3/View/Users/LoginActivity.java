package com.example.do_an_cs3.View.Users;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.example.do_an_cs3.R;
import com.example.do_an_cs3.View.MainActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    private Button LoginButton;
  //  private DatabaseManager dbManager;

    private EditText loginTextEmail;
    private TextView newAccount;
    private CheckBox showPass;
    private EditText loginTextPassword;
    private String email;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://manageproject-7a9ac-default-rtdb.firebaseio.com/");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        email = intent.getStringExtra("email");


        showPass = findViewById(R.id.checkBox);
      //  dbManager = new DatabaseManager(this);
        loginTextEmail = findViewById(R.id.loginEditTextEmail);
        loginTextPassword = findViewById(R.id.loginEditTextPassword);

        loginTextEmail.setText(email);

        LoginButton = findViewById(R.id.btnLogin);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //handleLogin();
                String emailText = loginTextEmail.getText().toString();
                String passwordText = loginTextPassword.getText().toString();
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String encodedEmail = emailText.replace(".", ",");

                        // In ra giá trị của biến encodedEmail để kiểm tra
                        Log.d("LoginActivity", "Encoded Email: " + encodedEmail);

                        // Lấy dữ liệu từ Firebase và chuyển đổi thành HashMap
                        HashMap<String, Object> userData = (HashMap<String, Object>) snapshot.child("users").child(encodedEmail).getValue();
                        String role = snapshot.child("users").child(encodedEmail).child("role").getValue(String.class);

                        // In ra giá trị của biến userData để kiểm tra
                        Log.d("LoginActivity", "User Data: " + userData);

                        if (userData != null) {
                            String emailFFB = (String) userData.get("email");
                            String passwordFFB = (String) userData.get("password");

                            // In ra giá trị của các biến emailFFB và passwordFFB để kiểm tra
                            Log.d("LoginActivity", "Email from Firebase: " + emailFFB);
                            Log.d("LoginActivity", "Password from Firebase: " + passwordFFB);

                            // Kiểm tra xem emailFFB và passwordFFB có giá trị null hay không
                            if (emailFFB != null && passwordFFB != null && emailFFB.equals(encodedEmail) && passwordFFB.equals(passwordText)) {
                                // Đăng nhập thành công
                                SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("user_email", emailText);
                                editor.apply();
                                if (role.equals("Lãnh đạo")) {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                                else {
                                    Intent intent = new Intent(LoginActivity.this, ChooseRoleActivity.class);
                                    startActivity(intent);
                                    finish();
                                }


                            // Thực hiện hành động tiếp theo, ví dụ: chuyển sang màn hình mới
                        } else {
                            // Đăng nhập không thành công
                            Toast.makeText(LoginActivity.this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                        }
                        }
                     else
                    {
                        // Không tìm thấy thông tin người dùng trong CSDL
                        Toast.makeText(LoginActivity.this, "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
                    }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Xử lý khi có lỗi xảy ra trong quá trình truy xuất dữ liệu từ Firebase
                        Toast.makeText(LoginActivity.this, "Đã xảy ra lỗi khi truy xuất dữ liệu từ Firebase", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


        // show pass
        showPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showPass.isChecked()) {
                    loginTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    loginTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        newAccount = findViewById(R.id.newAccount);
        newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void handleLogin() {
//        String email = loginTextEmail.getText().toString();
//        String password = loginTextPassword.getText().toString();
//
//        if (dbManager.login(email, password, LoginActivity.this)) {
//            // Nếu đăng nhập thành công, không cần phải làm gì thêm ở đây
//        } else {
//            // Nếu đăng nhập thất bại, hiển thị thông báo lỗi hoặc thực hiện các hành động khác
//            Toast.makeText(LoginActivity.this, "Incorrect email or password!", Toast.LENGTH_SHORT).show();
//        }
   }
}
