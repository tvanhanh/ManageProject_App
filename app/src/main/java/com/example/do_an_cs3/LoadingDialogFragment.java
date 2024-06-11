package com.example.do_an_cs3;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class LoadingDialogFragment extends DialogFragment {

    public static LoadingDialogFragment newInstance() {
        return new LoadingDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Đang tải..");
        progressDialog.setCancelable(false);
        return progressDialog;
    }
}
