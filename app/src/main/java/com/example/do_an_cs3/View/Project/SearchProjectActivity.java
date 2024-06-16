package com.example.do_an_cs3.View.Project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_cs3.Adapter.ProjectAdapter;
import com.example.do_an_cs3.Database.DatabaseFirebaseManager;
import com.example.do_an_cs3.Model.Project;
import com.example.do_an_cs3.R;
import com.example.do_an_cs3.View.MainActivity;
import com.google.firebase.database.*;
import java.util.ArrayList;
import java.util.List;

public class SearchProjectActivity extends AppCompatActivity {

    private EditText searchEditText;
    private ImageView btnSearch;
    private RecyclerView recyclerSearch;
    private ProjectAdapter projectAdapter;
    private TextView resultSearch;
    private List<Project> projectList = new ArrayList<>();
    DatabaseFirebaseManager dbManager;
    private Button btnHome;

    private String getCurrentUserEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", null);
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_project);
        dbManager = new DatabaseFirebaseManager();
        searchEditText = findViewById(R.id.search_edit_text);
        btnSearch = findViewById(R.id.btn_search);
        recyclerSearch = findViewById(R.id.recycler_search);
        resultSearch = findViewById(R.id.resultSearch);

        recyclerSearch.setLayoutManager(new LinearLayoutManager(SearchProjectActivity.this, RecyclerView.VERTICAL, false));
        projectAdapter = new ProjectAdapter(projectList, this, this);
        recyclerSearch.setAdapter(projectAdapter);
        btnHome = findViewById(R.id.buttonHome);
        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(SearchProjectActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQuery = searchEditText.getText().toString().trim();
                if (!searchQuery.isEmpty()) {
                    String email = getCurrentUserEmail();
                    if (email != null) {
                        dbManager.getProjectsByEmailAndSearch(email, searchQuery, new DatabaseFirebaseManager.GetProjectsByIdsListener() {
                            @Override
                            public void onGetProjectsByIdsSuccess(List<Project> projects) {
                                projectList.clear();
                                projectList.addAll(projects);
                                projectAdapter.notifyDataSetChanged();
                                 // Kiểm tra nếu danh sách dự án rỗng

                                    // Kiểm tra nếu danh sách dự án rỗng
                                    if (projects.isEmpty()) {
                                        resultSearch.setText("Không tìm thấy dự án nào có tên " + searchQuery );
                                        resultSearch.setTextColor(getResources().getColor(R.color.red));
                                    }else{
                                        resultSearch.setText("Kết quả tìm kiếm dự án tên "  + searchQuery  +" là:  ");
                                        resultSearch.setTextColor(getResources().getColor(R.color.xanhduong));
                                }
                            }
                            @Override
                            public void onGetProjectsByIdsFailure(String errorMessage) {
                                Toast.makeText(SearchProjectActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    Toast.makeText(SearchProjectActivity.this, "Nhập tên dự án để tìm kiếm", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
