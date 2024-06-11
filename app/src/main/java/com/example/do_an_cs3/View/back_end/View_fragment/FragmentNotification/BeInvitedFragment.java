package com.example.do_an_cs3.View.back_end.View_fragment.FragmentNotification;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_cs3.Adapter.InviteAdapter;
import com.example.do_an_cs3.Adapter.ProjectAdapter;
import com.example.do_an_cs3.Database.DatabaseFirebaseManager;
import com.example.do_an_cs3.Model.Invite;
import com.example.do_an_cs3.Model.Project;
import com.example.do_an_cs3.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BeInvitedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BeInvitedFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView rcv_invite;
    private InviteAdapter inviteAdapter;
    private DatabaseFirebaseManager dbFBManager;
    private List<Invite> inviteList;

    public BeInvitedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BeInvitedFragment newInstance(String param1, String param2) {
        BeInvitedFragment fragment = new BeInvitedFragment();
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_be_invited, container, false);

        rcv_invite = view.findViewById(R.id.rcv_invite);
        inviteList = new ArrayList<>();
        inviteAdapter = new InviteAdapter(inviteList, requireContext(),BeInvitedFragment.this,getChildFragmentManager());
        rcv_invite.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        rcv_invite.setAdapter(inviteAdapter);
        String email = getCurrentUserEmail();
        dbFBManager = new DatabaseFirebaseManager(requireContext());
        dbFBManager.getAllInvites(email, new DatabaseFirebaseManager.InvitesCallback() {
            @Override
            public void onInvitesReceivedFound(List<Invite> invites) {
                inviteList.clear();
                inviteList.addAll(invites);
                inviteAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String errorMessage) {
                // Handle error
                Log.e("BeInvitedFrament", "Error getInvites: " + errorMessage);
            }
        });


        return view;
    }
    }