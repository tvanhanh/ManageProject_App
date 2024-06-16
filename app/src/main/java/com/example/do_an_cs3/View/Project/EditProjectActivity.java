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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.do_an_cs3.Adapter.ProjectAdapter;
import com.example.do_an_cs3.Adapter.TaskAdapter;

import com.example.do_an_cs3.Database.DatabaseFirebaseManager;
import com.example.do_an_cs3.LoadingDialogFragment;
import com.example.do_an_cs3.Model.Project;
import com.example.do_an_cs3.R;
import com.example.do_an_cs3.View.MainActivity;
import com.example.do_an_cs3.View.Users.PersonnalActivity;
import com.example.do_an_cs3.View.SettingActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditProjectActivity extends AppCompatActivity {
    private Button nextButton;
    private Button btnBack;
    private EditText nameProject;
    private TextInputEditText descriptionProject;
    private TextView deadlineTime;
    private LoadingDialogFragment loadingDialog;
    private String idProject;
    private DatabaseFirebaseManager dbManager;
    private Project project;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_project);

        // Nhận idProject từ Intent
        Intent intent = getIntent();
        idProject = intent.getStringExtra("idProject");

        // Kiểm tra idProject
        if (idProject == null) {
            Toast.makeText(this, "Không thể nhận diện dự án", Toast.LENGTH_SHORT).show();
            finish();
            return;  // Ngăn chặn mã tiếp tục thực thi nếu không có idProject
        }

        // dbManager = new DatabaseManager(this);

        nameProject = findViewById(R.id.editAddNameProject);
        descriptionProject = findViewById(R.id.textInputEditTextDescription);
        deadlineTime = findViewById(R.id.textViewDate);
        Date currentCreation = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
        dbManager = new DatabaseFirebaseManager();
        dbManager.getProjectById(idProject, new DatabaseFirebaseManager.ProjectCallback() {
            @Override
            public void onSuccess(Project project) {
                nameProject.setText(project.getName());
                descriptionProject.setText(project.getDescription());
                deadlineTime.setText(project.getDeadline());

            }
            @Override
            public void onFailure(String errorMessage) {

            }
        });

        nextButton = findViewById(R.id.buttonThenext);
        nextButton.setOnClickListener(v -> {
            String name = nameProject.getText().toString();
            String description = descriptionProject.getText().toString();
            String deadline = deadlineTime.getText().toString();
//            String creationTime = dateFormat.format(currentCreation);
//            String status = "Dự án mới";
//           // int views = project.getViews();  // Lấy số lượt xem hiện tại từ đối tượng Project

            if (name.isEmpty() || description.isEmpty() || deadline.isEmpty()) {
                Toast.makeText(this, "Vui lòng kiểm tra lại thông tin", Toast.LENGTH_SHORT).show();
            } else {
                 updateProjectData(idProject,name,description,deadline);
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

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditProjectActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar selectedCalendar = Calendar.getInstance();
                        selectedCalendar.set(year, month, dayOfMonth);

                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(EditProjectActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
    private void updateProjectData(String projectId, String name, String description, String deadline) {

        DatabaseReference projectRef = DatabaseFirebaseManager.getInstance().getDatabaseReference().child("projects").child(projectId);
        projectRef.child("name").setValue(name);
        projectRef.child("description").setValue(description);
        projectRef.child("deadline").setValue(deadline)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(EditProjectActivity.this, "Cập nhật dự án thành công", Toast.LENGTH_SHORT).show();
                    // Quay lại màn hình chính hoặc làm điều gì đó khác
                    Intent intent = new Intent(EditProjectActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(EditProjectActivity.this, "Cập nhật dự án thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

}
