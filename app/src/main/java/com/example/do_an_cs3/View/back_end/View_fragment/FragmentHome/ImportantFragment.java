package com.example.do_an_cs3.View.back_end.View_fragment.FragmentHome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.do_an_cs3.R;

public class ImportantFragment extends Fragment {
    public ImportantFragment(){

    }
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup cotainner, Bundle saveInstanceState){

        return inflater.inflate(R.layout.fragment_important,cotainner,false);

    }
}
