//package com.example.do_an_cs3.Task;
//
//import static android.content.ContentValues.TAG;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.do_an_cs3.Adapter.TaskAdapter;
//import com.example.do_an_cs3.Model.Task;
//import com.example.do_an_cs3.R;
//import com.example.do_an_cs3.View.Project.DetailProjectActivity;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ListTaskActivity extends AppCompatActivity {
//
//    private RecyclerView recyclerViewTasks;
//    private TaskAdapter taskAdapter;
//    private List<Task> taskList;
//    private DatabaseReference databaseReference;
//    private String idProject;
//    private String participantEmail;
//
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_listtask);
//
//        recyclerViewTasks = findViewById(R.id.rcv_task);
//        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));
//        Intent intent = getIntent();
//        idProject = intent.getStringExtra("projectId");
//        participantEmail = intent.getStringExtra("emailParticipant");
//        Log.d(TAG, "Received idProject: " + idProject);
//        Log.d(TAG, "Received participantId: " + participantEmail);
//        if (idProject == null || idProject.isEmpty()) {
//            Toast.makeText(this, "Lỗi: ID dự án không hợp lệ", Toast.LENGTH_SHORT).show();
//            Log.e(TAG, "idProject is null or empty");
//            return;
//        }
//        if (participantEmail == null || participantEmail.isEmpty()) {
//            Toast.makeText(this, "Lỗi: ID người tham gia không hợp lệ", Toast.LENGTH_SHORT).show();
//            Log.e(TAG, "participantId is null or empty");
//            return;
//        }
//        taskList = new ArrayList<>();
//        taskAdapter = new TaskAdapter(taskList, this);
//        recyclerViewTasks.setAdapter(taskAdapter);
//        if (idProject != null && !idProject.isEmpty()) {
//            getListTask(idProject);
//        } else {
//            // Handle error
//        }
//        databaseReference = FirebaseDatabase.getInstance().getReference("tasks");
//
//        loadTasksFromFirebase();
//    }
//    private void loadTasksFromFirebase() {
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("tasks");
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    // Đảm bảo rằng giá trị có thể được chuyển đổi thành số nguyên
//                    try {
//                        String stringValue = snapshot.child("tasks").getValue(String.class);
//                        int intValue = Integer.parseInt(stringValue);
//                        // Sử dụng intValue
//                    } catch (NumberFormatException e) {
//                        // Xử lý khi giá trị không thể chuyển đổi thành số nguyên
//                        Log.e("FirebaseError", "Failed to convert value to int", e);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.e("FirebaseError", "Database error: " + databaseError.getMessage());
//            }
//        });
//
//    }
//    private void getListTask(String projectId) {
//        DatabaseReference databaseTasks = FirebaseDatabase.getInstance().getReference("tasks");
//        databaseTasks.orderByChild("projectId").equalTo(projectId).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                taskList.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    Task task = dataSnapshot.getValue(Task.class);
//                    if (task != null) {
//                        taskList.add(task);
//                    }
//                }
//                taskAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(ListTaskActivity.this, "Lỗi khi tải danh sách task", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//}
