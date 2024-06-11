package com.example.do_an_cs3.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_cs3.Database.DatabaseFirebaseManager;
import com.example.do_an_cs3.Invite.DetailInviteActivity;
import com.example.do_an_cs3.LoadingDialogFragment;
import com.example.do_an_cs3.Model.Invite;
import com.example.do_an_cs3.Model.Task;
import com.example.do_an_cs3.R;
import com.example.do_an_cs3.View.back_end.View_fragment.FragmentHome.UpdateNewFragment;
import com.example.do_an_cs3.View.back_end.View_fragment.FragmentNotification.BeInvitedFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class InviteAdapter extends RecyclerView.Adapter<InviteAdapter.InviteViewHolder> {
    private List<Invite> invites;
    private Context mContext;
    private DatabaseFirebaseManager dbFBManager;
    private BeInvitedFragment activity;
    private LoadingDialogFragment loadingDialog;
    private FragmentManager fragmentManager;

    public InviteAdapter(List<Invite> invites, Context mContext) {
        this.invites = invites;
        this.mContext = mContext;

    }
    public InviteAdapter() {
    }

    public InviteAdapter(List<Invite> invites, Context mContext, BeInvitedFragment activity, FragmentManager fragmentManager) {
        this.invites = invites;
        this.mContext = mContext;
        this.activity =  activity;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public InviteAdapter.InviteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notifi, parent, false);
        return new InviteAdapter.InviteViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull InviteAdapter.InviteViewHolder holder, int position) {
        dbFBManager = new DatabaseFirebaseManager(mContext);
        Invite invite = invites.get(position);
        if (invite != null) {
            holder.userNameSend.setText(invite.getUserNameSend());
            holder.userEmailSend.setText(invite.getEmailSend());
            holder.context.setText(invite.getContext());
            displayUserInfo(holder.circleImageView, invite.getEmailSend());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DetailInviteActivity.class);
                    intent.putExtra("invite", invite);
                    mContext.startActivity(intent);
                }
            });
        }

    }
    @Override
    public int getItemCount() {
        if(invites != null){
            return invites.size();
        }
        return 0;
    }
    public void displayUserInfo(CircleImageView circleImageView, String userEmail) {

        String encodedEmail = userEmail.replace(".", ",");
        DatabaseReference userRef = DatabaseFirebaseManager.getInstance().getDatabaseReference().child("users").child(encodedEmail);

        // Sử dụng ValueEventListener để lấy dữ liệu từ Firebase
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Kiểm tra xem dữ liệu có tồn tại hay không
                if (dataSnapshot.exists()) {
                    // Lấy dữ liệu từ DataSnapshot và hiển thị nó trong TextView
                    String userName = dataSnapshot.child("username").getValue(String.class);
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

    public static class InviteViewHolder extends RecyclerView.ViewHolder {

        private TextView userNameSend;
        private TextView userEmailSend;
        private TextView context;
        private CircleImageView circleImageView;
        public InviteViewHolder(@NonNull View itemView) {
            super(itemView);
            userEmailSend = itemView.findViewById(R.id.tvEmailSend);
            userNameSend = itemView.findViewById(R.id.tvNameSend);
            context = itemView.findViewById(R.id.tvContent);
            circleImageView = itemView.findViewById(R.id.img_user);
        }
    }
    public String getCurrentUserEmail() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", null); // Trả về null nếu không tìm thấy email
    }
}
