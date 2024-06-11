package com.example.do_an_cs3.Invite;

import android.app.Notification;
import android.app.Person;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import com.example.do_an_cs3.Database.DatabaseFirebaseManager;
import com.example.do_an_cs3.LoadingDialogFragment;
import com.example.do_an_cs3.Model.Company;
import com.example.do_an_cs3.Model.Invite;
import com.example.do_an_cs3.Model.User;
import com.example.do_an_cs3.R;
import com.example.do_an_cs3.View.AddInfoCompanyActivity;
import com.example.do_an_cs3.View.MainActivity;
import com.example.do_an_cs3.View.Users.AddPersonnalActivity;
import com.example.do_an_cs3.View.Users.PersonnalActivity;
import com.example.do_an_cs3.View.WarningActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class DetailInviteActivity extends AppCompatActivity {

    private DatabaseFirebaseManager dbManager;
    private Button btnjoin;
    private Button btndecline;
    private LoadingDialogFragment loadingDialog;
    private String userNamesend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_invite);
        dbManager = new DatabaseFirebaseManager();
        Invite invite = (Invite) getIntent().getSerializableExtra("invite");
        String email = invite.getEmailSend();
        String emailReceive = invite.getEmailReceive();
        String encodedEmail = emailReceive.replace(".", ",");
        dbManager.getUserByEmail(encodedEmail, new DatabaseFirebaseManager.UserCallback() {
            @Override
            public void onUserFound(User user) {
                userNamesend = user.getUserName();
                String avatar = user.getAvatar();
            }

            @Override
            public void onUserNotFound() {
                Log.d("User Info", "User not found with email: " + email);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("User Info", "Error: " + errorMessage);
            }
        });
        dbManager.getCompanyByEmail(email, new DatabaseFirebaseManager.CompanyCallback() {
            @Override
            public void onCompanyFound(Company company) {
                TextView userNameSend = findViewById(R.id.userNameSend);
                TextView context = findViewById(R.id.context);
                ImageView companyLogo = findViewById(R.id.companyLogo);
                TextView companyName = findViewById(R.id.companyName);
                TextView companyField = findViewById(R.id.companyField);
                TextView companyAddress = findViewById(R.id.companyAddress);
                TextView companyPhoneNumber = findViewById(R.id.companyPhoneNumber);
                TextView status = findViewById(R.id.statusTextView);
                String companyId = company.getCompanyId();
                dbManager.loadImageCompany(companyId, DetailInviteActivity.this,companyLogo);
                String username = invite.getUserNameSend();
                String emailSend = invite.getEmailSend();
                String emailReceive = invite.getEmailReceive();
                String name=  company.getCompanyName();
                String field = company.getField();
                String address = company.getAddress();
                String phoneNumber = company.getPhoneNumber();
                companyName.setText(name);
                companyAddress.setText("Địa chỉ : "+address);
                companyField.setText("Lĩnh vực: "+field);
                companyPhoneNumber.setText("Liên hệ: "+phoneNumber);
                status.setText(invite.getContext());
                String contetext="Thân chào " + userNamesend +",\n\nChúng tôi rất hân hạnh được mời bạn tham gia vào công ty "+ name +
                        ". Chúng tôi tin rằng bạn sẽ là một phần quan trọng của đội ngũ chúng tôi và chúng tôi rất mong được làm việc cùng bạn."
                        +
                        "Nếu bạn có câu hỏi cho chúng tôi xin vui lòng liên hệ email: "+ emailSend + " hoặc số điẹn thoại: " + phoneNumber + " để được giải đáp"
                        +
                        "\n\nTrân trọng,\n"+username;
                context.setText(contetext);
            }
            @Override
            public void onCompanyNotFound() {
                // Xử lý khi không tìm thấy người dùng với địa chỉ email đã cho
                String emailReceive = invite.getEmailReceive();
                String email = emailReceive;

                // Thử lại để lấy thông tin công ty với email nhận
                dbManager.getCompanyByEmail(email, new DatabaseFirebaseManager.CompanyCallback() {
                    @Override
                    public void onCompanyFound(Company company) {
                        // Lặp lại phần xử lý khi tìm thấy công ty
                        TextView userNameSend = findViewById(R.id.userNameSend);
                        TextView context = findViewById(R.id.context);
                        ImageView companyLogo = findViewById(R.id.companyLogo);
                        TextView companyName = findViewById(R.id.companyName);
                        TextView companyField = findViewById(R.id.companyField);
                        TextView companyAddress = findViewById(R.id.companyAddress);
                        TextView companyPhoneNumber = findViewById(R.id.companyPhoneNumber);
                        TextView status = findViewById(R.id.statusTextView);
                        String companyId = company.getCompanyId();
                        dbManager.loadImageCompany(companyId, DetailInviteActivity.this, companyLogo);
                        String username = invite.getUserNameSend();
                        String emailSend = invite.getEmailSend();
                        String emailReceive = invite.getEmailReceive();
                        String name = company.getCompanyName();
                        String field = company.getField();
                        String address = company.getAddress();
                        String phoneNumber = company.getPhoneNumber();
                        companyName.setText(name);
                        companyAddress.setText("Địa chỉ : " + address);
                        companyField.setText("Lĩnh vực: " + field);
                        companyPhoneNumber.setText("Liên hệ: " + phoneNumber);
                        status.setText(invite.getContext());
                        String contetext = "Thân chào " + username + ",\n\nChúng tôi rất hân hạnh được mời bạn tham gia vào công ty " + name +
                                ". Chúng tôi tin rằng bạn sẽ là một phần quan trọng của đội ngũ chúng tôi và chúng tôi rất mong được làm việc cùng bạn."
                                +
                                "Nếu bạn có câu hỏi cho chúng tôi xin vui lòng liên hệ email: " + emailSend + " hoặc số điẹn thoại: " + phoneNumber + " để được giải đáp"
                                +
                                "\n\nTrân trọng,\n" + username;

                        btnjoin.setVisibility(View.GONE);
                        btndecline.setVisibility(View.GONE);
                        context.setText(contetext);
                    }

                    @Override
                    public void onCompanyNotFound() {
                        // Xử lý khi không tìm thấy công ty với email nhận
                        Log.d("Company Info", "Không tìm thấy company với email nhận: " + emailReceive);
                        TextView status = findViewById(R.id.statusTextView);
                        status.setText("Không tìm thấy công ty");
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Log.e("Company Info", "Error Company: " + errorMessage);
                        TextView status = findViewById(R.id.statusTextView);
                        status.setText("Lỗi khi tìm kiếm công ty: " + errorMessage);
                    }
                });
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("Company Info", "Error Company: " + errorMessage);
                TextView status = findViewById(R.id.statusTextView);
                status.setText("Lỗi khi tìm kiếm công ty: " + errorMessage);
            }
        });
        btnjoin = findViewById(R.id.joinButton);
        btndecline = findViewById(R.id.declineButton);
        btnjoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                loadingDialog = LoadingDialogFragment.newInstance();
                loadingDialog.show(fragmentManager, "loading");
                dbManager.saveJoin(invite.getEmailReceive(), invite.getCompanyId(), DetailInviteActivity.this, new DatabaseFirebaseManager.SaveJoinListener() {
                    @Override
                    public void onSaveJoinSuccess() {
                        statusInvite(invite,"Đã");
                    }
                    @Override
                    public void onSaveJoinFailure(String errorMessage) {
                        Log.e("SaveJoin", "Error: " + errorMessage);
                    }
                });

                Intent intent = new Intent(DetailInviteActivity.this, PersonnalActivity.class);
                startActivity(intent);
                finish();
            }
        });
            btndecline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  statusInvite(invite,"Đã từ chối");
                }
                });
    }

    private void statusInvite(Invite invite, String status) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        loadingDialog = LoadingDialogFragment.newInstance();
        loadingDialog.show(fragmentManager, "loading");

        String email = invite.getEmailReceive();
        String emailReceive = invite.getEmailSend();
        String encodedEmail = email.replace(".", ",");

        if (email.isEmpty() || emailReceive.isEmpty()) {
            Toast.makeText(DetailInviteActivity.this, "Vui lòng kiểm tra lại thông tin", Toast.LENGTH_SHORT).show();
        } else {
            FragmentManager fragmentManager1 = getSupportFragmentManager();
            loadingDialog = LoadingDialogFragment.newInstance();
            loadingDialog.show(fragmentManager1, "loading");

            DatabaseReference invitesRef = DatabaseFirebaseManager.getInstance().getDatabaseReference().child("invites");
            DatabaseReference newInvitesRef = invitesRef.push();
            String inviteId = newInvitesRef.getKey();
            newInvitesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.child("invites").hasChild(inviteId)
                        //  && snapshot.child("projectsprojectsParticipant").hasChild(projectParticipantId)
                    ) {
                        Toast.makeText(DetailInviteActivity.this, "Invites already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        dbManager.getUserByEmail(encodedEmail, new DatabaseFirebaseManager.UserCallback() {
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
                        dbManager.getCompanyByEmail(invite.getEmailSend(), new DatabaseFirebaseManager.CompanyCallback() {
                            @Override
                            public void onCompanyFound(Company company) {
                                String companyId = company.getCompanyId();
                                newInvitesRef.child("companyId").setValue(companyId);
                                String companyName=  company.getCompanyName();
                                String context =  status+" tham gia công ty: " + companyName +" "+" ấn để xem chi tiết ";
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
                        Toast.makeText(DetailInviteActivity.this, "Mời nhân sự "+ emailReceive+ " thành công ! đợi Nhân sự đồng ý", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DetailInviteActivity.this, PersonnalActivity.class);
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
    }
    private void showLoading() {
        if (loadingDialog == null || !loadingDialog.isAdded()) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            loadingDialog = LoadingDialogFragment.newInstance();
            loadingDialog.show(fragmentManager, "loading");
        }
    }

    private void hideLoading() {
        if (loadingDialog != null && loadingDialog.isAdded()) {
            loadingDialog.dismiss();
        }
    }
    public String getCurrentUserEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", null);
    }
}
