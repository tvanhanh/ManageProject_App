    //package com.example.do_an_cs3.View.Users;
    //
    //import android.annotation.SuppressLint;
    //import android.content.Intent;
    //import android.os.Bundle;
    //import android.view.View;
    //import android.widget.Button;
    //import android.widget.EditText;
    //import android.widget.TextView;
    //import android.widget.Toast;
    //
    //import androidx.activity.EdgeToEdge;
    //import androidx.annotation.NonNull;
    //import androidx.appcompat.app.AppCompatActivity;
    //import androidx.core.graphics.Insets;
    //import androidx.core.view.ViewCompat;
    //import androidx.core.view.WindowInsetsCompat;
    //
    //import com.example.do_an_cs3.Database.DatabaseManager;
    //import com.example.do_an_cs3.MemoryData;
    //import com.example.do_an_cs3.R;
    //import com.google.firebase.database.DataSnapshot;
    //import com.google.firebase.database.DatabaseError;
    //import com.google.firebase.database.DatabaseReference;
    //import com.google.firebase.database.FirebaseDatabase;
    //import com.google.firebase.database.ValueEventListener;
    //
    //public class RegisterActivity extends AppCompatActivity {
    //    private TextView textLogin;
    //    private Button registerButton;
    //    private EditText addTextEmail;
    //    private EditText addTextPassword;
    //
    //    private DatabaseManager dbManager;
    //    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://manageproject-7a9ac-default-rtdb.firebaseio.com/");
    //    @SuppressLint("MissingInflatedId")
    //    @Override
    //    protected void onCreate(Bundle savedInstanceState) {
    //
    //
    //
    //        super.onCreate(savedInstanceState);
    //        EdgeToEdge.enable(this);
    //        setContentView(R.layout.activity_register);
    //        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
    //            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
    //            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
    //            return insets;
    //        });
    //
    //        dbManager = new DatabaseManager(this);
    //        addTextEmail = findViewById(R.id.editTextEmail);
    //        addTextPassword = findViewById(R.id.editTextPassword);
    //
    //
    //
    //        textLogin = findViewById(R.id.textLogin);
    //        textLogin.setOnClickListener(new View.OnClickListener() {
    //            @Override
    //            public void onClick(View v) {
    //                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
    //                startActivity(intent);
    //            }
    //        });
    //
    //
        //        registerButton = findViewById(R.id.btnRegister);
        //        if(!MemoryData.getData(this).isEmpty()){
        //            Toast.makeText(RegisterActivity.this, "suscess", Toast.LENGTH_SHORT).show();
        //            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        //            intent.putExtra("email", MemoryData.getData(this));
        //            startActivity(intent);
        //            finish();
        //        }
    //        registerButton.setOnClickListener(v -> {
    //
    //
    //
    //            String email = addTextEmail.getText().toString();
    //            String password = addTextPassword.getText().toString();
    //            if( email.isEmpty()||password.isEmpty())
    //
    //            {
    //                Toast.makeText(this, "Please check the information again ", Toast.LENGTH_SHORT).show();
    //            } else
    //
    //            {
    //                String nameEmail = addTextEmail.getText().toString();
    //                long insertedId = dbManager.addUser(email,password);
    //                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
    //                    @Override
    //                    public void onDataChange(@NonNull DataSnapshot snapshot) {
    //                        if(snapshot.child("users").hasChild(email)){
    //                            Toast.makeText(RegisterActivity.this, "email already exists", Toast.LENGTH_SHORT).show();
    //                        }else{
    //                            databaseReference.child("users").child("email").setValue(email);
    //                            databaseReference.child("users").child("password").setValue(password);
    //                            MemoryData.saveData(email,RegisterActivity.this);
    //
    //                            Toast.makeText(RegisterActivity.this, "suscess", Toast.LENGTH_SHORT).show();
    //                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
    //                            intent.putExtra("email", email);
    //                            startActivity(intent);
    //                            finish();
    //                        }
    //                       }
    //
    //
    //                    @Override
    //                    public void onCancelled(@NonNull DatabaseError error) {
    //
    //                    }
    //                });
    //
    //                if (insertedId == -1) {
    //                    Toast.makeText(this, "Is not valid Email ", +  Toast.LENGTH_SHORT).show();
    //                } else
    //                if (insertedId == -2){
    //                    Toast.makeText(this, "Password at least 6 digits ", +  Toast.LENGTH_SHORT).show();
    //                } else
    //                if (insertedId == -3){
    //                    Toast.makeText(this, "Email already exists", +  Toast.LENGTH_SHORT).show();
    //                }
    //                else {
    //                    Toast.makeText(this, "Register successfully " + nameEmail, Toast.LENGTH_SHORT).show();
    //                }
    //            }
    ////            @Override
    ////            public void onClick(View v) {
    ////                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
    ////                startActivity(intent);
    ////            }
    //              });
    //
    //        }
    //
    //
    //}
    package com.example.do_an_cs3.View.Users;

    import android.annotation.SuppressLint;
    import android.content.Intent;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.activity.EdgeToEdge;
    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.graphics.Insets;
    import androidx.core.view.ViewCompat;
    import androidx.core.view.WindowInsetsCompat;


    import com.example.do_an_cs3.MemoryData;
    import com.example.do_an_cs3.R;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;

    public class RegisterActivity extends AppCompatActivity {
        private TextView textLogin;
        private Button registerButton;
        private EditText addTextEmail;
        private EditText addTextPassword;

       // private DatabaseManager dbManager;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://manageproject-7a9ac-default-rtdb.firebaseio.com/");

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

           // dbManager = new DatabaseManager(this);
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

            registerButton = findViewById(R.id.btnRegister);
            if (!MemoryData.getData(this).isEmpty()) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.putExtra("email", MemoryData.getData(this));
                intent.putExtra("password", MemoryData.getPass(this));
            }
            registerButton.setOnClickListener(v -> {
                String email = addTextEmail.getText().toString();
                String encodedEmail = email.replace(".", ",");
                String password = addTextPassword.getText().toString();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this, "Please check the information again", Toast.LENGTH_SHORT).show();
                } else {
    //                long insertedId = dbManager.addUser(email, password);
    //
    //                if (insertedId == -1) {
    //                    Toast.makeText(this, "Is not valid Email", Toast.LENGTH_SHORT).show();
    //                } else if (insertedId == -2) {
    //                    Toast.makeText(this, "Password at least 6 digits", Toast.LENGTH_SHORT).show();
    //                } else if (insertedId == -3) {
    //                    Toast.makeText(this, "Email already exists", Toast.LENGTH_SHORT).show();
    //                } else {

                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.child("users").hasChild(encodedEmail)) {
                                    Toast.makeText(RegisterActivity.this, "Email already exists", Toast.LENGTH_SHORT).show();
                                } else {
                                    databaseReference.child("users").child(encodedEmail).child("email").setValue(encodedEmail);
                                    databaseReference.child("users").child(encodedEmail).child("password").setValue(password);
                                    databaseReference.child("users").child(encodedEmail).child("role").setValue("");

                                    MemoryData.saveData(encodedEmail, RegisterActivity.this);
                                    MemoryData.savePass(password, RegisterActivity.this);

                                    Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    intent.putExtra("email", email);
                                    intent.putExtra("emailEncoded", encodedEmail);
                                    intent.putExtra("password", email);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.e("Firebase", "Database error: " + error.getMessage());
                            }
                        });
                   // }
                }
            });

        }
    }
