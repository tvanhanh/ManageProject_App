package com.example.do_an_cs3.View.back_end.View_fragment.FragmentPersonnal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_cs3.Adapter.ProjectAdapter;
import com.example.do_an_cs3.Adapter.UserAdapter;

import com.example.do_an_cs3.Model.Project;
import com.example.do_an_cs3.Model.User;
import com.example.do_an_cs3.R;
import com.example.do_an_cs3.View.Users.AddPersonnalActivity;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListPersonnalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListPersonnalFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView rcv_user;
    private UserAdapter user_adapter;
   // private DatabaseManager dbManager;
    private List<User> users;
private Button buttonAdd;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListPersonnalFragment() {
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_personnal, container, false);

        rcv_user = view.findViewById(R.id.rcv_user);
        // rcv_project.setHasFixedSize(true);
      //  String email = getCurrentUserEmail();
        // Set up the adapter
       // dbManager = new DatabaseManager(requireContext());
    //    users = dbManager.getAllProjects();
      //  project_adapter = new ProjectAdapter(projects, requireContext());

        // Set up RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
     //   rcv_project.setLayoutManager(linearLayoutManager);
       // rcv_project.setAdapter(project_adapter);
        // Tìm kiếm và khởi tạo nút Button
        buttonAdd = view.findViewById(R.id.buttonaddperson);

        // Thiết lập OnClickListener cho nút Button
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện khi nút được nhấn
                // Tạo Intent để chuyển đến màn hình hoặc Activity mong muốn
                Intent intent = new Intent(getActivity(), AddPersonnalActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}