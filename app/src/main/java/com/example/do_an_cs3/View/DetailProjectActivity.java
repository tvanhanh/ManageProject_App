package com.example.do_an_cs3.View;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.do_an_cs3.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.material.bottomsheet.BottomSheetDialog;
public class DetailProjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_project);

                Button btnViewMore = findViewById(R.id.btnViewMore);
                btnViewMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showBottomSheetDialog();
                    }
                });
            }

            private void showBottomSheetDialog() {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
                View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog, null);
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

                // Xử lý sự kiện cho các nút trong BottomSheetDialog
                Button btnShareTask = bottomSheetView.findViewById(R.id.btnShareTask);
                Button btnConfirmComplete = bottomSheetView.findViewById(R.id.btnConfirmComplete);
                Button btnPause = bottomSheetView.findViewById(R.id.btnPause);
                Button btnReject = bottomSheetView.findViewById(R.id.btnReject);
                Button btnDeleteTask = bottomSheetView.findViewById(R.id.btnDeleteTask);
                Button btnEditExtend = bottomSheetView.findViewById(R.id.btnEditExtend);
                Button btnHistory = bottomSheetView.findViewById(R.id.btnHistory);

                // Thiết lập các sự kiện click cho các nút
                btnShareTask.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Xử lý chia sẻ công việc
                        bottomSheetDialog.dismiss();
                    }
                });

                btnConfirmComplete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Xác nhận hoàn thành
                        bottomSheetDialog.dismiss();
                    }
                });

                btnPause.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Tạm dừng công việc
                        bottomSheetDialog.dismiss();
                    }
                });

                btnReject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Từ chối công việc
                        bottomSheetDialog.dismiss();
                    }
                });

                btnDeleteTask.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Xóa công việc
                        bottomSheetDialog.dismiss();
                    }
                });

                btnEditExtend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Sửa hoặc gia hạn công việc
                        bottomSheetDialog.dismiss();
                    }
                });

                btnHistory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Xem lịch sử công việc
                        bottomSheetDialog.dismiss();
                    }
                });
            }
}