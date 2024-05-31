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
import com.example.do_an_cs3.Database.DatabaseManager;
import com.example.do_an_cs3.R;
import com.example.do_an_cs3.Model.Project;

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

        // Initialize RecyclerView
        rcv_project = view.findViewById(R.id.rcv_project);
       // rcv_project.setHasFixedSize(true);
        String email = getCurrentUserEmail();
        // Set up the adapter
        dbManager = new DatabaseManager(requireContext());
        projects = dbManager.getAllProjects(email);

        project_adapter = new ProjectAdapter(projects, requireContext());

        // Set up RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
        rcv_project.setLayoutManager(linearLayoutManager);
        rcv_project.setAdapter(project_adapter);
        return view;
    }
}
