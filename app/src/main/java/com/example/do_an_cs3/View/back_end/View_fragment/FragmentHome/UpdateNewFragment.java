package com.example.do_an_cs3.View.back_end.View_fragment.FragmentHome;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.do_an_cs3.Adapter.ProjectAdapter;
import com.example.do_an_cs3.Database.DatabaseFirebaseManager;
import com.example.do_an_cs3.Model.Project;
import com.example.do_an_cs3.R;
import java.util.ArrayList;
import java.util.List;

public class UpdateNewFragment extends Fragment {

    private RecyclerView rcv_project;
    private ProjectAdapter projectAdapter;
    private DatabaseFirebaseManager dbFBManager;
    private List<Project> projectList;

    public UpdateNewFragment() {
        // Required empty public constructor
    }

    private String getCurrentUserEmail() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", null);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_updatenew, container, false);

        rcv_project = view.findViewById(R.id.rcv_project);
        projectList = new ArrayList<>();
        projectAdapter = new ProjectAdapter(projectList, requireContext(),this);
        rcv_project.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        rcv_project.setAdapter(projectAdapter);

        dbFBManager = new DatabaseFirebaseManager(requireContext());

        loadProjects();

        return view;
    }

    private void loadProjects() {
        String email = getCurrentUserEmail();
        if (email == null) {
            // Handle null email
            Log.e("UpdateNewFragment", "User email is null");
            return;
        }
        dbFBManager.getAllProjects(email, new DatabaseFirebaseManager.ProjectsCallback() {
            @Override
            public void onProjectsReceived(List<Project> projects) {
                projectList.clear();
                projectList.addAll(projects);
                projectAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String errorMessage) {
                // Handle error
                Log.e("UpdateNewFragment", "Error: " + errorMessage);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadProjects();
    }
}
