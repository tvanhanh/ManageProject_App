package com.example.do_an_cs3.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    // Tên cơ sở dữ liệu
    private static final String DATABASE_NAME = "ManageProject";

    // Phiên bản cơ sở dữ liệu
    private static final int DATABASE_VERSION = 4;

    // Constructor
    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Phương thức onCreate để tạo cơ sở dữ liệu
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng Users
        String createTableUsers = "CREATE TABLE Users (" +
                "email TEXT PRIMARY KEY NOT NULL, " +
                "username TEXT, " +
                "password TEXT NOT NULL, " +
                "phone_number TEXT, " +
                "address TEXT, " +
                "referral_code TEXT, " +
                "avatar_url BLOB, " +
                "department_id INTEGER," +
                "role TEXT, " +
                "FOREIGN KEY(department_id) REFERENCES Departments(department_id)" +
                ")";
        db.execSQL(createTableUsers);

        // Các bảng khác
        // Tạo bảng Projects
        String createTableProjects = "CREATE TABLE Projects (" +
                "project_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "project_name TEXT, " +
                "project_description TEXT, " +
                "deadline TEXT, " +
                "creation_time TEXT," +
                "status TEXT, " +
                "views INTEGER ,"+
                "percent_complete INTEGER ,"+
                "email TEXT, " +
                "department_id INTEGER, " +
                "FOREIGN KEY(email) REFERENCES Users(email), " +
                "FOREIGN KEY(department_id) REFERENCES Departments(department_id)" +
                ")";
        db.execSQL(createTableProjects);

        // Tạo bảng Tasks
        String createTableTasks = "CREATE TABLE Tasks (" +
                "task_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "task_name TEXT, " +
                "task_description TEXT, " +
                "deadline TEXT, " +
                "status TEXT, " +
                "email TEXT, "+
                "project_id INTEGER, " +
                "FOREIGN KEY(email) REFERENCES Users(email) , " +
                "FOREIGN KEY(project_id) REFERENCES Projects(project_id)" +
                ")";
        db.execSQL(createTableTasks);

        // Tạo bảng Departments
        String createTableDepartments = "CREATE TABLE Departments (" +
                "department_id INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "department_name TEXT " +
                ")";
        db.execSQL(createTableDepartments);

        // Tạo bảng Discussions
        String createTableDiscussions = "CREATE TABLE Discussions (" +
                "discussion_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "project_id INTEGER, " +
                "email TEXT, " +
                "message TEXT, " +
                "time TEXT," +
                "FOREIGN KEY(project_id) REFERENCES Projects(project_id), " +
                "FOREIGN KEY(email) REFERENCES Users(email)" +
                ")";
        db.execSQL(createTableDiscussions);

        // Tạo bảng Evaluation
        String createTableEvaluation = "CREATE TABLE Evaluation (" +
                "evaluation_id INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "project_id INTEGER, " +
                "email TEXT, " +
                "quality_rating INTEGER, " +
                "FOREIGN KEY(project_id) REFERENCES Projects(project_id), " +
                "FOREIGN KEY(email) REFERENCES Users(email)" +
                ")";
        db.execSQL(createTableEvaluation);

        // Tạo bảng Performance
        String createTablePerformance = "CREATE TABLE Performance (" +
                "performance_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "email TEXT, " +
                "task_id INTEGER, " +
                "completion_rate REAL, " +
                "status_measure TEXT, " +
                "FOREIGN KEY(email) REFERENCES Users(email), " +
                "FOREIGN KEY(task_id) REFERENCES Tasks(task_id)" +
                ")";
        db.execSQL(createTablePerformance);

        // Tạo bảng Projects_Tag
        String createTableProject_Tag = "CREATE TABLE Project_Tag (" +
                "project_id INTEGER, " +
                "tag_name TEXT, " +
                "PRIMARY KEY (project_id, tag_name), " +
                "FOREIGN KEY(project_id) REFERENCES Projects(project_id)" +
                ")";
        db.execSQL(createTableProject_Tag);

        // Tạo bảng Task_Participants
        String createTableTaskParticipants = "CREATE TABLE Task_Participants (" +
                "task_id INTEGER, " +
                "email TEXT, " +
                "PRIMARY KEY (task_id, email), " +
                "FOREIGN KEY(task_id) REFERENCES Tasks(task_id), " +
                "FOREIGN KEY(email) REFERENCES Users(email)" +
                ")";
        db.execSQL(createTableTaskParticipants);

        // Tạo bảng Project_Participants
        String createTableProject_Participants = "CREATE TABLE Project_Participants (" +
                "project_id INTEGER, " +
                "email TEXT, " +
                "PRIMARY KEY (project_id, email), " +
                "FOREIGN KEY(project_id) REFERENCES Projects(project_id), " +
                "FOREIGN KEY(email) REFERENCES Users(email)" +
                ")";
        db.execSQL(createTableProject_Participants);

        // Tạo bảng Notification
        String createTableNotification = "CREATE TABLE Notification (" +
                "notificationID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "content TEXT, " +
                "dateTime TEXT, " +
                "email TEXT, " +
                "task_id INTEGER, " +
                "FOREIGN KEY(email) REFERENCES Users(email), " +
                "FOREIGN KEY(task_id) REFERENCES Tasks(task_id)" +
                ")";
        db.execSQL(createTableNotification);

        // Tạo bảng Attachments
        String createTableAttachments = "CREATE TABLE Attachments (" +
                "attachment_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "project_id INTEGER, " +
                "email TEXT, " +
                "file_path TEXT, " +
                "file_name TEXT, " +
                "file_type TEXT, " +
                "FOREIGN KEY(email) REFERENCES Users(email), " +
                "FOREIGN KEY(project_id) REFERENCES Projects(project_id)" +
                ")";
        db.execSQL(createTableAttachments);
    }

    // Phương thức onUpgrade để nâng cấp cơ sở dữ liệu
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 4) {
            // Tạo bảng tạm thời
//            String createTempTableUsers = "CREATE TABLE Users_temp (" +
//                    "email TEXT PRIMARY KEY NOT NULL, " +
//                    "username TEXT, " +
//                    "password TEXT NOT NULL, " +
//                    "phone_number TEXT, " +
//                    "address TEXT, " +
//                    "referral_code TEXT, " +
//                    "avatar_url BLOB, " +
//                    "department_id INTEGER," +
//                    "role TEXT, " +  // Bỏ giá trị mặc định cho role
//                    "FOREIGN KEY(department_id) REFERENCES Departments(department_id)" +
//                    ")";
//            db.execSQL(createTempTableUsers);
            String createTempTableTasks = "CREATE TABLE Tasks_temp (" +
                    "task_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "task_name TEXT, " +
                    "task_description TEXT, " +
                    "deadline TEXT, " +
                    "time_complete TEXT," +
                    "status TEXT, " +
                    "email TEXT, "+
                    "project_id INTEGER, " +
                    "FOREIGN KEY(email) REFERENCES Users(email) , " +
                    "FOREIGN KEY(project_id) REFERENCES Projects(project_id)" +
                    ")";
            db.execSQL(createTempTableTasks);

            // Sao chép dữ liệu từ bảng cũ sang bảng tạm thời
//            String copyDataToTempTable = "INSERT INTO Tasks_temp (email, username, password, phone_number, address, referral_code, avatar_url, department_id, role) " +
//                    "SELECT email, username, password, phone_number, address, referral_code, avatar_url, department_id, role FROM Users";
//            db.execSQL(copyDataToTempTable);

            // Xóa bảng cũ
            String dropOldTable = "DROP TABLE Tasks";
            db.execSQL(dropOldTable);

            // Đổi tên bảng tạm thời thành tên bảng cũ
            String renameTempTable = "ALTER TABLE Tasks_temp RENAME TO Tasks";
            db.execSQL(renameTempTable);
        }

        // Nếu có thêm các thay đổi khác cho phiên bản mới hơn, thêm vào đây
    }

    public SQLiteDatabase open() {
        return this.getWritableDatabase();
    }
}
