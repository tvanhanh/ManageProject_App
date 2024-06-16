package com.example.do_an_cs3.Task;

import static android.content.ContentValues.TAG;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.do_an_cs3.Adapter.TaskAdapter;
import com.example.do_an_cs3.Database.DatabaseFirebaseManager;
import com.example.do_an_cs3.LoadingDialogFragment;
import com.example.do_an_cs3.Model.Task;
import com.example.do_an_cs3.R;
import com.example.do_an_cs3.View.Project.DetailProjectActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity {
    private EditText nameTask;
    private TextInputEditText descriptionTask;
    private TextView deadlineTime;
    private Button nextButton;
    private Button btnBack;
    private DatabaseFirebaseManager dbManager;
    private LoadingDialogFragment loadingDialog;
    private TaskAdapter taskAdapter;
    private DetailProjectActivity activity;
    private String idProject;
    private String participantEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        nameTask = findViewById(R.id.editAddNameJob);
        descriptionTask = findViewById(R.id.textInputEditTextDescription);
        deadlineTime = findViewById(R.id.textViewDate);
        nextButton = findViewById(R.id.butonThenext);

        dbManager = new DatabaseFirebaseManager();

        Intent intent = getIntent();
        idProject = intent.getStringExtra("projectId");
        participantEmail = intent.getStringExtra("participantEmail");

        if (idProject == null || idProject.isEmpty()) {
            Toast.makeText(this, "Lỗi: ID dự án không hợp lệ", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "idProject is null or empty");
            return;
        }
        Log.d(TAG, "idProject: " + idProject);

        if (participantEmail == null || participantEmail.isEmpty()) {
            Toast.makeText(this, "Lỗi: ID người tham gia không hợp lệ", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "participantId is null or empty");
            return;
        }
        Log.d(TAG, "participantId: " + participantEmail);

        nextButton.setOnClickListener(v -> {

            String name = nameTask.getText().toString();
            String description = descriptionTask.getText().toString();
            String deadline = deadlineTime.getText().toString();
            String status = "Đang thực hiện";
            String email = getCurrentUserEmail();


            if (name.isEmpty() || description.isEmpty() || deadline.isEmpty()) {
                Toast.makeText(this, "Vui lòng kiểm tra lại thông tin", Toast.LENGTH_SHORT).show();
            } else {
                FragmentManager fragmentManager = getSupportFragmentManager();
                loadingDialog = LoadingDialogFragment.newInstance();
                loadingDialog.show(fragmentManager, "loading");


                DatabaseReference databaseTasks = FirebaseDatabase.getInstance().getReference("tasks");
                String taskId = databaseTasks.push().getKey();
                String timeComeplete = "";

                Task task = new Task(taskId, name, description, deadline, status, email, idProject, timeComeplete, participantEmail);

                Log.d(TAG, "Saving task: " + task.toString());


                databaseTasks.child(taskId).setValue(task).addOnCompleteListener(taskCompletion -> {
                    loadingDialog.dismiss();
                    if (taskCompletion.isSuccessful()) {
                        Toast.makeText(AddTaskActivity.this, "Thêm thành công " + name, Toast.LENGTH_SHORT).show();
                        Intent detailIntent = new Intent(AddTaskActivity.this, DetailProjectActivity.class);

                        detailIntent.putExtra("projectId", idProject);
                        detailIntent.putExtra("participantEmail", participantEmail);
                        startActivity(detailIntent);
                        finish();
                    } else {
                        Toast.makeText(AddTaskActivity.this, "Lỗi " + name, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        Button buttonAddTime = findViewById(R.id.buttonaddTime);
        buttonAddTime.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(AddTaskActivity.this, (view, year1, month1, dayOfMonth1) -> {
                Calendar selectedCalendar = Calendar.getInstance();
                selectedCalendar.set(year1, month1, dayOfMonth1);

                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddTaskActivity.this, (view1, hourOfDay, minute1) -> {
                    selectedCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    selectedCalendar.set(Calendar.MINUTE, minute1);

                    SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy, HH:mm", new Locale("vi"));
                    String dateTime = sdf.format(selectedCalendar.getTime());

                    deadlineTime.setText(dateTime);
                }, hour, minute, true);
                timePickerDialog.show();
            }, year, month, dayOfMonth);
            datePickerDialog.show();
        });

        btnBack = findViewById(R.id.btnback);
        btnBack.setOnClickListener(v -> finish());
    }

    public String getCurrentUserEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", null);
    }
}
