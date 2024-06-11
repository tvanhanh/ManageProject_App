package com.example.do_an_cs3.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_cs3.Database.DatabaseFirebaseManager;
import com.example.do_an_cs3.FragmentPersonnal.ListPersonnalFragment;
import com.example.do_an_cs3.Model.User;
import com.example.do_an_cs3.R;
import com.example.do_an_cs3.View.Project.DetailProjectActivity;
import com.example.do_an_cs3.View.back_end.View_fragment.FragmentHome.UpdateNewFragment;
import com.example.do_an_cs3.ViewHolder.UserViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {

    private List<User> userList;
    private Context mContext;
    private DatabaseFirebaseManager dbFBManager;
    private ListPersonnalFragment activity;
    private String projectId;

    public UserAdapter(List<User> userList, Context mContext, ListPersonnalFragment activity ) {
        this.userList = userList;
        this.mContext = mContext;
        this.activity =  activity;
    }
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        dbFBManager = new DatabaseFirebaseManager(mContext);
        User user = userList.get(position);

        if(user != null){

            holder.tvUserName.setText(user.getUserName());
            String encodedEmail =user.getEmail().replace(",", ".");
            holder.tvEmail.setText(encodedEmail);
            holder.tvDepartment.setText(user.getDeparment());
            displayUserInfo(holder.circleImageView,user.getEmail());
            //holder.tvTotalProject.setText(user.getTotalProjects());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAddUserDialog(user);
                }
            });
        }
    }


    public void displayUserInfo( CircleImageView circleImageView, String userEmail ) {
        String encodedEmail = userEmail.replace(".", ",");
        DatabaseReference userRef = DatabaseFirebaseManager.getInstance().getDatabaseReference().child("users").child(encodedEmail);

        // Sử dụng ValueEventListener để lấy dữ liệu từ Firebase
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Kiểm tra xem dữ liệu có tồn tại hay không
                if (dataSnapshot.exists()) {
                    // Lấy dữ liệu từ DataSnapshot và hiển thị nó trong TextView
                    String position = dataSnapshot.child("position").getValue(String.class);
                    dbFBManager.loadImageFromFirebase(encodedEmail, activity.getActivity(), circleImageView);
                    // Hiển thị dữ liệu trong TextView
                } else {
                    // Xử lý trường hợp không có dữ liệu

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    public String getCurrentUserEmail() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", null); // Trả về null nếu không tìm thấy email
    }
    @Override
    public int getItemCount() {
        if(userList != null){
            return userList.size();
        }
        return 0;
    }
    private void showAddUserDialog(User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Xác nhận");
        builder.setMessage("Bạn có muốn thêm nhân sự " + user.getUserName() + " không?");

        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Thêm nhân sự
                activity.addUserWork(user.getEmail());

                // Chuyển sang DetailProjectActivity
                Intent intent = new Intent(mContext, DetailProjectActivity.class);
                intent.putExtra("userName", user.getUserName());
                mContext.startActivity(intent);
            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Đóng dialog
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
