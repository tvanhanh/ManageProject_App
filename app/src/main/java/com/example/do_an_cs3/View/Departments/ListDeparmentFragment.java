package com.example.do_an_cs3.View.Departments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_cs3.Adapter.DepartmentAdapter;
import com.example.do_an_cs3.Adapter.ProjectAdapter;
import com.example.do_an_cs3.Database.DatabaseFirebaseManager;
import com.example.do_an_cs3.Model.Deparments;
import com.example.do_an_cs3.Model.Project;
import com.example.do_an_cs3.R;

import com.example.do_an_cs3.FragmentPersonnal.ListPersonnalFragment;

import com.example.do_an_cs3.View.Departments.AddDeparmentsActivity;
//import com.example.do_an_cs3.View.back_end.View_fragment.FragmentPersonnal.ListPersonnalFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ListDeparmentFragment extends Fragment {
    private Button add;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button buttonAdd;
    private RecyclerView recyclerView;
    private DepartmentAdapter departmentAdapter;
   // private List<Deparments> departmentList;
    private List<Deparments> departmentList = new ArrayList<>();
    private TextView option;

    private DatabaseFirebaseManager dbFBManager;
    private DatabaseReference databaseDepartments;

    public ListDeparmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListPersonnalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListPersonnalFragment newInstance(String param1, String param2) {
        ListPersonnalFragment fragment = new ListPersonnalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private String getCurrentUserEmail() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", null);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_deparment, container, false);
        String email = getCurrentUserEmail();
        recyclerView = view.findViewById(R.id.department_reycleView);

        departmentList = new ArrayList<>();
        departmentAdapter = new DepartmentAdapter(departmentList, requireContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(departmentAdapter);

        dbFBManager = new DatabaseFirebaseManager(requireContext());
        dbFBManager.getDeparment(email,new DatabaseFirebaseManager.DepartmentCallback(){
            @Override
            public void onDepartmentReceived(List<Deparments> deparments) {
                departmentList.clear();
                departmentList.addAll(deparments);
                departmentAdapter.notifyDataSetChanged();
            }


            @Override
            public void onError(String errorMessage) {

            }
        })
        ;






        buttonAdd = view.findViewById(R.id.buttonadd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddDeparmentsActivity.class);
                startActivity(intent);
            }
        });

        return view;


    }
}
