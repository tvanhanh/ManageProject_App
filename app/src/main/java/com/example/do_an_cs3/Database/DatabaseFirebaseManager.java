package com.example.do_an_cs3.Database;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.do_an_cs3.Invite.DetailInviteActivity;
import com.example.do_an_cs3.LoadingDialogFragment;
import com.example.do_an_cs3.Model.Company;
import com.example.do_an_cs3.Model.Invite;
import com.example.do_an_cs3.Model.Project;
import com.example.do_an_cs3.Model.User;
import com.example.do_an_cs3.View.AddInfoCompanyActivity;
import com.example.do_an_cs3.View.MainActivity;
import com.example.do_an_cs3.View.Users.PersonnalActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DatabaseFirebaseManager {
    private static DatabaseFirebaseManager instance;
    private DatabaseReference databaseReference;

    private Context mContext;
    private LoadingDialogFragment loadingDialog;

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

    public void addProject(EditText nameProject, TextInputEditText descriptionProject, TextView deadlineTime, Activity activity) {
        Date currentCreation = new Date();
        String name = nameProject.getText().toString();
        String description = descriptionProject.getText().toString();
        String deadline = "Thời hạn: " + deadlineTime.getText().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
        String creationTime = dateFormat.format(currentCreation);
        String status = "Dự án mới";
        int views = 0;
        float percent_complete = 0;
        String email = getCurrentUserEmail();
        int department = 0;
        String encodedEmail = email.replace(".", ",");

        if (name.isEmpty() || description.isEmpty() || deadline.isEmpty()) {
            Toast.makeText(activity, "Vui lòng kiểm tra lại thông tin", Toast.LENGTH_SHORT).show();
        } else {

            DatabaseReference projectsRef = DatabaseFirebaseManager.getInstance().getDatabaseReference().child("projects");
            DatabaseReference newProjectRef = projectsRef.push();
            String projectId = newProjectRef.getKey();
//                DatabaseReference projectParticipantRef = DatabaseFirebaseManager.getInstance().getDatabaseReference().child("projectsParticipant");
//                DatabaseReference newProjectParticipantRef = projectParticipantRef.push();
//                String projectParticipantId = newProjectParticipantRef.getKey();
            projectsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.child("projects").hasChild(projectId))
                    //  && snapshot.child("projectsprojectsParticipant").hasChild(projectParticipantId)
                    {
                        Toast.makeText(activity, "Projects already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        newProjectRef.child("creationTime").setValue(creationTime);
                        newProjectRef.child("deadline").setValue(deadline);
                        newProjectRef.child("department").setValue(department);
                        newProjectRef.child("email").setValue(email);
                        newProjectRef.child("description").setValue(description);
                        newProjectRef.child("percentCompleted").setValue(percent_complete);
                        newProjectRef.child("id").setValue(projectId);
                        newProjectRef.child("name").setValue(name);
                        newProjectRef.child("deadline").setValue(deadline);
                        newProjectRef.child("status").setValue(status);
                        newProjectRef.child("views").setValue(views);
//                            newProjectParticipantRef.child("projet_id").setValue(projectId);
//                            newProjectParticipantRef.child("email").setValue(email);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("Firebase", "Database error: " + error.getMessage());
                }
            });
            // }
        }
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public interface ProjectsCallback {
        void onProjectsReceived(List<Project> projects);

        void onError(String errorMessage);
    }

