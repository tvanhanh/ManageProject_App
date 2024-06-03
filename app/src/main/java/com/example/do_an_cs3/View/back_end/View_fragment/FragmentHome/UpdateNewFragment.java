package com.example.do_an_cs3.View.back_end.View_fragment.FragmentHome;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_cs3.Adapter.ProjectAdapter;
import com.example.do_an_cs3.Adapter.TaskAdapter;
import com.example.do_an_cs3.Database.DatabaseManager;
import com.example.do_an_cs3.Model.Project;
import com.example.do_an_cs3.R;

import java.util.List;

public class UpdateNewFragment extends Fragment {

    private RecyclerView rcv_project;
    private ProjectAdapter project_adapter;

    private DatabaseManager dbManager;
    private List<Project> projects;

    public UpdateNewFragment() {
        // Required empty public constructor
    }

    private String getCurrentUserEmail() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", null); // Trả về null nếu không tìm thấy email
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_updatenew, container, false);

        rcv_project = view.findViewById(R.id.rcv_project);

        // Set up the adapter
        dbManager = new DatabaseManager(requireContext());
        String email = getCurrentUserEmail();
        projects = dbManager.getAllProjects(email);
        TaskAdapter taskAdapter = new TaskAdapter(/* các tham số cần thiết */);

// Khởi tạo ProjectAdapter và truyền tham chiếu đến TaskAdapter
        ProjectAdapter projectAdapter = new ProjectAdapter(projects, requireContext(), taskAdapter);

        // Set up RecyclerView for Project
        LinearLayoutManager projectLayoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
        rcv_project.setLayoutManager(projectLayoutManager);
        rcv_project.setAdapter(projectAdapter);

        return view;
    }

    public void updateRecyclerView() {
        String email = getCurrentUserEmail();
        projects.clear(); // Clear the existing list
        projects.addAll(dbManager.getAllProjects(email)); // Get updated list from database
        project_adapter.notifyDataSetChanged(); // Notify adapter about data changes
    }


}
