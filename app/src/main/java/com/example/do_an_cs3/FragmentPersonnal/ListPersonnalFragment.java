package com.example.do_an_cs3.FragmentPersonnal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_cs3.Adapter.UserAdapter;

import com.example.do_an_cs3.Database.DatabaseFirebaseManager;
import com.example.do_an_cs3.Model.User;
import com.example.do_an_cs3.R;
import com.example.do_an_cs3.View.Project.AddProjectActivity;
import com.example.do_an_cs3.View.Project.DetailProjectActivity;
import com.example.do_an_cs3.View.Users.AddPersonnalActivity;
import com.example.do_an_cs3.View.Users.PersonnalActivity;

import java.util.ArrayList;
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
    private List<User> users = new ArrayList<>();;
    private Button buttonAdd;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String idProject;
    private String nameProject;
    private DatabaseFirebaseManager dbManager;

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
        View view = inflater.inflate(R.layout.fragment_list_personnal, container, false);

        rcv_user = view.findViewById(R.id.rcv_user);
        buttonAdd = view.findViewById(R.id.buttonaddperson);
        dbManager = new DatabaseFirebaseManager();
        // Set up RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
        rcv_user.setLayoutManager(linearLayoutManager);
        users = new ArrayList<>();
        // Set up UserAdapter
        user_adapter = new UserAdapter(users,requireContext(),this);
        rcv_user.setAdapter(user_adapter);
        Intent intent = getActivity().getIntent();
        idProject = intent.getStringExtra("idProject");
        nameProject = intent.getStringExtra("nameProject");

        // Set up OnClickListener for the Add button
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddPersonnalActivity.class);
                startActivity(intent);
            }
        });

        // Load user data from Firebase
        loadUsersData();

        return view;
    }

    public String getCurrentUserEmail() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", null);
    }
    private void loadUsersData() {
        // Sử dụng phương thức để lấy danh sách người dùng tham gia cùng một công ty từ Firebase
        String email = getCurrentUserEmail();
        DatabaseFirebaseManager.getInstance().getCompanyIdByEmailJoin(email, new DatabaseFirebaseManager.JoinCompanyCallback() {
            @Override
            public void onCompanyIdFound(String companyId) {
                // Khi tìm thấy companyId, sử dụng nó để lấy danh sách email tham gia cùng công ty
                DatabaseFirebaseManager.getInstance().getEmailsByCompanyId(companyId, new DatabaseFirebaseManager.EmailsCallback() {
                    @Override
                    public void onEmailsFound(List<String> emails) {
                        // Khi tìm thấy danh sách email, sử dụng chúng để lấy thông tin người dùng tương ứng
                        DatabaseFirebaseManager.getInstance().getUsersByEmails(emails, new DatabaseFirebaseManager.UsersSameCallback() {
                            @Override
                            public void onUsersFound(List<User> userList) {
                                // Khi tìm thấy danh sách người dùng, cập nhật RecyclerView
                                updateRecyclerView(userList);
                            }

                            @Override
                            public void onUsersNotFound() {
                                // Xử lý khi không tìm thấy người dùng
                            }

                            @Override
                            public void onError(String errorMessage) {
                                // Xử lý khi có lỗi xảy ra
                            }
                        });
                    }

                    @Override
                    public void onEmailsNotFound() {
                        // Xử lý khi không tìm thấy email nào tham gia công ty
                    }

                    @Override
                    public void onError(String errorMessage) {
                        // Xử lý khi có lỗi xảy ra
                    }
                });
            }

            @Override
            public void onCompanyIdNotFound() {
                // Xử lý khi không tìm thấy companyId
            }

            @Override
            public void onError(String errorMessage) {
                // Xử lý khi có lỗi xảy ra
            }
        });
    }


    private void updateRecyclerView(List<User> userList) {
        if (userList != null && !userList.isEmpty()) {
            users.clear();
            users.addAll(userList);
            user_adapter.notifyDataSetChanged();
        }
    }
    public void addUserWork(String emailWork){
        dbManager.saveProjectJoin( emailWork, idProject, getActivity(), new DatabaseFirebaseManager.SaveProjectJoinListener() {
            @Override
            public void onSaveProjectJoinSuccess() {
                Intent intent = new Intent(getActivity(), DetailProjectActivity.class);
                intent.putExtra("idProject", idProject);
                intent.putExtra("projectName", nameProject);
                startActivity(intent);
                getActivity().finish();
            }
            @Override
            public void onSaveProjectJoinFailure(String errorMessage) {

            }
        });
    }
}