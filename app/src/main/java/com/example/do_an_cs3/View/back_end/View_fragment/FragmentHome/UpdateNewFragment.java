package com.example.do_an_cs3.View.back_end.View_fragment.FragmentHome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.os.BuildCompat;
import androidx.fragment.app.Fragment;

import com.example.do_an_cs3.R;

import java.util.Locale;

public class UpdateNewFragment extends Fragment {
    public UpdateNewFragment(){

    }
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup cotainner, Bundle saveInstanceState){

        return inflater.inflate(R.layout.fragment_updatenew,cotainner,false);

    }
}
