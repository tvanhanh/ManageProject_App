package com.example.do_an_cs3.View.Departments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.do_an_cs3.Adapter.ViewPagerAdapterHome;
import com.example.do_an_cs3.Database.DatabaseFirebaseManager;
import com.example.do_an_cs3.R;
import com.example.do_an_cs3.View.Users.PersonnalActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class AddDeparmentsActivity extends AppCompatActivity {

    private EditText deparments;
    private Button adDeparments;
    private DatabaseReference departmentReference;
    private DatabaseReference newDeparments;
    private ViewPagerAdapterHome viewPagerAdapter;

    public String getCurrentUserEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", null);
    }
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddeparments);

        deparments = findViewById(R.id.editTextname);

        adDeparments = findViewById(R.id.addDdepertment);
        adDeparments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String department_Name = deparments.getText().toString();
                departmentReference = DatabaseFirebaseManager.getInstance().getDatabaseReference().child("departments");
                newDeparments = departmentReference.push();
                String departmentId = newDeparments.getKey();
                String email = getCurrentUserEmail();
                departmentReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child("departments").hasChild(departmentId)) {
                            Toast.makeText(AddDeparmentsActivity.this, "Departments already exists", Toast.LENGTH_SHORT).show();
                        } else {
                            newDeparments.child("department_name").setValue(department_Name);
                            newDeparments.child("id").setValue(departmentId);
                            newDeparments.child("completeJob").setValue("");
                            newDeparments.child("email").setValue(email);
                            SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("isListDepartment", true);
                            editor.apply();
                            Toast.makeText(AddDeparmentsActivity.this,"Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddDeparmentsActivity.this, PersonnalActivity.class);
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
        });



    }
}
