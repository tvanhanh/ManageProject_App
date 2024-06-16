package com.example.do_an_cs3.Task;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.do_an_cs3.Adapter.TaskAdapter;
import com.example.do_an_cs3.Database.DatabaseFirebaseManager;
import com.example.do_an_cs3.LoadingDialogFragment;
import com.example.do_an_cs3.Model.Task;
import com.example.do_an_cs3.R;
import com.example.do_an_cs3.View.Project.AddProjectActivity;
import com.example.do_an_cs3.View.Project.DetailProjectActivity;
import com.example.do_an_cs3.View.Project.ProjectActivity;
import com.example.do_an_cs3.View.SettingActivity;
import com.example.do_an_cs3.View.Users.PersonnalActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class EditTaskActivity extends AppCompatActivity {
    private TextView editTextTaskName, editTextTaskDescription, editTextTaskDeadline;
    private Button buttonSave;

    private String idTask;
    private TaskAdapter taskAdapter;
    private Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        taskAdapter = new TaskAdapter();
        Button buttonAddTime = findViewById(R.id.buttonaddTime);
        buttonAddTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar selectedCalendar = Calendar.getInstance();
                        selectedCalendar.set(year, month, dayOfMonth);

                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(EditTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                selectedCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                selectedCalendar.set(Calendar.MINUTE, minute);

                                SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy, HH:mm", new Locale("vi"));
                                String dateTime = sdf.format(selectedCalendar.getTime());

                                editTextTaskDeadline.setText(dateTime);
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

        Intent intent = getIntent();
        idTask = intent.getStringExtra("taskId");
        if (idTask != null ) {
            editTextTaskName = findViewById(R.id.editNameJob);
            editTextTaskDescription = findViewById(R.id.textInputEditTextDescription);
            editTextTaskDeadline = findViewById(R.id.textViewDate);
            buttonSave = findViewById(R.id.butonThenext);
            DatabaseReference taskRef = FirebaseDatabase.getInstance().getReference("tasks").child(idTask);
            taskRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Task task = snapshot.getValue(Task.class);
                        if (task != null) {
                            // Sau khi lấy dữ liệu thành công, bạn có thể tiếp tục thực hiện các thao tác
                            // như sửa đổi thông tin công việc hoặc hiển thị chi tiết công việc.
                            editTextTaskName.setText(task.getTaskName());
                            editTextTaskDescription.setText(task.getTaskDescription());
                            editTextTaskDeadline.setText(task.getTaskDeadline());
                        } else {
                            //Toast.makeText(mContext, "Không tìm thấy công việc với ID: " + taskId, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //Toast.makeText(mContext, "Không tìm thấy công việc với ID: " + taskId, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("TaskAdapter", "Lỗi khi lấy dữ liệu công việc từ Firebase: " + error.getMessage());
                }
            });
        }
        buttonSave.setOnClickListener(v -> saveTaskChanges());
    }

    private void saveTaskChanges() {
        // Lấy thông tin công việc sau khi chỉnh sửa từ giao diện
        String newName = editTextTaskName.getText().toString();
        String newDescription = editTextTaskDescription.getText().toString();
        String newDeadline = editTextTaskDeadline.getText().toString();

        // Kiểm tra xem các thông tin có rỗng không
        if (newName.isEmpty() || newDescription.isEmpty() || newDeadline.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Cập nhật thông tin công việc vào Firebase
        DatabaseReference taskRef = FirebaseDatabase.getInstance().getReference("tasks").child(Objects.requireNonNull(idTask));
        taskRef.child("taskName").setValue(newName);
        taskRef.child("taskDescription").setValue(newDescription);
        taskRef.child("taskDeadline").setValue(newDeadline)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(EditTaskActivity.this, "Cập nhật thông tin công việc thành công", Toast.LENGTH_SHORT).show();
                        taskAdapter.updateTaskList(idTask);
                        Intent intent = new Intent(EditTaskActivity.this, DetailProjectActivity.class);
                        startActivity(intent);
                        finish(); // Đóng Activity sau khi cập nhật thành công
                    } else {
                        Toast.makeText(EditTaskActivity.this, "Cập nhật thông tin công việc thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public String getCurrentUserEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        return sharedPreferences.getString("user_email", null);
    }

}


