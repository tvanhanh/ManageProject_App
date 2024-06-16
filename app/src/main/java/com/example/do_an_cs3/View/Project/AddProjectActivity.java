package com.example.do_an_cs3.View.Project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.do_an_cs3.Adapter.TaskAdapter;
import com.example.do_an_cs3.Database.DatabaseFirebaseManager;

import com.example.do_an_cs3.LoadingDialogFragment;
import com.example.do_an_cs3.MemoryData;
import com.example.do_an_cs3.R;
import com.example.do_an_cs3.View.MainActivity;
import com.example.do_an_cs3.View.Users.LoginActivity;
import com.example.do_an_cs3.View.Users.PersonnalActivity;
import com.example.do_an_cs3.View.SettingActivity;
import com.example.do_an_cs3.View.Users.RegisterActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddProjectActivity extends AppCompatActivity {
    private Button nextButton;
    private Button btnBack;
    private EditText nameProject;
    private TextInputEditText descriptionProject;
    private TextView deadlineTime;

    private LoadingDialogFragment loadingDialog;
    private DatabaseFirebaseManager dbManager;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproject);
        dbManager = new DatabaseFirebaseManager();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavigation);
        MenuItem jobaddMenuItem = bottomNavigationView.getMenu().findItem(R.id.add_job);
        jobaddMenuItem.setChecked(true);
       // DatabaseManager dbManager = new DatabaseManager(this);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                Intent homeIntent = new Intent(AddProjectActivity.this, MainActivity.class);
                startActivity(homeIntent);
                return true;
            }
        });

        MenuItem homeMenuItem = bottomNavigationView.getMenu().findItem(R.id.home);
        homeMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent homeIntent = new Intent(AddProjectActivity.this, MainActivity.class);
                startActivity(homeIntent);
                return true;
            }
        });
        nameProject = findViewById(R.id.editAddNameProject);
        descriptionProject = findViewById(R.id.textInputEditTextDescription);
        deadlineTime = findViewById(R.id.textViewDate);
        Date currentCreation = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy, HH:mm");

        // Chuyển đổi thời gian hiện tại thành chuỗi định dạng

        nextButton = findViewById(R.id.butonThenext);
        nextButton.setOnClickListener(v -> {
            String name = nameProject.getText().toString();
            String description = descriptionProject.getText().toString();
            String deadline = "Thời hạn: " + deadlineTime.getText().toString();
            String creationTime = dateFormat.format(currentCreation);
            String status = "Dự án mới";
            int views = 0;
            float percent_complete = 0;
            String email = getCurrentUserEmail();
            int department = 0;
            String encodedEmail = email.replace(".", ",");

            if (name.isEmpty() || description.isEmpty() || deadline.isEmpty()) {
                Toast.makeText(this, "Vui lòng kiểm tra lại thông tin", Toast.LENGTH_SHORT).show();
            } else {
                FragmentManager fragmentManager = getSupportFragmentManager();
                loadingDialog = LoadingDialogFragment.newInstance();
                loadingDialog.show(fragmentManager, "loading");
                DatabaseReference projectsRef = DatabaseFirebaseManager.getInstance().getDatabaseReference().child("projects");
                DatabaseReference newProjectRef = projectsRef.push();
                String projectId = newProjectRef.getKey();
//                DatabaseReference projectParticipantRef = DatabaseFirebaseManager.getInstance().getDatabaseReference().child("projectsParticipant");
//                DatabaseReference newProjectParticipantRef = projectParticipantRef.push();
//                String projectParticipantId = newProjectParticipantRef.getKey();
                projectsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child("projects").hasChild(projectId)

                        ) {
                            Toast.makeText(AddProjectActivity.this, "Dự án đã tồn tại", Toast.LENGTH_SHORT).show();
                        } else {

                            dbManager.saveProjectJoin( getCurrentUserEmail(), projectId ,AddProjectActivity.this, new DatabaseFirebaseManager.SaveProjectJoinListener() {
                                @Override
                                public void onSaveProjectJoinSuccess() {

                                }

                                @Override
                                public void onSaveProjectJoinFailure(String errorMessage) {

                                }
                            });
                            newProjectRef.child("creationTime").setValue(creationTime);
                            newProjectRef.child("deadline").setValue(deadline);
                            newProjectRef.child("department").setValue(department);
                            newProjectRef.child("email").setValue(email);
                            newProjectRef.child("description").setValue(description);
                            newProjectRef.child("percentCompleted").setValue(percent_complete);
                            newProjectRef.child("id").setValue(projectId);
                            newProjectRef.child("name").setValue(name);
                            newProjectRef.child("deadline").setValue(deadline);
                            newProjectRef.child("status").setValue(status);
                            newProjectRef.child("views").setValue(views);

                            Toast.makeText(AddProjectActivity.this, "Thêm dự án "+ name + " thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddProjectActivity.this, MainActivity.class);
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

            }
        });



        Button buttonAddTime = findViewById(R.id.buttonaddTime);
        buttonAddTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddProjectActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar selectedCalendar = Calendar.getInstance();
                        selectedCalendar.set(year, month, dayOfMonth);

                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(AddProjectActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                selectedCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                selectedCalendar.set(Calendar.MINUTE, minute);

                                SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy, HH:mm", new Locale("vi"));
                                String dateTime = sdf.format(selectedCalendar.getTime());

                                deadlineTime.setText(dateTime);
                            }
                        }, hour, minute, true);
                        timePickerDialog.show();
                    }
                }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        MenuItem settingMenuItem = bottomNavigationView.getMenu().findItem(R.id.setting);
        settingMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent settingIntent = new Intent(AddProjectActivity.this, SettingActivity.class);
                startActivity(settingIntent);
                return true;
            }
        });

        MenuItem jobMenuItem = bottomNavigationView.getMenu().findItem(R.id.job);
        jobMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent jobIntent = new Intent(AddProjectActivity.this, ProjectActivity.class);
                startActivity(jobIntent);
                return true;
            }
        });

        MenuItem perMenuItem = bottomNavigationView.getMenu().findItem(R.id.personnal);
        perMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent perIntent = new Intent(AddProjectActivity.this, PersonnalActivity.class);
                startActivity(perIntent);
                return true;
            }
        });

        btnBack = findViewById(R.id.btnback);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public String getCurrentUserEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", null);
    }

}