package com.example.do_an_cs3.Task;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.do_an_cs3.Adapter.TaskAdapter;
import com.example.do_an_cs3.Database.DatabaseManager;
import com.example.do_an_cs3.R;
import com.example.do_an_cs3.View.Project.AddProjectActivity;
import com.example.do_an_cs3.View.Project.DetailProjectActivity;
import com.example.do_an_cs3.View.Project.ProjectActivity;
import com.example.do_an_cs3.View.SettingActivity;
import com.example.do_an_cs3.View.Users.PersonnalActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity {
    private EditText nameTask;
    private TextInputEditText descriptionTask;
    private TextView deadlineTime;
    private Button nextButton;
    private Button btnBack;

    private TaskAdapter taskAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_task);
        nameTask = findViewById(R.id.editAddNameJob);
        descriptionTask = findViewById(R.id.textInputEditTextDescription);
        deadlineTime = findViewById(R.id.textViewDate);
        nextButton = findViewById(R.id.butonThenext);
        DatabaseManager dbManager = new DatabaseManager(this);

        nextButton.setOnClickListener(v -> {
            String name = nameTask.getText().toString();
            String description = descriptionTask.getText().toString();
            String deadline = deadlineTime.getText().toString();
            String status = "Đang thực hiện";
            String email = getCurrentUserEmail();
            Intent intent = getIntent();
            int idProject = intent.getIntExtra("idProject", -1 );

            if (name.isEmpty() || description.isEmpty() || deadline.isEmpty()) {
                Toast.makeText(this, "Vui lòng kiểm tra lại thông tin", Toast.LENGTH_SHORT).show();
            } else {
                long insertedId = dbManager.addTask(name, description, deadline, status, email, idProject);
                if (insertedId != -1) {
                    Toast.makeText(this, "Thêm thành công " + name, Toast.LENGTH_SHORT).show();
                    taskAdapter.notifyDataSetChanged();
                    intent = new Intent(AddTaskActivity.this, DetailProjectActivity.class);
                    startActivity(intent);
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

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar selectedCalendar = Calendar.getInstance();
                        selectedCalendar.set(year, month, dayOfMonth);

                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(AddTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

    }
    public String getCurrentUserEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", null);
    }
}