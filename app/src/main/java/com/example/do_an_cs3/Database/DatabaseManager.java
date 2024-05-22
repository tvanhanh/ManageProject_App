package com.example.do_an_cs3.Database;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.example.do_an_cs3.Model.Project;
import com.example.do_an_cs3.Model.User;
import com.example.do_an_cs3.View.Users.ChooseRoleActivity;
import com.example.do_an_cs3.View.MainActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private Database dbhelper;
    private SQLiteDatabase db;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 8;
    private static final SecureRandom RANDOM = new SecureRandom();

    public DatabaseManager(Context context) {
        dbhelper = new Database(context);
        db = dbhelper.getWritableDatabase();
    }

    public static String generateReferralCode() {
        StringBuilder referralCode = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            referralCode.append(CHARACTERS.charAt(index));
        }
        return referralCode.toString();
    }

    public long addUser(String email, String password) {
        // Kiểm tra email có đúng định dạng "@gmail.com"
        if (!isValidEmail(email)) {
            return -1; // hoặc một giá trị khác biểu thị lỗi
        }
        // Kiểm tra mật khẩu có đúng độ dài 6 ký tự không
        if (password.length() <= 6) {
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

        String referralCode = generateReferralCode();


        // Nếu email hợp lệ và không tồn tại trong cơ sở dữ liệu, thêm mới người dùng vào cơ sở dữ liệu
        ContentValues values = new ContentValues();

        values.put("email", email);
        values.put("password", password);
        values.put("referral_code", referralCode);

        long id = db.insert("Users", null, values);
        return id;
    }


    //    private byte[] getBytesFromImage(String imagePath) {
//        try {
//            File imageFile = new File(imagePath);
//            FileInputStream fis = new FileInputStream(imageFile);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            byte[] buffer = new byte[1024];
//            int bytesRead;
//            while ((bytesRead = fis.read(buffer)) != -1) {
//                baos.write(buffer, 0, bytesRead);
//            }
//            fis.close();
//            return baos.toByteArray();
//        } catch (IOException e) {
//            Log.e("DatabaseHelper", "Failed to convert image to byte array", e);
//            return null;
//        }
//    }
//    public byte[] getBytesFromImage(String filePath) {
//        FileInputStream fis = null;
//        ByteArrayOutputStream baos = null;
//        try {
//            fis = new FileInputStream(filePath);
//            baos = new ByteArrayOutputStream();
//            byte[] buffer = new byte[1024];
//            int bytesRead;
//            while ((bytesRead = fis.read(buffer)) != -1) {
//                baos.write(buffer, 0, bytesRead);
//            }
//            return baos.toByteArray();
//        } catch (FileNotFoundException e) {
//            Log.e("DatabaseHelper", "File not found", e);
//        } catch (IOException e) {
//            Log.e("DatabaseHelper", "Failed to read file", e);
//        } finally {
//            try {
//                if (fis != null) {
//                    fis.close();
//                }
//                if (baos != null) {
//                    baos.close();
//                }
//            } catch (IOException e) {
//                Log.e("DatabaseHelper", "Failed to close streams", e);
//            }
//        }
//        return null;
//    }
    @SuppressLint("Range")
    public User getUserInfo(String email) {
        User user = null; // Khởi tạo user là null để kiểm tra sau
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.dbhelper.getReadableDatabase();
            // Sử dụng câu lệnh SQL với điều kiện email
            cursor = db.rawQuery("SELECT * FROM Users WHERE email = ?", new String[]{email});
            if (cursor.moveToFirst()) {
                // Lấy thông tin người dùng từ cursor
                String userName = cursor.getString(cursor.getColumnIndex("username"));
                String phoneNumber = cursor.getString(cursor.getColumnIndex("phone_number"));
                String address = cursor.getString(cursor.getColumnIndex("address"));
                String referralCode = cursor.getString(cursor.getColumnIndex("referral_code"));
                byte[] avatarBytes = cursor.getBlob(cursor.getColumnIndex("avatar_url"));
                int department = cursor.getInt(cursor.getColumnIndex("department_id"));
                String role = cursor.getString(cursor.getColumnIndex("role"));

                // Chuyển đổi byte array thành String
                String avatar = (avatarBytes != null) ? Base64.encodeToString(avatarBytes, Base64.DEFAULT) : null;

                user = new User(userName, phoneNumber, address, referralCode, avatar, department, role);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Log lỗi hoặc xử lý ngoại lệ tại đây
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return user;
    }


    // Phương thức kiểm tra định dạng email
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@gmail\\.com";
        return email.matches(emailPattern);
    }

    @SuppressLint("Range") // Phương thức đăng nhập
    public boolean login(String email, String password, Activity activity) {
        String[] columns = {"email", "role"};
        String selection = "email=? AND password=?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query("Users", columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();

        if (count > 0) {
            cursor.moveToFirst();
            String role = cursor.getString(cursor.getColumnIndex("role"));
            cursor.close(); // Close the cursor after retrieving the data
            // Lưu email của người dùng vào SharedPreferences
            SharedPreferences sharedPreferences = activity.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("user_email", email);
            editor.apply();
            if (role == null || role.isEmpty()) {
                // If role is empty, display a different view
                Intent intent = new Intent(activity, ChooseRoleActivity.class); // Assuming ChooseRoleActivity is the activity you want to show
                activity.startActivity(intent);
            } else {
                // If login is successful and role is not empty, go to MainActivity
                Intent intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);
            }

            // Ẩn LoginActivity
            activity.finish();
            return true;
        } else {
            cursor.close(); // Ensure the cursor is closed if count is 0
            return false;
        }
    }

    public boolean updateUserRole(String email, String newRole, String department) {
        try {
            // Tạo một đối tượng ContentValues để chứa dữ liệu cần cập nhật
            ContentValues values = new ContentValues();
            values.put("role", newRole);
            values.put("department_id", department);

            // Tạo điều kiện để xác định người dùng cần cập nhật vai trò
            String selection = "email=?";
            String[] selectionArgs = {email};

            // Thực hiện cập nhật dữ liệu trong cơ sở dữ liệu
            int rowsAffected = db.update("Users", values, selection, selectionArgs);

            // Kiểm tra xem có dòng nào được cập nhật không
            if (rowsAffected > 0) {
                // Trả về true nếu cập nhật thành công
                return true;
            } else {
                // Trả về false nếu không có dòng nào được cập nhật
                return false;
            }
        } catch (Exception e) {
            // Xử lý lỗi và thông báo cho người dùng (nếu cần)
            Log.e("Update Role and Department Error ", "Error updating role: " + e.getMessage());
            return false; // Trả về false nếu có lỗi xảy ra
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
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.dbhelper.getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM Projects", null);
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndex("project_id"));
                    String name = cursor.getString(cursor.getColumnIndex("project_name"));
                    String description = cursor.getString(cursor.getColumnIndex("project_description"));
                    String deadline = cursor.getString(cursor.getColumnIndex("deadline"));
                    String creationTime = cursor.getString(cursor.getColumnIndex("creation_time"));
                    String status = cursor.getString(cursor.getColumnIndex("status"));
                    int views = cursor.getInt(cursor.getColumnIndex("views"));
                    int percentCompleted = cursor.getInt(cursor.getColumnIndex("percent_complete"));
                    String email = cursor.getString(cursor.getColumnIndex("email"));
                    String department = cursor.getString(cursor.getColumnIndex("department_id"));
                    if (department == null) {
                        department = ""; // hoặc bất kỳ giá trị mặc định nào bạn muốn
                    }

                    Project project = new Project(id, name, description, deadline, creationTime, views, percentCompleted, email, status, department);
                    projectList.add(project);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Log lỗi hoặc xử lý ngoại lệ tại đây
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return projectList;
    }

}