package com.example.do_an_cs3.Database;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.do_an_cs3.Model.Project;
import com.example.do_an_cs3.View.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private Database dbhelper;
    private SQLiteDatabase db;

    public DatabaseManager(Context context) {
        dbhelper = new Database(context);
        db = dbhelper.getWritableDatabase();
    }
    public long addUser(String email, String password) {
        // Kiểm tra email có đúng định dạng "@gmail.com"
        if (!isValidEmail(email)) {
            return -1; // hoặc một giá trị khác biểu thị lỗi
        }
        // Kiểm tra mật khẩu có đúng độ dài 6 ký tự không
        if (password.length() != 6) {
            return -2; // hoặc một giá trị khác biểu thị lỗi
        }

        // Kiểm tra xem email đã tồn tại trong cơ sở dữ liệu chưa
        Cursor cursor = db.query("Users", null, "email=?", new String[]{email}, null, null, null);
        if (cursor.getCount() > 0) {
            // Nếu email đã tồn tại, đóng cursor và trả về -3 hoặc một giá trị mà bạn xác định để biểu thị lỗi
            cursor.close();
            return -3; // hoặc một giá trị khác biểu thị lỗi
        }
        cursor.close();
        // Nếu email hợp lệ và không tồn tại trong cơ sở dữ liệu, thêm mới người dùng vào cơ sở dữ liệu
        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("password", password);
        long id = db.insert("Users", null, values);
        return id;
    }
    // Phương thức kiểm tra định dạng email
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@gmail\\.com";
        return email.matches(emailPattern);
    }

    // Phương thức đăng nhập
    public boolean login(String email, String password, Activity activity) {
        String[] columns = {"email"};
        String selection = "email=? AND password=?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query("Users", columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();

        if (count > 0) {
            // Nếu đăng nhập thành công, chuyển sang MainActivity
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
            // Ẩn LoginActivity
            activity.finish();
            return true;
        } else {
            return false;
        }
    }
    public long addProject(String name, String description, String deadline) {
        try {
            // Định dạng deadline thành định dạng của kiểu dữ liệu DATETIME trong SQLite
//            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("'Thứ' E, dd 'tháng' MM yyyy, HH:mm");
//            LocalDateTime localDateTime = LocalDateTime.parse(deadline, inputFormatter);
//            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            String formattedDeadline = outputFormatter.format(localDateTime);

            // Tạo một đối tượng ContentValues để chứa dữ liệu cần chèn vào cơ sở dữ liệu
            ContentValues values = new ContentValues();
            values.put("project_name", name);
            values.put("project_description", description);
            values.put("deadline", deadline); // Chèn chuỗi định dạng Deadline vào cột Deadline

            // Thực hiện chèn dữ liệu vào bảng "Projects" của cơ sở dữ liệu
            long id = db.insert("Projects", null, values);

            return id;
        } catch (Exception e) {
            // Xử lý lỗi và thông báo cho người dùng
            Log.e("Add Project Error", "Error adding project: " + e.getMessage());
            Toast.makeText(null, "Error adding project: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return -1; // Trả về giá trị -1 để biểu thị rằng có lỗi xảy ra
        }
    }
    @SuppressLint("Range")
    public List<Project> getAllProjects() {

        List<Project> projectList = new ArrayList<>();
        SQLiteDatabase db = this.dbhelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Projects", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("project_id"));
                String name = cursor.getString(cursor.getColumnIndex("project_name"));
                String description = cursor.getString(cursor.getColumnIndex("project_description"));
                String deadline = cursor.getString(cursor.getColumnIndex("deadline"));
                String creationTime = cursor.getString(cursor.getColumnIndex("creation_time"));
                int percentCompleted = cursor.getInt(cursor.getColumnIndex("percent_completed"));
                int views = cursor.getInt(cursor.getColumnIndex("views"));
                String email = cursor.getString(cursor.getColumnIndex("email"));
                String status = cursor.getString(cursor.getColumnIndex("status"));
                String department = cursor.getString(cursor.getColumnIndex("department"));
                Project project = new Project(id ,name, description, deadline, creationTime, views, percentCompleted, email, status, department);
                projectList.add(project);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return projectList;
    }

}