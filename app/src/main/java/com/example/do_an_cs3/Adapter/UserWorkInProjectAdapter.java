package com.example.do_an_cs3.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_cs3.Database.DatabaseFirebaseManager;
import com.example.do_an_cs3.Model.User;
import com.example.do_an_cs3.R;
import com.example.do_an_cs3.View.Project.DetailProjectActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserWorkInProjectAdapter extends RecyclerView.Adapter<UserWorkInProjectAdapter.UserViewHolder>  {
        private List<User> userList;
        private DatabaseFirebaseManager dbManager;
        private DetailProjectActivity activity;
        private Context mContext;

        public UserWorkInProjectAdapter(List<User> userList, DetailProjectActivity activity) {
            this.userList = userList;
            this.activity = activity;
        }

        @NonNull
        @Override
        public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_staff_work, parent, false);
            return new UserViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
            dbManager = new DatabaseFirebaseManager();
            User user = userList.get(position);
            holder.userNameTextView.setText(user.getUserName());
            //user.getPosition(
            String encodedEmail =user.getEmail().replace(",", ".");
            holder.positionAndEmailTextView.setText( " - " +encodedEmail);
            // Load ảnh avatar vào CircleImageView
            Picasso.get().load(user.getAvatar()).placeholder(R.drawable.useravatar).into(holder.circleImageViewWork);
           // displayUserInfo(holder.circleImageView,user.getEmail());
            // Xử lý sự kiện khi nhấn nút
            holder.addTaskButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Thực hiện hành động khi nhấn nút
                }
            });
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
                    dbManager.loadImageFromFirebase(encodedEmail, activity, circleImageView);
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

        @Override
        public int getItemCount() {
            return userList.size();
        }

        public static class UserViewHolder extends RecyclerView.ViewHolder {
            CircleImageView circleImageViewWork;
            TextView userNameTextView;
            TextView positionAndEmailTextView;
            Button addTaskButton;

            public UserViewHolder(@NonNull View itemView) {
                super(itemView);
                circleImageViewWork = itemView.findViewById(R.id.circleImageViewWork);
                userNameTextView = itemView.findViewById(R.id.userNameWork);
                positionAndEmailTextView = itemView.findViewById(R.id.PossitonAndEmail);
                addTaskButton = itemView.findViewById(R.id.addTask);
            }
        }
    }