//    public void getAllProjects(String emailProject, ProjectsCallback callback) {
//        DatabaseReference projectsRef = DatabaseFirebaseManager.getInstance().getDatabaseReference().child("projects");
//
//        projectsRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                List<Project> projectList = new ArrayList<>();
//                for (DataSnapshot projectSnapshot : snapshot.getChildren()) {
//                    Project project = projectSnapshot.getValue(Project.class);
//                    if (project != null && project.getEmail().equals(emailProject)) {
//                        String projectId = projectSnapshot.child("id").getValue(String.class);
//                        String projectName = projectSnapshot.child("name").getValue(String.class);
//                        String projectDescription = projectSnapshot.child("description").getValue(String.class);
//                        String projectDeadline = projectSnapshot.child("deadline").getValue(String.class);
//                        String projectCreationTime = projectSnapshot.child("creationTime").getValue(String.class);
//                        String projectStatus = projectSnapshot.child("status").getValue(String.class);
//                        Integer viewsInteger = projectSnapshot.child("views").getValue(Integer.class);
//                        int views = viewsInteger != null ? viewsInteger.intValue() : 0;
//                        Integer projectPercentCompletedInteger = projectSnapshot.child("percentCompleted").getValue(Integer.class);
//                        int percent = projectPercentCompletedInteger != null ? projectPercentCompletedInteger.intValue() : 0;
//                        String projectEmail = projectSnapshot.child("email").getValue(String.class);
//                        Integer projectDepartmentIdInteger = projectSnapshot.child("department").getValue(Integer.class);
//                        int projectDepartmentId = projectDepartmentIdInteger != null ? projectDepartmentIdInteger.intValue() : 0;
//                        Log.e("DatabaseFirebaseManager", "Project ID: " + projectId);
//                        Project projectGetFB = new Project(projectId, projectName, projectDescription, projectDeadline, projectCreationTime,
//                                views, percent, projectEmail, projectStatus, projectDepartmentId);
//                        projectList.add(projectGetFB);
//                    }
//                }
//                callback.onProjectsReceived(projectList);
//
//                Log.d("Firebase", "Total projects for email " + emailProject + ": " + projectList.size());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                callback.onError(error.getMessage());
//                Log.e("Firebase", "Error getting projects" + error.getMessage(), error.toException());
//            }
//
//        });
//    }
    //lấy ID project bằng email
    public void getProjectsByEmail(String email, GetProjectsByEmailListener listener) {
        DatabaseReference joinProjectsRef = FirebaseDatabase.getInstance().getReference().child("joinProjects");
        joinProjectsRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> projectIds = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String projectId = snapshot.child("projectId").getValue(String.class);
                    if (projectId != null) {
                        projectIds.add(projectId);
                    }
                }
                listener.onGetProjectsByEmailSuccess(projectIds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onGetProjectsByEmailFailure(databaseError.getMessage());
            }
        });
    }

        // Hàm lấy thông tin các dự án dựa trên mảng projectId
        public void getProjectsByIds(List<String> projectIds, GetProjectsByIdsListener listener) {
            DatabaseReference projectsRef = FirebaseDatabase.getInstance().getReference().child("projects");
            List<Project> projects = new ArrayList<>();

            for (String projectId : projectIds) {
                projectsRef.child(projectId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Project project = dataSnapshot.getValue(Project.class);
                        if (project != null) {
                            projects.add(project);
                        }

                        // Kiểm tra nếu đã lấy đủ các dự án
                        if (projects.size() == projectIds.size()) {
                            listener.onGetProjectsByIdsSuccess(projects);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        listener.onGetProjectsByIdsFailure(databaseError.getMessage());
                    }
                });
            }
        }

        public interface GetProjectsByEmailListener {
            void onGetProjectsByEmailSuccess(List<String> projectIds);
            void onGetProjectsByEmailFailure(String errorMessage);
        }

        public interface GetProjectsByIdsListener {
            void onGetProjectsByIdsSuccess(List<Project> projects);
            void onGetProjectsByIdsFailure(String errorMessage);
        }
        //chức năng hiển thị recycle view người thực hiện'

        public void getUsersByProjectId(String projectId, GetUsersByProjectIdListener listener) {
            DatabaseReference joinProjectsRef = databaseReference.child("joinProjects");
            List<String> userIds = new ArrayList<>();

            joinProjectsRef.orderByChild("projectId").equalTo(projectId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String userId = snapshot.child("email").getValue(String.class);
                        if (userId != null) {
                            userIds.add(userId);
                        }
                    }

                    // Gọi listener để thông báo kết quả
                    listener.onGetUsersByProjectIdSuccess(userIds);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    listener.onGetUsersByProjectIdFailure(databaseError.getMessage());
                }
            });
        }
            //Hàm get các user chhung dự án
    public void getUsers(List<String> userIds, GetUsersListener listener) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        List<User> userList = new ArrayList<>();

        for (String userId : userIds) {
            String emailEncode = userId.replace(".", ",");
            usersRef.orderByChild("email").equalTo(emailEncode).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User user = snapshot.getValue(User.class);
                        if (user != null) {
                            userList.add(user);
                        }
                    }

                    // Kiểm tra nếu đã lấy đủ thông tin của các người dùng
                    if (userList.size() == userIds.size()) {
                        listener.onGetUsersSuccess(userList);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    listener.onGetUsersFailure(databaseError.getMessage());
                }
            });
        }
    }

    // Interface listener để xử lý kết quả lấy danh sách người dùng dựa trên projectId
    public interface GetUsersByProjectIdListener {
        void onGetUsersByProjectIdSuccess(List<String> userIds);
        void onGetUsersByProjectIdFailure(String errorMessage);
    }

    // Interface listener để xử lý kết quả lấy thông tin người dùng từ userIds
    public interface GetUsersListener {
        void onGetUsersSuccess(List<User> users);
        void onGetUsersFailure(String errorMessage);
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
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra
                Toast.makeText(activity, "Lỗi khi tải ảnh từ Firebase: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
            public void loadImageCompany(String idCompany, Activity activity, ImageView ImageView) {
                // Lấy URL của ảnh từ cơ sở dữ liệu Firebase Realtime Database hoặc Cloud Firestore
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("companys").child(idCompany).child("logoImg");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String imageUrl = dataSnapshot.getValue(String.class);
                        // Sử dụng URL này để tải ảnh về và hiển thị nó
                        Glide.with(activity)
                                .load(imageUrl)
                                .into(ImageView);
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

    public interface UserCallback {
        void onUserFound(User user);

        void onUserNotFound();

        void onError(String errorMessage);
    }
    public void getUserByEmail(String userEmail, UserCallback callback) {
        // Thực hiện truy vấn để lấy người dùng với email tương ứng
        DatabaseReference usersRef = DatabaseFirebaseManager.getInstance().getDatabaseReference().child("users");
        Query query = usersRef.orderByChild("email").equalTo(userEmail);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Lặp qua các snapshot để lấy dữ liệu của người dùng
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User user = snapshot.getValue(User.class);
                        if (user != null) {
                            callback.onUserFound(user);
                            return; // Chỉ cần lấy một người dùng
                        }
                    }
                } else {
                    callback.onUserNotFound();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onError(databaseError.getMessage());
            }
        });
    }

    public interface CompanyCallback {
        void onCompanyFound(Company company);

        void onCompanyNotFound();

        void onError(String errorMessage);
    }

    public void getCompanyByEmail(String userEmail, CompanyCallback callback) {
        // Thực hiện truy vấn để lấy người dùng với email tương ứng
        DatabaseReference companysRef = DatabaseFirebaseManager.getInstance().getDatabaseReference().child("companys");
        Query query = companysRef.orderByChild("email").equalTo(userEmail);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Lặp qua các snapshot để lấy dữ liệu của người dùng
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Company company = snapshot.getValue(Company.class);
                        if (company != null) {
                            callback.onCompanyFound(company);
                            return; // Chỉ cần lấy một người dùng
                        }
                    }
                } else {
                    callback.onCompanyNotFound();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onError(databaseError.getMessage());
            }
        });
    }

    public interface InvitesCallback {
    void onInvitesReceivedFound(List<Invite> invites);

    void onError(String errorMessage);
}
    public void getAllInvites(String userEmail, InvitesCallback callback) {
        DatabaseReference invitesRef = DatabaseFirebaseManager.getInstance().getDatabaseReference().child("invites");
        Query query = invitesRef.orderByChild("emailReceive").equalTo(userEmail);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Invite> inviteList = new ArrayList<>();
                for (DataSnapshot inviteSnapshot : snapshot.getChildren()) {
                    // Kiểm tra xem có dữ liệu trong snapshopt không
                    if (inviteSnapshot.exists()) {
                        // Tạo một đối tượng Invite từ dữ liệu trong snapshot
                        Invite invite = inviteSnapshot.getValue(Invite.class);
                        if (invite != null) {
                            // Thêm invite vào danh sách
                            inviteList.add(invite);
                            // Log thông tin của invite
                            Log.d("Firebase", "Invite userNameSend: " + invite.getUserNameSend());
                            Log.d("Firebase", "Invite avatarSend: " + invite.getAvatarSend());
                            Log.d("Firebase", "Invite companyId: " + invite.getCompanyId());
                            Log.d("Firebase", "Invite context: " + invite.getContext());
                            Log.d("Firebase", "Invite emailSend: " + invite.getEmailSend());
                            Log.d("Firebase", "Invite emailReceive: " + invite.getEmailReceive());
                        }
                    }
                }
                // Gửi danh sách invites thông qua callback
                callback.onInvitesReceivedFound(inviteList);
                Log.d("Firebase", "Total invites for email " + userEmail + ": " + inviteList.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi nếu có
                callback.onError(error.getMessage());
                Log.e("Firebase", "Error getting invites: " + error.getMessage(), error.toException());
            }
        });
    }
    public interface SaveJoinListener {
        void onSaveJoinSuccess(); // Được gọi khi việc lưu dữ liệu thành công
        void onSaveJoinFailure(String errorMessage); // Được gọi khi có lỗi xảy ra trong quá trình lưu dữ liệu
    }
    public void saveJoin(String emailReceive, String companyId, Activity activity, SaveJoinListener listener) {
        DatabaseReference joinCompanyRef = DatabaseFirebaseManager.getInstance().getDatabaseReference().child("joinCompanys");

        // Sắp xếp dữ liệu theo cả hai trường companyId và emailReceive
        Query query = joinCompanyRef.orderByChild("companyId").equalTo(companyId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean companyExists = false;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Kiểm tra xem có bất kỳ nút nào có cả hai trường companyId và emailReceive không
                    if (dataSnapshot.child("emailReceive").getValue(String.class).equals(emailReceive)) {
                        companyExists = true;
                        break;
                    }
                }
                if (companyExists) {
                    Toast.makeText(activity, "Company already exists", Toast.LENGTH_SHORT).show();
                    // Gọi callback để thông báo rằng saveJoin đã hoàn thành
                    listener.onSaveJoinSuccess();
                } else {
                    // Tạo một đối tượng JoinCompany mới và thêm vào Firebase
                    DatabaseReference newJoinCompanyRef = joinCompanyRef.push();
                    newJoinCompanyRef.child("companyId").setValue(companyId);
                    newJoinCompanyRef.child("emailReceive").setValue(emailReceive);
                    Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show();
                    // Gọi callback để thông báo rằng saveJoin đã hoàn thành
                    listener.onSaveJoinSuccess();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi nếu có
                listener.onSaveJoinFailure(error.getMessage());
            }
        });
    }
    public interface SaveProjectJoinListener {
        void onSaveProjectJoinSuccess(); // Được gọi khi việc lưu dữ liệu thành công
        void onSaveProjectJoinFailure(String errorMessage); // Được gọi khi có lỗi xảy ra trong quá trình lưu dữ liệu
    }
    public void saveProjectJoin(String emailJoin, String projectId, Activity activity, SaveProjectJoinListener listener) {
        DatabaseReference joinProjectRef = DatabaseFirebaseManager.getInstance().getDatabaseReference().child("joinProjects");
        String emailEncode = emailJoin.replace("," , ".");
        // Sắp xếp dữ liệu theo cả hai trường companyId và emailReceive
        Query query = joinProjectRef.orderByChild("projectId").equalTo(projectId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean projectExists = false;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Kiểm tra xem có bất kỳ nút nào có cả hai trường companyId và emailReceive không
                    if (dataSnapshot.child("email").getValue(String.class).equals(emailEncode)) {
                        projectExists = true;
                        break;
                    }
                }
                if (projectExists) {
                    Toast.makeText(activity, "Nhân sự "+ emailEncode +" đã tham gia dự án", Toast.LENGTH_SHORT).show();
                    // Gọi callback để thông báo rằng saveJoin đã hoàn thành
                    listener.onSaveProjectJoinSuccess();
                } else {
                    // Tạo một đối tượng JoinCompany mới và thêm vào Firebase
                    DatabaseReference newJoinCompanyRef = joinProjectRef.push();
                    newJoinCompanyRef.child("projectId").setValue(projectId);
                    newJoinCompanyRef.child("email").setValue(emailEncode);
                    Toast.makeText(activity, "Thêm thành công " + emailEncode + " vào dự án", Toast.LENGTH_SHORT).show();
                    // Gọi callback để thông báo rằng saveJoin đã hoàn thành
                    listener.onSaveProjectJoinSuccess();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi nếu có
                listener.onSaveProjectJoinFailure(error.getMessage());
            }
        });
    }
    public interface UsersCallback {
        void onInvitesReceivedFound(List<User> users);

        void onError(String errorMessage);
    }

    public interface JoinCompanyCallback {
        void onCompanyIdFound(String companyId); // Được gọi khi tìm thấy companyId
        void onCompanyIdNotFound(); // Được gọi khi không tìm thấy companyId
        void onError(String errorMessage); // Được gọi khi có lỗi xảy ra
    }
    public void getCompanyIdByEmailJoin(String emailReceive, DatabaseFirebaseManager.JoinCompanyCallback callback) {
        DatabaseReference joinCompanyRef = DatabaseFirebaseManager.getInstance().getDatabaseReference().child("joinCompanys");

        // Truy vấn để tìm nút có emailReceive phù hợp
        Query query = joinCompanyRef.orderByChild("emailReceive").equalTo(emailReceive);

        // Thực hiện lắng nghe sự kiện khi dữ liệu thay đổi
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Kiểm tra xem có dữ liệu phù hợp không
                if (snapshot.exists()) {
                    // Duyệt qua các nút tìm được
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String companyId = dataSnapshot.child("companyId").getValue(String.class);
                        // Gọi callback và trả về companyId
                        callback.onCompanyIdFound(companyId);
                        return; // Kết thúc vòng lặp sau khi tìm thấy companyId đầu tiên
                    }
                } else {
                    // Không tìm thấy dữ liệu phù hợp
                    callback.onCompanyIdNotFound();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi nếu có
                callback.onError(error.getMessage());
            }
        });
    }
    public interface EmailsCallback {
        void onEmailsFound(List<String> emails); // Được gọi khi tìm thấy các email
        void onEmailsNotFound(); // Được gọi khi không tìm thấy email nào
        void onError(String errorMessage); // Được gọi khi có lỗi xảy ra
    }
    public void getEmailsByCompanyId(String companyId, DatabaseFirebaseManager.EmailsCallback callback) {
        DatabaseReference joinCompanyRef = DatabaseFirebaseManager.getInstance().getDatabaseReference().child("joinCompanys");

        // Truy vấn để tìm nút có companyId phù hợp
        Query query = joinCompanyRef.orderByChild("companyId").equalTo(companyId);

        // Thực hiện lắng nghe sự kiện khi dữ liệu thay đổi
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> emails = new ArrayList<>();
                // Kiểm tra xem có dữ liệu phù hợp không
                if (snapshot.exists()) {
                    // Duyệt qua các nút tìm được
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String emailReceive = dataSnapshot.child("emailReceive").getValue(String.class);
                        // Thêm email vào danh sách
                        emails.add(emailReceive);
                    }
                    // Gọi callback và trả về danh sách emails
                    callback.onEmailsFound(emails);
                } else {
                    // Không tìm thấy dữ liệu phù hợp
                    callback.onEmailsNotFound();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi nếu có
                callback.onError(error.getMessage());
            }
        });
    }
    public interface UsersSameCallback {
        void onUsersFound(List<User> userList); // Được gọi khi tìm thấy các người dùng
        void onUsersNotFound(); // Được gọi khi không tìm thấy người dùng nào
        void onError(String errorMessage); // Được gọi khi có lỗi xảy ra
    }


    public void getUsersByEmails(List<String> emails, DatabaseFirebaseManager.UsersSameCallback callback) {
        DatabaseReference usersRef = DatabaseFirebaseManager.getInstance().getDatabaseReference().child("users");
        List<User> userList = new ArrayList<>();

        // Duyệt qua danh sách các email
        for (String email : emails) {
            // Encode email để sử dụng làm key trong Firebase
            String encodedEmail = email.replace(".", ",");

            // Thực hiện truy vấn để lấy thông tin người dùng với email tương ứng
            Query query = usersRef.orderByChild("email").equalTo(encodedEmail);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    // Kiểm tra xem có dữ liệu phù hợp không
                    if (snapshot.exists()) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            // Đọc thông tin từ DataSnapshot và tạo đối tượng User
                            User user = dataSnapshot.getValue(User.class);
                            if (user != null) {
                                userList.add(user);
                            }
                        }
                        // Gọi callback và trả về danh sách người dùng
                        callback.onUsersFound(userList);
                    } else {
                        // Không tìm thấy người dùng với email tương ứng
                        callback.onUsersNotFound();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Xử lý lỗi nếu có
                    callback.onError(error.getMessage());
                }
            });
        }
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
