package com.example.do_an_cs3.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.do_an_cs3.Database.DatabaseManager;
import com.example.do_an_cs3.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddJobActivity extends AppCompatActivity {
    private Button nextButton;
    private Button btnBack;
    private EditText nameProject;
    private TextInputEditText descriptionProject;
    private TextView deadlineTime;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addjob);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavigation);
        MenuItem jobaddMenuItem = bottomNavigationView.getMenu().findItem(R.id.add_job);
        jobaddMenuItem.setChecked(true);
        DatabaseManager dbManager = new DatabaseManager(this);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                Intent homeIntent = new Intent(AddJobActivity.this, MainActivity.class);
                startActivity(homeIntent);
                return true;
            }
        });

        MenuItem homeMenuItem = bottomNavigationView.getMenu().findItem(R.id.home);
        homeMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent homeIntent = new Intent(AddJobActivity.this, MainActivity.class);
                startActivity(homeIntent);
                return true;
            }
        });

        nameProject = findViewById(R.id.editAddNameProject);
        descriptionProject = findViewById(R.id.textInputEditTextDescription);
        deadlineTime = findViewById(R.id.textViewDate);

        nextButton = findViewById(R.id.butonThenext);
        nextButton.setOnClickListener(v -> {
            String name = nameProject.getText().toString();
            String description = descriptionProject.getText().toString();
            String deadline = deadlineTime.getText().toString();

            if (name.isEmpty() || description.isEmpty() || deadline.isEmpty()) {
                Toast.makeText(this, "Vui lòng kiểm tra lại thông tin", Toast.LENGTH_SHORT).show();
            } else {
                long insertedId = dbManager.addProject(name, description, deadline);
                if (insertedId != -1) {
                    Toast.makeText(this, "Thêm thành công " + name, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Lỗi " + name, Toast.LENGTH_SHORT).show();
                }
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

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddJobActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar selectedCalendar = Calendar.getInstance();
                        selectedCalendar.set(year, month, dayOfMonth);

                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(AddJobActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                Intent settingIntent = new Intent(AddJobActivity.this, SettingActivity.class);
                startActivity(settingIntent);
                return true;
            }
        });

        MenuItem jobMenuItem = bottomNavigationView.getMenu().findItem(R.id.job);
        jobMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent jobIntent = new Intent(AddJobActivity.this, JobActivity.class);
                startActivity(jobIntent);
                return true;
            }
        });

        MenuItem perMenuItem = bottomNavigationView.getMenu().findItem(R.id.personnal);
        perMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent perIntent = new Intent(AddJobActivity.this, PersonnalActivity.class);
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
}
