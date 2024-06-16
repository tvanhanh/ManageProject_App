package com.example.do_an_cs3.View.Users;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.do_an_cs3.Database.DatabaseFirebaseManager;
import com.example.do_an_cs3.LoadingDialogFragment;
import com.example.do_an_cs3.Model.Company;
import com.example.do_an_cs3.Model.User;
import com.example.do_an_cs3.R;
import com.example.do_an_cs3.View.MainActivity;
import com.example.do_an_cs3.View.Project.AddProjectActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddPersonnalActivity extends AppCompatActivity {


    private Button butonThenext;
    private EditText editTextEmailReceive;
    private DatabaseFirebaseManager dbFBManager;
    private LoadingDialogFragment loadingDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpersonnal);
        butonThenext = findViewById(R.id.butonThenext);
        editTextEmailReceive = findViewById(R.id.editTextEmail);
        dbFBManager = new DatabaseFirebaseManager(this);

        butonThenext.setOnClickListener(v -> {
            String email = getCurrentUserEmail();
            String emailReceive = editTextEmailReceive.getText().toString();
            String encodedEmail = email.replace(".", ",");


            if ( email.isEmpty() || emailReceive.isEmpty() || emailReceive.isEmpty()) {
                Toast.makeText(this, "Vui lòng kiểm tra lại thông tin", Toast.LENGTH_SHORT).show();
            } else {
                FragmentManager fragmentManager = getSupportFragmentManager();
                loadingDialog = LoadingDialogFragment.newInstance();
                loadingDialog.show(fragmentManager, "loading");

                DatabaseReference invitesRef = DatabaseFirebaseManager.getInstance().getDatabaseReference().child("invites");
                DatabaseReference newInvitesRef = invitesRef.push();
                String inviteId = newInvitesRef.getKey();
                newInvitesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child("invites").hasChild(inviteId)
                            //  && snapshot.child("projectsprojectsParticipant").hasChild(projectParticipantId)
                        ) {
                            Toast.makeText(AddPersonnalActivity.this, "Invites already exists", Toast.LENGTH_SHORT).show();
                        } else {
                            dbFBManager.getUserByEmail(encodedEmail, new DatabaseFirebaseManager.UserCallback() {
                                @Override
                                public void onUserFound(User user) {
                                    // Xử lý khi người dùng được tìm thấy
                                    // Ví dụ: Hiển thị thông tin người dùng trong giao diện người dùng
                                    String userNamesend = user.getUserName();
                                    String avatar = user.getAvatar();
                                    newInvitesRef.child("userNameSend").setValue(userNamesend);
                                    newInvitesRef.child("avatarSend").setValue(avatar);
                                    Log.d("User Info", "Username: " + user.getUserName());
                                    // Tiếp tục xử lý dữ liệu người dùng khác nếu cần
                                }
                                @Override
                                public void onUserNotFound() {
                                    // Xử lý khi không tìm thấy người dùng với địa chỉ email đã cho
                                    Log.d("User Info", "User not found with email: " + email);
                                }

                                @Override
                                public void onError(String errorMessage) {
                                    // Xử lý khi có lỗi xảy ra trong quá trình tìm kiếm người dùng
                                    Log.e("User Info", "Error: " + errorMessage);
                                }
                            });
                            dbFBManager.getCompanyByEmail(email, new DatabaseFirebaseManager.CompanyCallback() {
                                @Override
                                public void onCompanyFound(Company company) {
                                    String companyId = company.getCompanyId();
                                    newInvitesRef.child("companyId").setValue(companyId);
                                    String companyName=  company.getCompanyName();
                                    String context = "Mời bạn tham gia công ty " + companyName +" "+"ấn để xem chi tiết ";
                                    newInvitesRef.child("context").setValue(context);
                                    Log.d("Company Info", "Company: " + company.getCompanyName());
                                }

                                @Override
                                public void onCompanyNotFound() {
                                    // Xử lý khi không tìm thấy người dùng với địa chỉ email đã cho
                                    Log.d("Company Info", "Không tìm thấy company with email: " + email);
                                }
                                @Override
                                public void onError(String errorMessage) {
                                    // Xử lý khi có lỗi xảy ra trong quá trình tìm kiếm người dùng
                                    Log.e("Company Info", "Error Company: " + errorMessage);
                                }
                            });
                            newInvitesRef.child("emailSend").setValue(email);
                            newInvitesRef.child("emailReceive").setValue(emailReceive);
                            Toast.makeText(AddPersonnalActivity.this, "Mời nhân sự "+ emailReceive+ " thành công ! đợi Nhân sự đồng ý", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddPersonnalActivity.this, PersonnalActivity.class);
                            intent.putExtra("email", email);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("Firebase", "Database error: " + error.getMessage());
                    }
                });

            }
        });
    }
    private String getCurrentUserEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", null);
    }
}