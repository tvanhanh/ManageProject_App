package com.example.do_an_cs3.Adapter;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_cs3.Model.User;
import com.example.do_an_cs3.R;
import com.example.do_an_cs3.Task.AddTaskActivity;

import com.example.do_an_cs3.View.Project.DetailProjectActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserWorkInProjectAdapter extends RecyclerView.Adapter<UserWorkInProjectAdapter.UserViewHolder> {

    private List<User> userList;
    private DetailProjectActivity activity;
    private Context mContext;

    public UserWorkInProjectAdapter(List<User> userList, DetailProjectActivity activity, Context context) {
        this.userList = userList;
        this.activity = activity;
        this.mContext = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_staff_work, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.userNameTextView.setText(user.getUserName());
        String encodedEmail = user.getEmail().replace(",", ".");
        holder.positionAndEmailTextView.setText(" - " + encodedEmail);

        Picasso.get().load(user.getAvatar()).placeholder(R.drawable.useravatar).into(holder.circleImageViewWork);

        holder.addTaskButton.setOnClickListener(v -> showPopupMenu(holder, user.getEmail(), position));
    }

    private void showPopupMenu(UserViewHolder holder, String userEmail, int position) {
        PopupMenu popupMenu = new PopupMenu(mContext, holder.itemView);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            String projectId = activity.getIdProject();

            if (projectId == null || projectId.isEmpty()) {
                Log.e(TAG, "Project ID is null or empty");
                Toast.makeText(mContext, "Lỗi: ID dự án không hợp lệ", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (itemId == R.id.addJob) {
                Intent intent = new Intent(mContext, AddTaskActivity.class);
                intent.putExtra("projectId", projectId);
                intent.putExtra("participantEmail", userEmail); // Gửi email
                mContext.startActivity(intent);
            } else if (itemId == R.id.listJob) {
                Intent intent = new Intent(mContext, AddTaskActivity.class);
                intent.putExtra("projectId", projectId);
                intent.putExtra("participantEmail", userEmail); // Gửi email
                mContext.startActivity(intent);
            } else if (itemId == R.id.delete) {
                removeParticipant(userEmail, projectId, position);
            }
            return true;
        });
        popupMenu.show();
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

    private void removeParticipant(String userEmail, String projectId, int position) {
        DatabaseReference joinProjectRef = FirebaseDatabase.getInstance().getReference("joinProjects");
        joinProjectRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean participantExists = false;
                for (DataSnapshot projectSnapshot : snapshot.getChildren()) {
                    String email = projectSnapshot.child("email").getValue(String.class);
                    String projId = projectSnapshot.child("projectId").getValue(String.class);
                    if (email != null && projId != null && email.equals(userEmail) && projId.equals(projectId)) {
                        participantExists = true;
                        projectSnapshot.getRef().removeValue().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(mContext, "Xóa người tham gia thành công", Toast.LENGTH_SHORT).show();
                                userList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, userList.size());
                            } else {
                                Log.e(TAG, "Error deleting participant: " + task.getException().getMessage());
                                Toast.makeText(mContext, "Lỗi khi xóa người tham gia", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    }
                }
                if (!participantExists) {
                    Log.e(TAG, "Participant not found in project");
                    Toast.makeText(mContext, "Người tham gia không có trong dự án", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Database error: " + error.getMessage());
                Toast.makeText(mContext, "Lỗi cơ sở dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
