package com.example.do_an_cs3.Database;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.do_an_cs3.Adapter.ProjectAdapter;
import com.example.do_an_cs3.Model.Project;
import com.example.do_an_cs3.Model.Task;
import com.example.do_an_cs3.View.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DatabaseFirebaseManager {
    private static DatabaseFirebaseManager instance;
    private DatabaseReference databaseReference;


    private Context mContext;

    public DatabaseFirebaseManager(Context context) {
        // Khởi tạo DatabaseReference

        mContext = context;
    }

    public DatabaseFirebaseManager() {
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://manageproject-7a9ac-default-rtdb.firebaseio.com/");
    }

    public static synchronized DatabaseFirebaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseFirebaseManager();
        }
        return instance;
    }


    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public interface ProjectsCallback {
        void onProjectsReceived(List<Project> projects);
        void onError(String errorMessage);
    }
    public void getAllProjects(String emailProject, ProjectsCallback callback) {
        DatabaseReference projectsRef = DatabaseFirebaseManager.getInstance().getDatabaseReference().child("projects");

        projectsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Project> projectList = new ArrayList<>();
                for (DataSnapshot projectSnapshot : snapshot.getChildren()) {
                    Project project = projectSnapshot.getValue(Project.class);
                    if (project != null && project.getEmail().equals(emailProject)) {
                        String projectId = projectSnapshot.child("id").getValue(String.class);
                        String projectName = projectSnapshot.child("name").getValue(String.class);
                        String projectDescription = projectSnapshot.child("description").getValue(String.class);
                        String projectDeadline = projectSnapshot.child("deadline").getValue(String.class);
                        String projectCreationTime = projectSnapshot.child("creationTime").getValue(String.class);
                        String projectStatus = projectSnapshot.child("status").getValue(String.class);
                        Integer viewsInteger = projectSnapshot.child("views").getValue(Integer.class);
                        int views = viewsInteger != null ? viewsInteger.intValue() : 0;
                        Integer projectPercentCompletedInteger = projectSnapshot.child("percentCompleted").getValue(Integer.class);
                        int percent = projectPercentCompletedInteger != null ? projectPercentCompletedInteger.intValue() : 0;
                        String projectEmail = projectSnapshot.child("email").getValue(String.class);
                        Integer projectDepartmentIdInteger = projectSnapshot.child("department").getValue(Integer.class);
                        int projectDepartmentId = projectDepartmentIdInteger != null ? projectDepartmentIdInteger.intValue() : 0;
                        Project projectGetFB = new Project(projectId, projectName, projectDescription, projectDeadline, projectCreationTime,
                                views, percent, projectEmail, projectStatus, projectDepartmentId);
                        projectList.add(projectGetFB);

                    }
                }
                callback.onProjectsReceived(projectList);

                Log.d("Firebase", "Total projects for email " + emailProject + ": " + projectList.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                  callback.onError(error.getMessage());
                Log.e("Firebase", "Error getting projects", error.toException());
            }

        });
    }


    public void loadImageFromFirebase(String encodedEmail, Activity activity, CircleImageView circleImageView) {
        // Lấy URL của ảnh từ cơ sở dữ liệu Firebase Realtime Database hoặc Cloud Firestore
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(encodedEmail).child("avatar");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String imageUrl = dataSnapshot.getValue(String.class);
                // Sử dụng URL này để tải ảnh về và hiển thị nó
                Glide.with(activity)
                        .load(imageUrl)
                        .into(circleImageView);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra
                Toast.makeText(activity, "Lỗi khi tải ảnh từ Firebase: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getCurrentUserEmail() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", null);
    }


    // Hàm để log thông tin của một dự án

//    public List<Task> getAll(int idProject) {
//        List<Task> taskList = new ArrayList<>();
//        try {
//            db = this.dbhelper.getReadableDatabase();
//            cursor = db.rawQuery("SELECT * FROM Tasks WHERE project_id = ?", new String[]{String.valueOf(idProject)});
//            if (cursor.moveToFirst()) {
//                do {
//                    int id = cursor.getInt(cursor.getColumnIndex("task_id"));
//                    String name = cursor.getString(cursor.getColumnIndex("task_name"));
//                    String description = cursor.getString(cursor.getColumnIndex("task_description"));
//                    String deadline = cursor.getString(cursor.getColumnIndex("deadline"));
//                    String timeComplete = cursor.getString(cursor.getColumnIndex("time_complete"));
//                    String status = cursor.getString(cursor.getColumnIndex("status"));
//                    String email = cursor.getString(cursor.getColumnIndex("email"));
//                    int project_id = cursor.getInt(cursor.getColumnIndex("project_id"));
////                    if (cursor.isNull(department)) {
////                        department = 1; // hoặc bất kỳ giá trị mặc định nào bạn muốn
////                    }
//                    Task task = new Task(id, name, description, deadline, status, email, project_id, timeComplete);
//                    taskList.add(task);
//                } while (cursor.moveToNext());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            // Log lỗi hoặc xử lý ngoại lệ tại đây
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//            if (db != null) {
//                db.close();
//            }
//        }
//        return taskList;
//    }
        }
