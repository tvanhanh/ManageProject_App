package com.example.do_an_cs3.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_cs3.Model.Deparments;
import com.example.do_an_cs3.Model.Project;
import com.example.do_an_cs3.R;
import com.example.do_an_cs3.View.Project.DetailProjectActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.DeparmentViewHolder> {

    private List<Deparments> deparmentsList;
    private Context mContext;

    public DepartmentAdapter(List<Deparments> deparmentsList, Context mContext) {
        this.deparmentsList = deparmentsList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public DeparmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_department_list, parent, false);
        return new DeparmentViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull DeparmentViewHolder holder, int position) {
        Deparments deparment = deparmentsList.get(position);
        if (deparment != null) {
            holder.tvdeparment_name.setText(deparment.getDeparment_name());
            holder.textOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopupMenu(v, deparment.getId(),deparment.getDeparment_name()); // Truyền ID của dự án khi người dùng nhấp vào menu popup
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return deparmentsList != null ? deparmentsList.size() : 0;
    }

    public class DeparmentViewHolder extends RecyclerView.ViewHolder {

        private TextView tvdeparment_name;
        private TextView textOption;

        public DeparmentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvdeparment_name = itemView.findViewById(R.id.text_department_name);
            textOption = itemView.findViewById(R.id.textOption);
        }
    }

    private void showPopupMenu(View view, String departmentId, String nameDepartment) {
        PopupMenu popupMenu = new PopupMenu(mContext, view);
        popupMenu.inflate(R.menu.popupdepartment);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.update) {
                    showUpdateDialog(departmentId);
                    //  Toast.makeText(mContext, "Sửa: " + departmentId, Toast.LENGTH_SHORT).show();
                    return true;
                } else if (itemId == R.id.delete) {
                    // Gọi hàm xóa phòng ban khi người dùng chọn tuỳ chọn "Xóa" từ menu popup
                    deleteDepartment(departmentId, nameDepartment);
                    return true;
                } else {
                    return false;
                }
            }
        });
        popupMenu.show();
    }

    // Hàm xóa phòng ban
    private void deleteDepartment(String departmentId, String departmentName) {
        DatabaseReference departmentRef = FirebaseDatabase.getInstance().getReference("departments").child(departmentId);
        departmentRef.removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Xóa thành công, cập nhật danh sách và thông báo
                Toast.makeText(mContext, "Xóa phòng ban" + departmentName + "thành công ", Toast.LENGTH_SHORT).show();
                updateDepartmentList(departmentId);
            } else {
                // Xảy ra lỗi, thông báo lỗi
                Toast.makeText(mContext, "Xóa phòng ban thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateDepartmentList(String deletedDepartmentId) {
        // Tạo một danh sách tạm thời để lưu trữ các phòng ban mới sau khi xóa
        List<Deparments> updatedList = new ArrayList<>();
        // Lặp qua danh sách phòng ban hiện tại
        for (Deparments department : deparmentsList) {
            // Nếu ID của phòng ban hiện tại không trùng với ID của phòng ban vừa xóa, thêm phòng ban vào danh sách tạm thời
            if (!department.getId().equals(deletedDepartmentId)) {
                updatedList.add(department);
            }
        }
        // Cập nhật danh sách phòng ban với danh sách mới đã được cập nhật
        deparmentsList = updatedList;
        // Thông báo cho Adapter biết rằng dữ liệu đã thay đổi
        notifyDataSetChanged();
    }

    public void updateDepartment(String id, String newDepartmentName) {
        DatabaseReference departmentRef = FirebaseDatabase.getInstance().getReference("departments").child(id);
        departmentRef.child("deparment_name").setValue(newDepartmentName).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Cập nhật thành công, thông báo và cập nhật danh sách
                Toast.makeText(mContext, "Cập nhật phòng ban thành công", Toast.LENGTH_SHORT).show();
                updateDepartmentList(id, newDepartmentName);
            } else {
                // Xảy ra lỗi, thông báo lỗi
                Toast.makeText(mContext, "Cập nhật phòng ban thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Hàm cập nhật danh sách phòng ban sau khi sửa
    private void updateDepartmentList(String updatedDepartmentId, String newDepartmentName) {
        for (Deparments department : deparmentsList) {
            if (department.getId().equals(updatedDepartmentId)) {
                department.setDeparment_name(newDepartmentName);
                break;
            }
        }
        notifyDataSetChanged();
    }
    private void showUpdateDialog(String departmentId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Sửa phòng ban");

        final EditText input = new EditText(mContext);
        input.setHint("Nhập tên phòng ban mới");
        builder.setView(input);

        builder.setPositiveButton("Cập nhật", (dialog, which) -> {
            String newDepartmentName = input.getText().toString().trim();
            if (!newDepartmentName.isEmpty()) {
                updateDepartment(departmentId, newDepartmentName);
            } else {
                Toast.makeText(mContext, "Tên phòng ban không được để trống", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.cancel());

        builder.show();
    }

}


//}

