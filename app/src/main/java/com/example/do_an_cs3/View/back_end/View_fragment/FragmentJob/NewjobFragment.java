package com.example.do_an_cs3.View.back_end.View_fragment.FragmentJob;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.do_an_cs3.Adapter.ProjectAdapter;
import com.example.do_an_cs3.Database.DatabaseManager;
import com.example.do_an_cs3.Model.Project;
import com.example.do_an_cs3.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewjobFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewjobFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView rcv_project;
    private ProjectAdapter project_adapter;
    private DatabaseManager dbManager;
    private List<Project> projects;

    public NewjobFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Newjob_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewjobFragment newInstance(String param1, String param2) {
        NewjobFragment fragment = new NewjobFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newjob, container, false);

        // Initialize RecyclerView
        rcv_project = view.findViewById(R.id.rcv_project);
        // rcv_project.setHasFixedSize(true);

        // Set up the adapter
        dbManager = new DatabaseManager(requireContext());
        projects = dbManager.getAllProjects();
        project_adapter = new ProjectAdapter(projects, requireContext());

        // Set up RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
        rcv_project.setLayoutManager(linearLayoutManager);
        rcv_project.setAdapter(project_adapter);
        return view;
    }
}